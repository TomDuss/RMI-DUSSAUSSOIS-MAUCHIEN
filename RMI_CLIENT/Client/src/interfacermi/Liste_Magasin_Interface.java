package interfacermi;

import metier.Magasin;
import metier.Produit;

import java.rmi.Remote;
import java.util.List;

public interface Liste_Magasin_Interface extends Remote {
    public List<Magasin> getMagasins() throws Exception;
    public List<Produit> getProduits(String nomMagasin) throws Exception;
}
