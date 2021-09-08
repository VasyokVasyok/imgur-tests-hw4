package ru.VasyokVasyok.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.VasyokVasyok.base.Images;
import ru.VasyokVasyok.base.Videos;

import java.io.File;

import static io.restassured.RestAssured.given;
import static ru.VasyokVasyok.base.Videos.VIDEO_MP4;
import static ru.VasyokVasyok.base.Videos.VIDEO_MPEG;

public class UploadVideoTests extends BaseTest {
    String videoDeleteHash;

    //Добавление видео разных форматов
    @ParameterizedTest
    @EnumSource(Videos.class)
    void uploadVideoFileDifferentFormatTest(Videos video) {
        videoDeleteHash = given()
                .multiPart("video", video.getPath())
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
