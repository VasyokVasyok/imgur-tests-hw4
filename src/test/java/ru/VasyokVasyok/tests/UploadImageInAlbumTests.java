package ru.VasyokVasyok.tests;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.VasyokVasyok.base.Images;

import java.awt.*;
import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Images.*;

public class UploadImageInAlbumTests extends BaseTest {
    static String albumDeleteHash;
    static String albumWithoutCover;

    @Test
        //Создание нового альбома без обложки
    void createNewAlbumTest() {
        albumWithoutCover = given()
                .multiPart("title", "My little album")
                .multiPart("description", "This albums contains a lot of dank memes. Be prepared.")
                .when()
                .post("/album")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
        //Создание нового альбома с обложкой
    void createNewAlbumWithCoverTest() {
        albumDeleteHash = given()
                .multiPart("title", "My new little album")
                .multiPart("description", "This albums contains a lot of dank memes. Be prepared.")
                .multiPart("cover", IMAGE_JPEG.getPath())
                .when()
                .post("/album")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @ParameterizedTest
    @EnumSource(value = Images.class, names = {"IMAGE_JPEG, IMAGE_PNG, IMAGE_URL, IMAGE_TIFF"})
    void uploadingImageDifferentFormatsInAlbumTest(Images image) {
        given()
                .multiPart("image", image.getPath())
                .multiPart("album", albumDeleteHash)
                .expect()
                .body("data.width", CoreMatchers.equalTo(image.getWidth()))
                .body("data.height", CoreMatchers.equalTo(image.getHeight()))
                .body("data.type", CoreMatchers.equalTo(image.getType()))
                .when()
                .post("/image")
                .prettyPeek();
    }

    @AfterAll
    static void tearDown() {
        given()
                .when()
                .delete("image/{imageDeleteHash}", albumDeleteHash);
        given()
                .when()
                .delete("image/{imageDeleteHash}", albumDeleteHash);
    }
}
