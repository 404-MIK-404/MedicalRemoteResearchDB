package sample.User;



public class User {

    private String Access;
    private String NameUser;
    private String PasswordUser;

    private static User userUseDB;


    private User(String access, String nameUser, String passwordUser) {
        Access = access;
        NameUser = nameUser;
        PasswordUser = passwordUser;
    }

    public String getAccess() {
        return Access;
    }

    public static User getUser(String access, String nameUser, String passwordUser){
        if (userUseDB == null){
            userUseDB = new User(access,nameUser,passwordUser);
        }
        return userUseDB;
    }

    public static User getUser(){
        if (userUseDB == null){
            return null;
        }
        return userUseDB;
    }


}
