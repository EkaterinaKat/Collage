package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.ImageSizeUtil;
import com.katyshevtseva.fx.dialog.StandardDialogBuilder;
import com.katyshevtseva.fx.dialog.controller.ImageSelectDialogController.ImageContainer;
import com.katyshevtseva.general.OneArgKnob;

import java.util.ArrayList;
import java.util.List;

class Utils {

    static void setSizeByWidth(Image image, double newWidth) {
        image.setFitWidth(newWidth);
        image.setFitHeight(ImageSizeUtil.getHeightByWidth(image.getImageView(), newWidth));
    }

    static void setSizeByHeight(Image image, double newHeight) {
        image.setFitHeight(newHeight);
        image.setFitWidth(ImageSizeUtil.getWidthByHeight(image.getImageView(), newHeight));
    }

    static void openImageSelectionDialog(List<Image> images, OneArgKnob<ImageContainer> listener) {
        new StandardDialogBuilder()
                .setIconPath(Config.designInfo == null ? null : Config.designInfo.getIconPath())
                .setCssPath(Config.designInfo == null ? null : Config.designInfo.getCssPath())
                .openImageSelectionDialog(new ArrayList<>(images), listener);
    }

    static void openEditableImageSelectionDialog(
            List<Image> images,
            List<Image> addableImages,
            OneArgKnob<ImageContainer> imageClickListener,
            OneArgKnob<List<ImageContainer>> windowCloseListener) {
        new StandardDialogBuilder()
                .setIconPath(Config.designInfo == null ? null : Config.designInfo.getIconPath())
                .setCssPath(Config.designInfo == null ? null : Config.designInfo.getCssPath())
                .openEditableImageSelectionDialog(new ArrayList<>(images), new ArrayList<>(addableImages),
                        imageClickListener, windowCloseListener);
    }
}
