package metier;

public class LigneCommande extends Commande{

	public int idProduit;
	public int quantiteCommande;
	public int tarif;
	public LigneCommande(int idCommande, String date, int idProduit, int quantiteCommande, int tarif) {
		super(idCommande, date);
		this.idProduit = idProduit;
		this.quantiteCommande = quantiteCommande;
		this.tarif = tarif;
	}
	public int getIdProduit() {
		return idProduit;
	}
	public void setIdProduit(int idProduit) {
		this.idProduit = idProduit;
	}
	public int getQuantiteCommande() {
		return quantiteCommande;
	}
	public void setQuantiteCommande(int quantiteCommande) {
		this.quantiteCommande = quantiteCommande;
	}
	public int getTarif() {
		return tarif;
	}
	public void setTarif(int tarif) {
		this.tarif = tarif;
	}
	

}
