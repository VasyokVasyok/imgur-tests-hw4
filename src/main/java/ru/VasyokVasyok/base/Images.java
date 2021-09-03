package ru.VasyokVasyok.base;
public enum Images {
    IMAGE_JPEG("src/test/resources/im.jpg"),
    IMAGE_PNG("src/test/resources/im1.png"),
    IMAGE_TIFF("src/test/resources/im2.tiff"),
    IMAGE_1X1_BMP("src/test/resources/im4.bmp"),
    IMAGE_10000X5000_JPEG("src/test/resources/im5.jpg"),
    IMAGE_GIF("src/test/resources/hump-day-icegif-6.gif"),
    IMAGE_URL("https://library.kissclipart.com/20180914/giq/kissclipart-automated-teller-machine-clipart-automated-teller-27a7dc965ea92c02.png");

    private String path;

    Images(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
