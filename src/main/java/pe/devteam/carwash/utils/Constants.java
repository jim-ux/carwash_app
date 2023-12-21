package pe.devteam.carwash.utils;

import io.github.cdimascio.dotenv.Dotenv;

public class Constants {

    //private static final Dotenv dotenv = Dotenv.load();

    public static final String SYSTEM_NAME = "CarWash";

    //Database credentials
//    public static final String DB_DRIVER =  dotenv.get("DB_DRIVER");
//    public static final String DB_USER = dotenv.get("DB_USER");
//    public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
//    public static final String DB_URL = dotenv.get("DB_URL");

    public static final String DB_DRIVER =  "com.mysql.cj.jdbc.Driver";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/db_carwash?useSSL=false";
}
