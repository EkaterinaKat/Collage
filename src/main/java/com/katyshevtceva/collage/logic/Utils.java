package com.katyshevtceva.collage.logic;


import javafx.scene.image.ImageView;

class Utils {

    static void setSizeByWidth(ImageView imageView, double newWidth) {
        imageView.setFitWidth(newWidth);
        imageView.setFitHeight(getHeightByWidth(imageView, newWidth));
    }

    static double getHeightByWidth(ImageView imageView, double width) {
        return (imageView.getImage().getHeight() * width) / imageView.getImage().getWidth();
    }

    static void setSizeByHeight(ImageView imageView, double newHeight) {
        imageView.setFitHeight(newHeight);
        imageView.setFitWidth(getWidthByHeight(imageView, newHeight));
    }

    private static double getWidthByHeight(ImageView imageView, double height) {
        return (imageView.getImage().getWidth() * height) / imageView.getImage().getHeight();
    }

}
