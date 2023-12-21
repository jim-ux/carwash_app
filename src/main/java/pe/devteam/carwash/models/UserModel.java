package pe.devteam.carwash.models;

import javafx.scene.control.Alert;
import pe.devteam.carwash.config.Database;
import pe.devteam.carwash.entities.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {

    private Connection conn;

    public UserEntity GetUser(String userName, String userPass){

        String sqlQuery = "select user_id, user_name,user_pass from tbl_users where user_name like ? and user_pass like ?";
        UserEntity userEntity = null;

        try{

            Database database = new Database();
            this.conn = database.getConnection();
            PreparedStatement statement = this.conn.prepareStatement(sqlQuery);
            statement.setString(1, userName);
            statement.setString(2, userPass);
            ResultSet rs = statement.executeQuery();



            if(rs.next()){

                userEntity = new UserEntity();

                while(rs.next()){

                    userEntity.setId(rs.getInt(1));
                    userEntity.setUserName(rs.getString(2));
                    userEntity.setPassword(rs.getString(3));

                }
            }

        }catch (SQLException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Código de error: "+ ex.getErrorCode());
            alert.setContentText(ex.getMessage());
            alert.setTitle("Error");
            alert.show();

        }finally {

            try{
                if(this.conn != null){
                    this.conn.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.toString());
            }
        }

        return userEntity;

    }


}
