package serveur;

import implementation.Banque_Implementation;
import implementation.Connexion_Implementation;
import implementation.Inscription_Implementation;
import implementation.OutilsSolvable_Implementation;
import interfacermi.Inscription_Interface;
import metier.Banque;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class Serveur_Banque {
    public static void main(String[] args) {
        try{
            int port = 8001;
            List<Banque> liste_banque = new ArrayList<Banque>();

            System.setProperty("java.rmi.server.hostname", "localhost");
            LocateRegistry.createRegistry(port);

            //inscription a une banque
            Inscription_Implementation inscription_implementation = new Inscription_Implementation();
            Naming.rebind ("rmi://localhost:"+port+"/inscription", inscription_implementation);


            // serveur multi banque
            Banque_Implementation banque_implementation = new Banque_Implementation();
            liste_banque = inscription_implementation.getChoixBanque();
            for(Banque banque: liste_banque){
                Naming.rebind ("rmi://localhost:"+port+"/"+banque.getNomBanque(), banque_implementation);
            }

            //connexion du client a l'interface banque
            Connexion_Implementation connexion_implementation = new Connexion_Implementation();
            Naming.rebind ("rmi://localhost:"+port+"/connexion", connexion_implementation);

            //connexion du serveur magasin aux serveur banque
            OutilsSolvable_Implementation outilsSolvable_implementation = new OutilsSolvable_Implementation();
            Naming.rebind ("rmi://localhost:"+port+"/commande", outilsSolvable_implementation);

            System.out.println ("Le serveur banque fonctionne !");

        }catch (Exception e) {
            System.out.println ("Le serveur banque ne fonctionne pas !  " +"\n"+ e);
        }
    }
}
