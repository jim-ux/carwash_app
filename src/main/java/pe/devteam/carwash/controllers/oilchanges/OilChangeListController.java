package pe.devteam.carwash.controllers.oilchanges;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class OilChangeListController {

    @FXML
    private DatePicker dtpFecha;

    @FXML
    private void initialize(){

        dtpFecha.showWeekNumbersProperty();

        dtpFecha.setOnAction((EventHandler) event -> {
            LocalDate date  = dtpFecha.getValue();
            System.out.println("Fecha seleccionada:" + date.toString());
        });
    }


}
