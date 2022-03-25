package metier;

import java.io.Serializable;

public class Magasin implements Serializable {
	
	public int idMagasin;
	public String NomMagasin;
	public String Contact;
	public String TypeMagasin;
	
	
	public Magasin(int idMagasin, String nomMagasin, String contact, String typeMagasin) {
		super();
		this.idMagasin = idMagasin;
		NomMagasin = nomMagasin;
		Contact = contact;
		TypeMagasin = typeMagasin;
	}

	public Magasin() {
	}

	public int getIdMagasin() {
		return idMagasin;
	}
	public void setIdMagasin(int idMagasin) {
		this.idMagasin = idMagasin;
	}
	public String getNomMagasin() {
		return NomMagasin;
	}
	public void setNomMagasin(String nomMagasin) {
		NomMagasin = nomMagasin;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getTypeMagasin() {
		return TypeMagasin;
	}
	public void setTypeMagasin(String typeMagasin) {
		TypeMagasin = typeMagasin;
	}

}
