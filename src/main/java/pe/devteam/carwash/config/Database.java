package pe.devteam.carwash.config;

import javafx.scene.control.Alert;
import pe.devteam.carwash.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection conn = null;

    public Connection getConnection(){

        try{

            Class.forName(Constants.DB_DRIVER);
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);

        }catch (ClassNotFoundException | SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Ha ocurrido un error");
            alert.setContentText(ex.toString());
            alert.show();

        }

        return this.conn;
    }


}
