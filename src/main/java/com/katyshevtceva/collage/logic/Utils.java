package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageSizeUtil;

class Utils {

    static void setSizeByWidth(Image image, double newWidth) {
        image.setFitWidth(newWidth);
        image.setFitHeight(ImageSizeUtil.getHeightByWidth(image.getImageView(), newWidth));
    }

    static void setSizeByHeight(Image image, double newHeight) {
        image.setFitHeight(newHeight);
        image.setFitWidth(ImageSizeUtil.getWidthByHeight(image.getImageView(), newHeight));
    }
}
