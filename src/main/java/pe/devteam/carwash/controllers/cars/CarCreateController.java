package pe.devteam.carwash.controllers.cars;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.devteam.carwash.entities.CarEntity;
import pe.devteam.carwash.models.CarModel;
import pe.devteam.carwash.utils.Constants;
import pe.devteam.carwash.utils.EasyAlert;
import pe.devteam.carwash.utils.EasyAlertEvents;

public class CarCreateController {

    @FXML
    private TextField txtCarRegist;
    @FXML
    private TextField txtCarBrand;
    @FXML
    private TextField txtCarModel;
    @FXML
    private TextField txtCarColor;

    private CarListController _controller;

    @FXML
    private void saveUser(){

        boolean correctData = this.verifyInputData();

        if(correctData){

            CarModel carModel = new CarModel();
            CarEntity carEntity = carModel.InsertUser(
                    this.txtCarRegist.getText(),
                    this.txtCarBrand.getText(),
                    this.txtCarModel.getText(),
                    this.txtCarColor.getText()
            );



             this.ConfirmInsert(carEntity);
        }
    }

    private Boolean verifyInputData(){

        String carRegist = this.txtCarRegist.getText();
        String carBrand = this.txtCarBrand.getText();
        String carModel = this.txtCarModel.getText();
        String carColor = this.txtCarColor.getText();

        boolean emptyData = (carRegist.isEmpty() || carBrand.isEmpty() || carModel.isEmpty() || carColor.isEmpty());


        if(emptyData){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Campos vacíos");
            alert.setContentText("Verifique que todos los campos esten rellenados");
            alert.show();

            return false;

        }

        return true;

    }

    public void SetController(CarListController controller){
        this._controller = controller;
    }

    private void ConfirmInsert(CarEntity car){

        if(car != null){

            EasyAlert alert = new EasyAlert("Datos guardados", "Los datos han sido guardados correctamente", Alert.AlertType.INFORMATION);
            alert.ShowWithActions(new EasyAlertEvents() {
                @Override
                public void onOkOrDismiss() {
                    _controller.insertRow(car);
                    ((Stage) txtCarColor.getScene().getWindow()).close();
                }
            });

        }

    }
}
