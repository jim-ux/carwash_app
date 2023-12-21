package pe.devteam.carwash.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pe.devteam.carwash.CarWashApplication;
import pe.devteam.carwash.utils.Constants;
import pe.devteam.carwash.utils.EasyAlert;
import pe.devteam.carwash.utils.EasyAlertEvents;


import java.io.IOException;


public class MainWindowController {

    @FXML
    private MenuItem mitCarModule;
    @FXML
    private MenuItem mitOilChModule;
    @FXML
    private AnchorPane acpNavigation;

    @FXML
    private void initialize() {

        Platform.runLater(this::ConfirmApplicationClose);
    }

    @FXML
    private void navigateTo(ActionEvent event) throws IOException {

        MenuItem clickedButton = (MenuItem) event.getSource();

        String itemClicked = ((MenuItem) event.getSource()).getId();
        Node node = null;

        switch (itemClicked){
            case "mitCarModule": {
                node = FXMLLoader.load(CarWashApplication.class.getResource("cars-views/car-list-view.fxml"));
                break;
            }
            case "mitOilChModule": {
                System.out.println("Haciendo click");
                node = FXMLLoader.load(CarWashApplication.class.getResource("oil-changes/oilch-list-view.fxml"));
                break;
            }
        }

        acpNavigation.getChildren().clear();

        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);

        acpNavigation.getChildren().add(node);

    }

    @FXML
    private void ConfirmApplicationClose(){
        Stage stage = (Stage) this.acpNavigation.getScene().getWindow();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(Constants.SYSTEM_NAME);
                alert.setHeaderText("Alerta");
                alert.setContentText("¿Está seguro que desea salir del sistema?");
                alert.getButtonTypes();
                alert.showAndWait().ifPresent(response -> {
                    if (response != ButtonType.OK) {
                        event.consume();
                    }
                });
            }
        });

    }
}
