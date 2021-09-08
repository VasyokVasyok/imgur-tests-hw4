package ru.VasyokVasyok.base;

public enum Images {
    IMAGE_JPEG("src/test/resources/im.jpg", "image/jpeg", 900, 520),
    IMAGE_PNG("src/test/resources/im1.png", "image/png", 512, 512),
    IMAGE_TIFF("src/test/resources/im2.tiff", "image/tiff", 800, 800),
    IMAGE_1X1_BMP("src/test/resources/im4.bmp", "image/bmp", 1, 1),
    IMAGE_10000X5000_JPEG("src/test/resources/im5.jpg", "image/jpeg", 10000, 5000),
    IMAGE_GIF("src/test/resources/hump-day-icegif-6.gif", "image/gif", 396, 504),
    IMAGE_URL("https://library.kissclipart.com/20180914/giq/kissclipart-automated-teller-machine-clipart-automated-teller-27a7dc965ea92c02.png", "image/png", 512, 512);

    private String path;
    private String type;
    private Integer width;
    private Integer height;

    Images(String path, String type, Integer width, Integer height) {
        this.path = path;
        this.type = type;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public String getType() {
        return type;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
