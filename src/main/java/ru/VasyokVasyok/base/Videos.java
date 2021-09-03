package ru.VasyokVasyok.base;
public enum Videos {
    VIDEO_MP4("src/test/resources/Star-6962.mp4"),
    VIDEO_MPEG("src/test/resources/Star-6962.mpeg");

    private String path;
    Videos(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
