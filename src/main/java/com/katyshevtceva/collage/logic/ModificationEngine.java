package com.katyshevtceva.collage.logic;

import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
        collagePane.setOnDragDetected(event -> {
            if (!collage.editingMode())
                return;

            Dragboard db = collagePane.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString("");
            db.setContent(content);
            event.consume();

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

        collagePane.setOnDragOver(event -> {
            if (currentModification != null) {
                currentModification.reportDragEvent(event);
            }
        });

        collagePane.setOnDragDone(event -> {
            currentModification = null;
            event.consume();
        });
    }
}
