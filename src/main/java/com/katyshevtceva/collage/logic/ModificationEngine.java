package com.katyshevtceva.collage.logic;

import javafx.scene.layout.Pane;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class ModificationEngine {
    private Collage collage;
    private Pane collagePane;
    private ComponentModification currentModification;

    ModificationEngine(Collage collage) {
        this.collage = collage;
        collagePane = collage.getPane();
        tuneComponentModificationMechanism();
    }

    private void tuneComponentModificationMechanism() {
        collagePane.setOnMousePressed(event -> {
            if (!collage.editingMode())
                return;

            List<Component> components = collage.getComponents().stream()
                    .sorted(Comparator.comparing(Component::getZ).reversed())
                    .collect(Collectors.toList());

            for (Component component : components) {
                currentModification = ComponentModification.getModificationIfNeededOrNull(component, event);
                if (currentModification != null) {
                    collage.moveComponentToFirstPlan(component);
                    break;
                }
            }
        });

        collagePane.setOnMouseDragged(event -> {
            if (currentModification != null) {
                currentModification.reportDragEvent(event);
            }
        });
    }
}
