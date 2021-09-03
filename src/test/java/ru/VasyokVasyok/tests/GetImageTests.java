package ru.VasyokVasyok.tests;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Images.*;

public class GetImageTests extends BaseTest {
    String imageHash;
    public String createImage(String nameOfIm) {
        imageHash = given()
                .body(new File(nameOfIm))
                .when()
                .post("/image")
                .jsonPath()
                .get("data.id");
        return imageHash;
    }

    @Test
    //Запрос картинки jpeg.
    void getExistingImageJPEG() {
       given()
                .when()
                .get("image/{imageHash}", imageHash = createImage(IMAGE_JPEG.getPath()));
    }

    @Test
    //Запрос картинки png.
    void getExistingImagePNG() {
        given()
                .when()
                .get("image/{imageHash}", imageHash = createImage(IMAGE_PNG.getPath()));
    }

    @Test
    //Запрос картинки Gif.
    void getExistingImageGif() {
        given()
                .when()
                .get("image/{imageHash}", imageHash = createImage(IMAGE_GIF.getPath()));
    }
}
