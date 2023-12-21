package pe.devteam.carwash.models;

import javafx.scene.control.Alert;
import pe.devteam.carwash.config.Database;
import pe.devteam.carwash.dto.CarOilChangesDto;
import pe.devteam.carwash.utils.EasyAlert;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OilChangesModel {

    private Connection connection;

    public List<CarOilChangesDto> listOilChanges(String dateFilter, String carRegistFilter){

        List<CarOilChangesDto> list = new ArrayList<CarOilChangesDto>();
        String sqlQuery = "call SP_R_L_OILCHANGES( ?, ? )";

        try{

            Database database = new Database();
            this.connection = database.getConnection();

            CallableStatement statement = this.connection.prepareCall(sqlQuery);
            statement.setString(1, dateFilter);
            statement.setString(2, carRegistFilter);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                CarOilChangesDto oilChange = new CarOilChangesDto();
                oilChange.setId(rs.getInt(1));
                oilChange.setDate(rs.getTimestamp(2));
                oilChange.setCarRegist(rs.getString(3));
                oilChange.setActualKm(rs.getInt(4));
                oilChange.setNextKm(rs.getInt(5));

                list.add(oilChange);

            }

            statement.close();
            rs.close();

        }catch (SQLException ex){

            EasyAlert easyAlert = new EasyAlert("Código de error: "+ ex.getErrorCode(), ex.getSQLState(), Alert.AlertType.ERROR);
            easyAlert.Show();

        }finally {

            try{
                if(this.connection != null) this.connection.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return list;
    }
}
