package interfacermi;

import metier.Banque;

import java.rmi.Remote;
import java.util.List;

public interface Inscription_Interface extends Remote {
    public List<Banque> getChoixBanque() throws Exception;
    public boolean inscriptionClient(String nom, String prenom, String nomBanque, String login, String mdp) throws Exception;
}
