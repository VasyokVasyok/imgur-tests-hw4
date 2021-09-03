package ru.VasyokVasyok.tests;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
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

    @Test
        //Добавление картинки jpeg в новый альбом
    void uploadImageJPEGInAlbum() {
        given()
                .multiPart("image", new File(IMAGE_JPEG.getPath()))
                .multiPart("album", albumDeleteHash)
                .expect()
                .body("data.width", CoreMatchers.equalTo(900))
                .body("data.height", CoreMatchers.equalTo(520))
                .body("data.type", CoreMatchers.equalTo("image/jpeg"))
                .when()
                .post("/image")
                .prettyPeek();
    }

    @Test
        //Добавление картинки png в новый альбом
    void uploadImagePNGInAlbum() {
        given()
                .multiPart("image", new File(IMAGE_PNG.getPath()))
                .multiPart("album", albumDeleteHash)
                .expect()
                .body("data.width", CoreMatchers.equalTo(512))
                .body("data.height", CoreMatchers.equalTo(512))
                .body("data.type", CoreMatchers.equalTo("image/png"))
                .when()
                .post("/image")
                .prettyPeek();
    }

    @Test
        //Добавление картинки tiff в новый альбом
    void uploadImageTIFFInAlbum() {
        given()
                .multiPart("image", new File(IMAGE_TIFF.getPath()))
                .multiPart("album", albumDeleteHash)
                .expect()
                .body("data.width", CoreMatchers.equalTo(800))
                .body("data.height", CoreMatchers.equalTo(800))
                .body("data.type", CoreMatchers.equalTo("image/tiff"))
                .when()
                .post("/image")
                .prettyPeek();
    }

    @Test
        //Добавление картинки url в новый альбом
    void uploadImageURLInAlbum() {
        given()
                .multiPart("image", IMAGE_URL.getPath())
                .multiPart("album", albumDeleteHash)
                .expect()
                .body("data.width", CoreMatchers.equalTo(512))
                .body("data.height", CoreMatchers.equalTo(512))
                .body("data.type", CoreMatchers.equalTo("image/png"))
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
