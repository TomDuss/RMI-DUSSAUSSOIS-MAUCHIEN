package interfacermi;

import java.rmi.Remote;

public interface OutilsSolvable_Interface extends Remote {
    public String getBanque(String nom, String prenom) throws Exception;
    public int getIDClient(String nom, String prenom) throws Exception;

}
