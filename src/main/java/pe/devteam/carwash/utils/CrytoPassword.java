package pe.devteam.carwash.utils;


import org.mindrot.jbcrypt.BCrypt;

public class CrytoPassword {

    private static int salts = 12;

    public static String hashPassword(String plainPassword){
        return  BCrypt.hashpw(plainPassword, BCrypt.gensalt(salts));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
