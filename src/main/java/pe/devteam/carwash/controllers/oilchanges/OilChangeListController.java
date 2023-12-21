package pe.devteam.carwash.controllers.oilchanges;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pe.devteam.carwash.dto.CarOilChangesDto;
import pe.devteam.carwash.models.OilChangesModel;
import pe.devteam.carwash.utils.EasyAlert;

import java.util.Date;
import java.util.List;

public class OilChangeListController{

    @FXML
    private DatePicker dtpFecha;
    @FXML
    private TableView tbvChanges;

    @FXML
    private TableColumn tbcId;

    @FXML
    private TableColumn tbcDate;

    @FXML
    private TableColumn tbcCarRegist;

    @FXML
    private TableColumn tbcActualKm;

    @FXML
    private TableColumn tbcNextKm;

    @FXML
    private TextField txtCarRegist;

    @FXML
    private ObservableList<CarOilChangesDto> carOilChangeList = FXCollections.observableArrayList();
    @FXML
    private void initialize(){

        this.configureTable();

    }

    private void configureTable(){

        //Columns configuration
        tbcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tbcCarRegist.setCellValueFactory(new PropertyValueFactory<>("carRegist"));
        tbcActualKm.setCellValueFactory(new PropertyValueFactory<>("actualKm"));
        tbcNextKm.setCellValueFactory(new PropertyValueFactory<>("nextKm"));

    }

    @FXML
    private void fillTable(){

        this.tbvChanges.getItems().clear();

        String inputDate = this.dtpFecha.getValue() != null ? this.dtpFecha.getValue().toString() : "%";
        String inputCarRegist = this.txtCarRegist.getText() != null ? this.txtCarRegist.getText() : "%";
        OilChangesModel model = new OilChangesModel();
        List<CarOilChangesDto> list = model.listOilChanges(inputDate, inputCarRegist);

        if(list.isEmpty()){

            EasyAlert alert = new EasyAlert("Oops", "No se han encontrado registros", Alert.AlertType.WARNING);
            alert.Show();

        }

        this.carOilChangeList.addAll(list);
        this.tbvChanges.setItems(this.carOilChangeList);


    }



}
