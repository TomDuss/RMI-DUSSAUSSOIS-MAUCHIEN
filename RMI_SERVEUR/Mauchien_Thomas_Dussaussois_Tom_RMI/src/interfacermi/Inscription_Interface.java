package interfacermi;

import java.rmi.Remote;

public interface Inscription_Interface extends Remote {
    public boolean inscriptionClient(String nom, String prenom, String login, String mdp) throws Exception;
}
