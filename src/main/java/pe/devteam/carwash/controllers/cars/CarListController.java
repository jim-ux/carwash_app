package pe.devteam.carwash.controllers.cars;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.devteam.carwash.CarWashApplication;
import pe.devteam.carwash.entities.CarEntity;
import pe.devteam.carwash.models.CarModel;
import pe.devteam.carwash.utils.Constants;

import java.io.IOException;
import java.util.List;

public class CarListController {

    @FXML
    private TextField txtSearch;
    @FXML
    private TableView tbvCars;
    @FXML
    private TableColumn tbcCarId;
    @FXML
    private TableColumn tbcCarRegist;
    @FXML
    private TableColumn tbcCarBrand;
    @FXML
    private TableColumn tbcCarModel;
    @FXML
    private TableColumn tbcCarColor;

    private int temporalDeleteIndex;

    private ObservableList<CarEntity> carsList = FXCollections.observableArrayList();

    @FXML
    private void initialize(){

        this.ConfigureTable();

    }



    @FXML
    private void FindRegist(){

        String inputRegist = this.VerifyInputRegist(this.txtSearch);

        this.FillTable(inputRegist);

    }

    private String VerifyInputRegist(TextField textField){

        String textFromTF = textField.getText();
        return textFromTF.isEmpty() ? "%" : textFromTF;

    }

    private void FillTable(String filter){

        this.tbvCars.getItems().clear();

        CarModel carModel = new CarModel();
        List<CarEntity> list = carModel.GetCars(filter);

        if(list.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setContentText("No se encontraron datos");
            alert.setHeaderText("Oops!!!");
            alert.show();
        }else{
            this.carsList.addAll(list);
            this.tbvCars.setItems(this.carsList);
        }

    }

    private void ConfigureTable(){

        tbcCarId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbcCarRegist.setCellValueFactory(new PropertyValueFactory<>("registration"));
        tbcCarBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        tbcCarModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        tbcCarColor.setCellValueFactory(new PropertyValueFactory<>("color"));

        //Double click Events
        tbvCars.setRowFactory( tv -> {
            TableRow<CarEntity> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) && event.getButton() == MouseButton.PRIMARY) {

                    CarEntity rowData = row.getItem();
                    try {
                        this.openUpdateView(rowData, row.getIndex());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            return row ;

        });

        tbvCars.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCode() == KeyCode.DELETE){
                    CarEntity car = (CarEntity) tbvCars.getSelectionModel().getSelectedItem();
                    temporalDeleteIndex = tbvCars.getSelectionModel().getSelectedIndex();
                    ConfirmDelete(car.getId());
                }
            }
        });
    }

    private void openUpdateView(CarEntity car, int index) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CarWashApplication.class.getResource("cars-views/car-update-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 350, 400);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Actualizar datos");
        stage.initModality(Modality.APPLICATION_MODAL);
        CarUpdateController controller = fxmlLoader.getController();
        controller.SetCarData(car, index, this);
        stage.setScene(scene);
        stage.show();
    }

    public void updateRow(CarEntity car, int index){

        this.carsList.set(index, car);
        tbvCars.refresh();
    }

    public void insertRow(CarEntity car){

        if(carsList.size() == 0){
            this.carsList.add(car);
            this.tbvCars.setItems(this.carsList);
        }else{
            this.carsList.add(car);
            this.tbvCars.refresh();
        }

    }

    @FXML
    private void openCreateView() throws  IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(CarWashApplication.class.getResource("cars-views/car-create-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 350, 400);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Añadir datos");
        stage.setScene(scene);
        CarCreateController controller = fxmlLoader.getController();
        controller.SetController(this);
        stage.show();
    }

    private void ConfirmDelete(int carId){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(Constants.SYSTEM_NAME);
        alert.setHeaderText("Advertencia");
        alert.setContentText("Esta a punto de eliminar el elemento de código "+carId );
        alert.showAndWait().ifPresent(response->{

            if(response.getButtonData() == ButtonBar.ButtonData.OK_DONE){
                this.deleteCar(carId);
            }

        });

    }

    private void deleteCar(int id){

        CarModel carModel = new CarModel();
        int status = carModel.deleteCar(id);

        if(status == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(Constants.SYSTEM_NAME);
            alert.setHeaderText("Mensaje");
            alert.setContentText("Los datos han sido eliminados correctamente.");
            alert.show();

            this.carsList.remove(temporalDeleteIndex);
            tbvCars.refresh();
        }
    }

}
