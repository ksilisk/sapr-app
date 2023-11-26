package com.ksilisk.sapr.handler;

import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.storage.ConstructionStorage;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.WindowEvent;

import java.util.Optional;
import java.util.function.Supplier;

public class PreprocessorCloseEventHandler implements EventHandler<WindowEvent> {
    private final Supplier<ConstructionParameters> parametersSupplier;
    private final ConstructionStorage storage = ConstructionStorage.INSTANCE;

    public PreprocessorCloseEventHandler(Supplier<ConstructionParameters> parametersSupplier) {
        this.parametersSupplier = parametersSupplier;
    }

    @Override
    public void handle(WindowEvent event) {
        if (!parametersSupplier.get().equals(storage.getParameters())) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Вы не сохранили измнения. Хотите сохранить?",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.isEmpty()) {
                event.consume();
            }
            if (buttonType.get() == ButtonType.YES) {
                event.consume();
            }
        }
    }
}
