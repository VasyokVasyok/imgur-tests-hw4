package ru.VasyokVasyok.tests;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.VasyokVasyok.base.Images;

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

    //Запрос картинок разных форматов
    @ParameterizedTest
    @EnumSource(value = Images.class, names = {"IMAGE_JPEG", "IMAGE_PNG", "IMAGE_GIF"})
    void getExistingImageDifferentTypes(Images image) {
        given()
                .when()
                .get("image/{imageHash}", imageHash = createImage(image.getPath()));
    }

}
