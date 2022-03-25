package interfacermi;

import metier.Banque;
import metier.Client;

import java.rmi.Remote;
import java.util.List;

public interface Connexion_Interface extends Remote {

    public List<Client> banqueConnexion(String login, String mdp) throws Exception;
    public List<Banque> serveurBanque(int idClient) throws Exception;
}
