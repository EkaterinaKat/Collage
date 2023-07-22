package com.katyshevtceva.collage.logic;

import com.katyshevtseva.fx.Point;
import javafx.scene.input.MouseEvent;

class ComponentModification {
    private final Component component;
    private final ComponentModification.ModificationType modificationType;
    private final Point cursorInitPos;
    private final Point imageInitPos;

    static ComponentModification getModificationIfNeededOrNull(Component component, MouseEvent dragEvent) {
        Point dragStartPoint = new Point(dragEvent.getX(), dragEvent.getY());

        if (component.sizeAdjusterContainsPoint(dragStartPoint)) {
            return new ComponentModification(ModificationType.RESIZING, dragStartPoint, component);
        }

        if (component.imageContainsPoint(dragStartPoint)) {
            return new ComponentModification(ModificationType.MOVING, dragStartPoint, component);
        }

        return null;
    }

    private ComponentModification(ModificationType modificationType, Point dragStartPoint, Component component) {
        this.component = component;
        this.modificationType = modificationType;
        this.cursorInitPos = dragStartPoint;
        imageInitPos = component.getPos();
    }

    private enum ModificationType {
        RESIZING, MOVING
    }

    void reportDragEvent(MouseEvent event) {
        if (modificationType == ModificationType.MOVING) {
            relocateIfAllowable(event);
        } else if (modificationType == ComponentModification.ModificationType.RESIZING) {
            resizeIfAllowable(event);
        }
    }

    private void relocateIfAllowable(MouseEvent event) {
        Point newPos = new Point(event.getX() - cursorInitPos.getX() + imageInitPos.getX(),
                event.getY() - cursorInitPos.getY() + imageInitPos.getY());
        component.relocateIfAllowable(newPos);
    }

    private void resizeIfAllowable(MouseEvent event) {
        double newWidth = event.getX() - component.getPos().getX();
        component.resizeIfAllowable(newWidth);
    }
}
