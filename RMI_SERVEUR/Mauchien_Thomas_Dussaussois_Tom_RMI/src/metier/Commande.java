package metier;

public class Commande {

	public int idCommande;
	public String date;
	
	public Commande(int idCommande, String date) {
		super();
		this.idCommande = idCommande;
		this.date = date;
	}
	public int getIdCommande() {
		return idCommande;
	}
	public void setIdCommande(int idCommande) {
		this.idCommande = idCommande;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
