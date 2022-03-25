package metier;

import java.io.Serializable;

public class Client implements Serializable {
    public int idClient;
    public  String NomClient;
    public  String login;
    public  String MotDePasse;
    public  String PrenomClient;

    public Client(int idclient, String nomClient, String Login, String motdepasse, String prenomClient) {
        super();
        idClient = idclient;
        NomClient = nomClient;
        login = Login;
        MotDePasse = motdepasse;
        PrenomClient = prenomClient;
    }

    public Client() {
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return NomClient;
    }

    public void setNomClient(String nomClient) {
        NomClient = nomClient;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return MotDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        MotDePasse = motDePasse;
    }

    public String getPrenomClient() {
        return PrenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        PrenomClient = prenomClient;
    }
}
