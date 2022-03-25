package implementation;

import connexion.Connexion;
import interfacermi.Liste_Magasin_Interface;
import metier.Magasin;
import metier.Produit;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Liste_Magasin extends UnicastRemoteObject implements Liste_Magasin_Interface {

	public Liste_Magasin() throws Exception {

	}
	Connexion c1 = new Connexion();
	public List<Magasin> getMagasins() throws Exception {

		List<Magasin> liste_Magasin = new ArrayList<Magasin>();

		try(Statement stmt = c1.creeConnexion().createStatement()){


			// executer la requete
			String sql = "SELECT * FROM magasin";
			ResultSet res = stmt.executeQuery(sql);

			//extration des données de ResultSet
			while(res.next()) {
				// Récupérer par nom de colonne
				int id = res.getInt("idMagasin");
				String nom = res.getString("NomMagasin");
				String contact = res.getString("contact");
				String typeMagasin = res.getString("TypeMagasin");

				// Définir les valeurs

				Magasin m = new Magasin();
				m.setIdMagasin(id);
				m.setNomMagasin(nom);
				m.setContact(contact);
				m.setTypeMagasin(typeMagasin);
				liste_Magasin.add(m);

			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		return liste_Magasin;
	}

}
