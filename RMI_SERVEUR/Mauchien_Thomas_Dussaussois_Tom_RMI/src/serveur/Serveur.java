package serveur;

import implementation.*;
import metier.Magasin;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

	public static void main(String[] args) {

		try {
			List<Magasin> liste_Magasin = new ArrayList<Magasin>();
			int port = 8000;

			//liste de magasins et de produits
			System.setProperty("java.rmi.server.hostname", "localhost");
			LocateRegistry.createRegistry(port);
			Liste_Magasin listeMagasin = new Liste_Magasin();
			Naming.rebind ("rmi://localhost:"+port+"/magasins", listeMagasin);


			//un serveur par magasin
			liste_Magasin = listeMagasin.getMagasins();
			Magasin_Implementation magasin_implementation = new Magasin_Implementation();
			for(Magasin magasin: liste_Magasin){
				Naming.rebind ("rmi://localhost:"+port+"/"+magasin.getNomMagasin(), magasin_implementation);
			}
			//connexion a l'appli
			Connexion_Implementation connexion_magasin = new Connexion_Implementation();
			Naming.rebind ("rmi://localhost:"+port+"/connexion", connexion_magasin);

			//passer une commande
			Commande_Implementation commande_implementation = new Commande_Implementation();
			for(Magasin magasin: liste_Magasin){
				Naming.rebind ("rmi://localhost:"+port+"/commande"+magasin.getNomMagasin(), commande_implementation);
			}


			//inscription a l'appli
			Inscription_Implementation inscription_implementation = new Inscription_Implementation();
			Naming.rebind ("rmi://localhost:"+port+"/inscription", inscription_implementation);

			System.out.println ("Server prêt !");

		} catch (Exception e) {
			System.out.println ("Le serveur magasin ne fonctionne pas !  " +"\n"+ e);
		}
	}
}

