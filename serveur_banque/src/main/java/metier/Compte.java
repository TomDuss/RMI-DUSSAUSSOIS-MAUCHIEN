package metier;

import java.io.Serializable;

public class Compte implements Serializable {
    private int idBanque;
    private int solde;
    private int idClient;

    public Compte(int idBanque, int solde, int idClient) {
        this.idBanque = idBanque;
        this.solde = solde;
        this.idClient = idClient;
    }

    public Compte() {
    }

    public int getIdBanque() {
        return idBanque;
    }

    public void setIdBanque(int idBanque) {
        this.idBanque = idBanque;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
}
