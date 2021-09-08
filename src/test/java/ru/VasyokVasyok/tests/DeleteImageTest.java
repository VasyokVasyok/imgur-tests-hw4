package ru.VasyokVasyok.tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.VasyokVasyok.base.Images;

import java.io.File;

import static io.restassured.RestAssured.given;

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

    //удаление изображений разных форматов
    @ParameterizedTest
    @EnumSource(value = Images.class, names = {"IMAGE_JPEG, IMAGE_PNG, IMAGE_Tiff, IMAGE_Gif"})
    void deleteExistingImageDifferentTypesTest(Images image) {
        given()
                .when()
                .delete("image/{imageDeleteHash}", imageDeleteHash = createImage(image.getPath()));
    }
}
