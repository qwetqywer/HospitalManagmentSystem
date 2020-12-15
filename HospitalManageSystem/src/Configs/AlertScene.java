package Configs;

import javafx.scene.control.Alert;

public abstract class AlertScene {
    public static void callAlert(String alertMessage) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }
}