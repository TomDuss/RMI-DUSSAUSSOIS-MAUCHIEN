package connexion;

import java.sql.*;

public class Connexion {

    public Connection creeConnexion() throws SQLException{
        String url = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/mauchien3u_rmi";
        url += "?serverTimezone=Europe/Paris";
        String login = "mauchien3u_appli";
        String pwd = "31807313";
        Connection maConnexion = null;
        try {
            maConnexion = DriverManager.getConnection(url, login, pwd);
            //System.out.println("la connection fonctionne");
        } catch (SQLException sqle) {
            System.out.println("Erreur connexion : " + sqle.getMessage());
        }
        return maConnexion;
    }


}
