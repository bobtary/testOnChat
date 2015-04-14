package testsonchat;

import java.io.Serializable;

public class Personne implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom=null;
    private String prenom=null;

   public Personne (String nom, String prenom) {
        this.nom=nom;
        this.prenom=prenom;
    }
    public String getNom () { return nom; }
    public void setNom (String nom) { this.nom = nom; }
    public String getPrenom () { return prenom; }
    public void setPrenom (String prenom) { this.prenom = prenom; }
    public String inChaine() { 
       return String.format ("nom=[%s] prenom=[%s]",nom,prenom);
    } 
    public String doPseudo() { 
       return String.format ("%s.%s",nom,prenom);
    } 
}
