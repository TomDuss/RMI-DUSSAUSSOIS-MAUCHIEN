package metier;

import java.io.Serializable;

public class Banque implements Serializable {

    public  int idBanque;
    public String NomBanque;

    public Banque() {
    }

    public Banque(int idBanque, String nomBanque) {
        this.idBanque = idBanque;
        NomBanque = nomBanque;
    }

    public int getIdBanque() {
        return idBanque;
    }

    public void setIdBanque(int idBanque) {
        this.idBanque = idBanque;
    }

    public String getNomBanque() {
        return NomBanque;
    }

    public void setNomBanque(String nomBanque) {
        NomBanque = nomBanque;
    }
}
