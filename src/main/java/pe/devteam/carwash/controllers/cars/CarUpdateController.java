package pe.devteam.carwash.controllers.cars;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.devteam.carwash.CarWashApplication;
import pe.devteam.carwash.entities.CarEntity;
import pe.devteam.carwash.models.CarModel;

import java.io.IOException;

public class CarUpdateController {

    @FXML
    private TextField txtCarRegist;
    @FXML
    private TextField txtCarBrand;
    @FXML
    private TextField txtCarModel;
    @FXML
    private TextField txtCarColor;

    private CarEntity _carEntity;

    private int rowIndex;

    private CarListController _carListController;

    public void SetCarData(CarEntity carEntity, int index, CarListController controller){

        this.rowIndex = index;
        this._carEntity = carEntity;
        this._carListController = controller;

        txtCarRegist.setText(_carEntity.getRegistration());
        txtCarBrand.setText(_carEntity.getBrand());
        txtCarModel.setText(_carEntity.getModel());
        txtCarColor.setText(_carEntity.getColor());

    }

    @FXML
    public void updateData(){

        boolean correctData = verifyInputData();

        if(correctData){

            CarModel carModel = new CarModel();
            int status = carModel.UpdateCar(this._carEntity);
            this.confirmUpdate(status);


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

        }else{

            _carEntity.setRegistration(carRegist);
            _carEntity.setBrand(carBrand);
            _carEntity.setModel(carModel);
            _carEntity.setColor(carColor);

        }

        return true;

    }

    private void confirmUpdate(int status){

        if(status == 1){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Datos actualizados");
            alert.setContentText("Presione en el botón para regresar a la página principal");

            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.CLOSE || response == ButtonType.OK){

                    this._carListController.updateRow(this._carEntity,this.rowIndex);
                    ((Stage) this.txtCarRegist.getScene().getWindow()).close();
                }
            });


        }

    }
}
