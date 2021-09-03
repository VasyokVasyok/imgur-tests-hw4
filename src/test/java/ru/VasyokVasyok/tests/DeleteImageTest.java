package ru.VasyokVasyok.tests;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Images.*;

public class DeleteImageTest extends BaseTest {
    String imageDeleteHash;

    public String createImage(String nameOfIm) {
        imageDeleteHash = given()
                .body(new File(nameOfIm))
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
        return imageDeleteHash;
    }

    @Test
    //Удаление картинки jpeg.
    void deleteExistingImageJPEGTest() {
        given()
                .when()
                .delete("image/{imageDeleteHash}",  imageDeleteHash = createImage(IMAGE_JPEG.getPath()));
    }

    @Test
    //удаление картинки png.
    void deleteExistingImagePNGTest() {
        given()
                .when()
                .delete("image/{imageDeleteHash}",  imageDeleteHash = createImage(IMAGE_PNG.getPath()));
    }

    @Test
    //Удаление картинки Gif.
    void deleteExistingImageGifTest() {
        given()
                .when()
                .delete("image/{imageDeleteHash}",  imageDeleteHash = createImage(IMAGE_GIF.getPath()));
    }

    @Test
    //Удаление картинки Tiff.
    void deleteExistingImageTiffTest() {
        given()
                .when()
                .delete("image/{imageDeleteHash}",  imageDeleteHash = createImage(IMAGE_TIFF.getPath()));
    }
}
