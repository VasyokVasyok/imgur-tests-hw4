package ru.VasyokVasyok.tests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Videos.VIDEO_MP4;
import static ru.VasyokVasyok.base.Videos.VIDEO_MPEG;

public class UploadVideoTests extends BaseTest {
    String videoDeleteHash;

    @Test
    void uploadImageFileVideoMP4Test() {
        videoDeleteHash = given()
                .multiPart("video", new File(VIDEO_MP4.getPath()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @Test
    void uploadImageFileVideoMEGTest() {
        videoDeleteHash = given()
                .multiPart("video", new File(VIDEO_MPEG.getPath()))
                .when()
                .post("/image")
                .prettyPeek()
                .jsonPath()
                .get("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        if (videoDeleteHash != null) {
            given()
                    .when()
                    .delete("image/{imageDeleteHash}", videoDeleteHash);
        } else {
            System.out.println("There is no image to delete!");
        }
    }
}
