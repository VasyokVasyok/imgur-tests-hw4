package ru.VasyokVasyok.tests;
import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Images.*;

public class UploadImageTests extends BaseTest {
    String imageDeleteHash;
    static final String IMAGE_FILE = "src/test/resources/im.jpg";

    static String encodePicture() throws IOException {
        byte[] im = FileUtils.readFileToByteArray(new File(IMAGE_FILE));
        String imStr = java.util.Base64.getEncoder().encodeToString(im);
        return imStr;
    }

    @Test
        //Загрузка картинки jpeg.
    void uploadImageFileJPGTest() {
        imageDeleteHash = given()
                .body(new File(IMAGE_JPEG.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(900))
                .body("data.height", CoreMatchers.equalTo(520))
                .body("data.type", CoreMatchers.equalTo("image/jpeg"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки png.
    void uploadImageFilePNGTest() {
        imageDeleteHash = given()
                .body(new File(IMAGE_PNG.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(512))
                .body("data.height", CoreMatchers.equalTo(512))
                .body("data.type", CoreMatchers.equalTo("image/png"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки gif
    void uploadImageFileGifTest() {
        imageDeleteHash = given()
                .body(new File(IMAGE_GIF.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(396))
                .body("data.height", CoreMatchers.equalTo(504))
                .body("data.type", CoreMatchers.equalTo("image/gif"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки tiff
    void uploadImageFileTiffTest() {
        imageDeleteHash = given()
                .body(new File(IMAGE_TIFF.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(800))
                .body("data.height", CoreMatchers.equalTo(800))
                .body("data.type", CoreMatchers.equalTo("image/tiff"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки base64.
    void uploadImageBase64Test() throws IOException {
        imageDeleteHash = given()
                .multiPart("image", encodePicture())
                .expect()
                .body("data.width", CoreMatchers.equalTo(900))
                .body("data.height", CoreMatchers.equalTo(520))
                .body("data.type", CoreMatchers.equalTo("image/jpeg"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки url.
    void uploadImageUrlTest() {
        imageDeleteHash = given()
                .multiPart("image", IMAGE_URL.getPath())
                .expect()
                .body("data.width", CoreMatchers.equalTo(512))
                .body("data.height", CoreMatchers.equalTo(512))
                .body("data.type", CoreMatchers.equalTo("image/png"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки размера 1х1.
    void uploadImage1х1Test() {
        imageDeleteHash = given()
                .body(new File(IMAGE_1X1_BMP.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(1))
                .body("data.height", CoreMatchers.equalTo(1))
                .body("data.type", CoreMatchers.equalTo("image/bmp"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Загрузка картинки размера 10000х5000.
    void uploadImage10000х5000Test() {
        imageDeleteHash = given()
                .body(new File(IMAGE_10000X5000_JPEG.getPath()))
                .expect()
                .body("data.width", CoreMatchers.equalTo(10000))
                .body("data.height", CoreMatchers.equalTo(5000))
                .body("data.type", CoreMatchers.equalTo("image/jpeg"))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        if (imageDeleteHash != null) {
            given()
                    .when()
                    .delete("image/{imageDeleteHash}", imageDeleteHash);
        } else {
            System.out.println("There is no image to delete!");
        }
    }
}
