package metier;

import java.io.Serializable;

public class Produit implements Serializable {
	public int idProduit;
	public String NomProduit;
	public int QuantiteProduit;
	public int idMagasin;
	public int prix;

	public Produit(int idProduit, String nomProduit, int quantiteProduit, int idmagasin, int Prix) {
		super();
		this.idProduit = idProduit;
		NomProduit = nomProduit;
		QuantiteProduit = quantiteProduit;
		idMagasin = idmagasin;
		prix = Prix;

	}
	public Produit() {
	}

	public int getIdProduit() {
		return idProduit;
	}
	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}
	public String getNomProduit() {
		return NomProduit;
	}
	public void setNomProduit(String nomProduit) {
		NomProduit = nomProduit;
	}
	public int getQuantiteProduit() {
		return QuantiteProduit;
	}
	public void setQuantiteProduit(int quantiteProduit) {
		QuantiteProduit = quantiteProduit;
	}

	public int getIdMagasin() {
		return idMagasin;
	}

	public void setIdMagasin(int idMagasin) {
		this.idMagasin = idMagasin;
	}

	public int getPrix() {
		return prix;
	}
	public void setPrix(int prix) {
		this.prix = prix;
	}

	@Override
	public String toString() {
		return "Produit{" +
				"idProduit=" + idProduit +
				", NomProduit='" + NomProduit + '\'' +
				", QuantiteProduit=" + QuantiteProduit +
				", idMagasin=" + idMagasin +
				", prix=" + prix +
				'}';
	}
}
