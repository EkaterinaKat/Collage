package com.katyshevtceva.collage.logic;

class Utils {

    static void setSizeByWidth(Image image, double newWidth) {
        image.setFitWidth(newWidth);
        image.setFitHeight(getHeightByWidth(image, newWidth));
    }

    static double getHeightByWidth(Image image, double width) {
        return image.getHeightToWidthRatio() * width;
    }

    static void setSizeByHeight(Image image, double newHeight) {
        image.setFitHeight(newHeight);
        image.setFitWidth(getWidthByHeight(image, newHeight));
    }

    private static double getWidthByHeight(Image image, double height) {
        return height / image.getHeightToWidthRatio();
    }
}
