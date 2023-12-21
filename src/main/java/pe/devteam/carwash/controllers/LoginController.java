package pe.devteam.carwash.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import pe.devteam.carwash.CarWashApplication;
import pe.devteam.carwash.entities.UserEntity;
import pe.devteam.carwash.models.UserModel;
import pe.devteam.carwash.utils.CrytoPassword;
import pe.devteam.carwash.utils.EasyAlert;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField pwfPassword;

    @FXML
    private ImageView imvLogin;

    @FXML
    private void initialize(){

        Image picture = new Image(CarWashApplication.class.getResourceAsStream("images/coche.png"));
        imvLogin.setImage(picture);

    }

    @FXML
    private void LoginUser(){

        try{

            String userName = this.txtUserName.getText();
            String password = this.pwfPassword.getText();

            if(userName.isEmpty() || password.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Campos vacíos");
                alert.setContentText("Rellene todos los campos para continuar.");
                alert.show();
                return;
            }


            UserModel userModel = new UserModel();
            UserEntity userEntity = userModel.GetUser(userName);

            if(userEntity.getUserName() == null){

                EasyAlert alert = new EasyAlert("Usuario no encontrado", "Verifique los campos ingresados, por favor.", Alert.AlertType.ERROR);
                alert.Show();

            }else{

                if(CrytoPassword.checkPassword(password, userEntity.getPassword())){

                    this.OpenMainWindow();

                }else{

                    EasyAlert alert = new EasyAlert("Error de contraseña", "Verifique sus credenciales e ingrese de nuevo", Alert.AlertType.ERROR);
                    alert.Show();
                }

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    private void OpenMainWindow() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(CarWashApplication.class.getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        Stage stage = new Stage();
        stage.setTitle("Principal");
        stage.setScene(scene);
        stage.show();

        ((Stage) txtUserName.getScene().getWindow()).close();
    }
}
