package pe.devteam.carwash.models;

import javafx.scene.control.Alert;
import pe.devteam.carwash.config.Database;
import pe.devteam.carwash.entities.CarEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarModel {

    private Connection conn;

    public List<CarEntity> GetCars(String filterRegist){

        List<CarEntity> list = new ArrayList<CarEntity>();

        String sqlQuery = "SELECT * FROM tbl_cars WHERE tbl_cars.car_rgst LIKE CONCAT('%' ,? ,'%')";

        try{
            Database database = new Database();
            this.conn = database.getConnection();
            PreparedStatement preparedStatement = this.conn.prepareStatement(sqlQuery);
            preparedStatement.setString(1, filterRegist);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                CarEntity carEntity = new CarEntity();
                carEntity.setId(resultSet.getInt(1));
                carEntity.setRegistration(resultSet.getString(2));
                carEntity.setBrand(resultSet.getString(3));
                carEntity.setModel(resultSet.getString(4));
                carEntity.setColor(resultSet.getString(5));

                list.add(carEntity);
            }

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Código de error: "+ex.getErrorCode());
            alert.setContentText(ex.toString());
            alert.show();
        }finally {

            try{

                if(this.conn != null){
                    this.conn.close();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return list;

    }

    public int UpdateCar(CarEntity car){

        int status = 2;

        String sqlQuery = "UPDATE tbl_cars SET car_rgst = ?, car_brnd = ?, car_modl = ?, car_colr = ? WHERE car_id LIKE ?";

        try{

            Database database = new Database();
            this.conn = database.getConnection();
            PreparedStatement statement = this.conn.prepareStatement(sqlQuery);
            statement.setString(1,car.getRegistration());
            statement.setString(2, car.getBrand());
            statement.setString(3, car.getModel());
            statement.setString(4, car.getColor());
            statement.setInt(5, car.getId());
            status = statement.executeUpdate();

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Código de error: "+ex.getErrorCode());
            alert.setContentText(ex.toString());
            alert.show();

        }finally {

            try{

                if(this.conn != null){
                    this.conn.close();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return status;
    }

    public CarEntity InsertUser(String carRegist,String carBrand, String carModel, String carColor){

        String sqlQuery = "{CALL SP_C_CAR(?,?,?,?)}";
        CarEntity car = null;

        try{
            Database database = new Database();
            this.conn = database.getConnection();
            CallableStatement statement = this.conn.prepareCall(sqlQuery);
            statement.setString(1,carRegist);
            statement.setString(2,carBrand);
            statement.setString(3, carModel);
            statement.setString(4, carColor);
            statement.execute();
            ResultSet rs = statement.getResultSet();

            while(rs.next()){
                car = new CarEntity();
                car.setId(rs.getInt(1));
                car.setRegistration(rs.getString(2));
                car.setBrand(rs.getString(3));
                car.setModel(rs.getString(4));
                car.setColor(rs.getString(5));


            }

            statement.close();
            rs.close();

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Código de error: "+ex.getErrorCode());
            alert.setContentText(ex.toString());
            alert.show();

        }finally {

            try{

                if(this.conn != null){
                    this.conn.close();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }

        return car;
    }

    public int deleteCar(int carId){

        int status = 2;
        String sqlQuery = "DELETE FROM tbl_cars WHERE car_id LIKE ?";

        try{
            Database database = new Database();
            this.conn = database.getConnection();
            PreparedStatement statement = this.conn.prepareStatement(sqlQuery);
            statement.setInt(1, carId);
            status = statement.executeUpdate();

            statement.close();

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Código de error: "+ex.getErrorCode());
            alert.setContentText(ex.toString());
            alert.show();

        }finally {

            try{

                if(this.conn != null){
                    this.conn.close();
                }

            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }


        return status;

    }


}
