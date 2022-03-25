package interfacermi;

import metier.Produit;

import java.rmi.Remote;
import java.util.List;

public interface Magasin_Interface extends Remote{
    public List<Produit> getProduits(int id) throws Exception;
    public int getIdMagasin(String nomMagasin) throws Exception;
}