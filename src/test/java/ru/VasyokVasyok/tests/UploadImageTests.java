package ru.VasyokVasyok.tests;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.VasyokVasyok.base.Images;

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

    //Загрузка картинки разных форматов.
    @ParameterizedTest
    @EnumSource(Images.class)
    void uploadImageFileDifferentFormatTest(Images image) {
        imageDeleteHash = given()
                .multiPart("image", image.getPath())
                .expect()
                .body("data.width", CoreMatchers.equalTo(image.getWidth()))
                .body("data.height", CoreMatchers.equalTo(image.getHeight()))
                .body("data.type", CoreMatchers.equalTo(image.getType()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    //Загрузка картинки base64.
    @Test
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
