/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;

import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.File ;
import java.util.ArrayList ;
import java.util.Iterator;
/**
 *
 * @author bobtary
 */
public class RunMe {
    
    public static void main(String args[]) {
        Personne toto= new Personne("EL","MATADOR");
        //System.out.println("Caracteristiques de toto  :" + toto.inChaine());
        /*
        // serialisation de l objet
        saveObject (toto,"./toto.bin");
        if ( new File("./toto.bin").exists()) {System.out.println("le fichier toto.bin existe !") ; };
        
        // deserilisation de toto
        toto=null;
        try{
            toto=readObject("./toto.bin"); 
        } catch (ClassNotFoundException cnfe){cnfe.printStackTrace();} 
        System.out.println("Toto deseriliase : " + toto.inChaine());
        */
        
        String phrase2format = "Vous rentrez dans le chat" ;
        System.out.println(Message.format(phrase2format, "toto", 50)) ;
        
        
        // toto chat
        Chatter totolechat = new Chatter(toto);
        ArrayList<String> scenar=new ArrayList();
        scenar.add("[ "+totolechat.getPseudo()+" ] "+"Salut c toto");
        scenar.add("[ "+totolechat.getPseudo()+" ] "+"Je peux venir ?");
        scenar.add("[ "+totolechat.getPseudo()+" ] "+"je me casse, salut!");
        totolechat.setScenario(scenar);
        
        // bob l eponge
        Chatter bob = new Chatter("bob.leponge");
        scenar=new ArrayList();
        scenar.add("[ "+bob.getPseudo()+" ] "+"Salut c " + bob.getPseudo());
        scenar.add("[ "+bob.getPseudo()+" ] "+" it there anybody else?");
        scenar.add("[ "+bob.getPseudo()+" ] "+"i send this one");
        scenar.add("[ "+bob.getPseudo()+" ] "+"quit");
        bob.setScenario(scenar);

        // bibi 
        Chatter bib = new Chatter("bibendum");  
        scenar=new ArrayList();
        scenar.add("[ "+bib.getPseudo()+" ] "+"Salut I'm " + bib.getPseudo());
        scenar.add("[ "+bib.getPseudo()+" ] "+" it there anybody else?");
        scenar.add("[ "+bib.getPseudo()+" ] "+"I'm hungry");
        scenar.add("[ "+bib.getPseudo()+" ] "+"exit");
        bib.setScenario(scenar);
        totolechat.dialogue();
        bob.dialogue();
        bib.dialogue();
        
                
    }
    
    public static void saveObject (Personne personne, String filename) {
        try {
            FileOutputStream streamFileOut = new FileOutputStream (filename);
            ObjectOutputStream streamObjectOut = new ObjectOutputStream (streamFileOut);
            streamObjectOut.writeObject (personne);
            streamObjectOut.flush ();
            streamObjectOut.close ();
        }   catch (java.io.IOException e) {
               e.printStackTrace ();
        }
    }
    
    public static Personne readObject (String filename) throws ClassNotFoundException{
        FileInputStream streamFileIn=null;
        ObjectInputStream streamObjectIn = null ;
        Personne indiv=null;
        try {
            streamFileIn = new FileInputStream (filename);
            streamObjectIn = new ObjectInputStream (streamFileIn);
            try {
                indiv=(Personne) streamObjectIn.readObject();
            } catch (ClassNotFoundException e) { e.printStackTrace();}
            //streamObjectOut.flush ();
           
        }catch (IOException ioe) { 
            ioe.printStackTrace ();
        } finally{  
            if (streamObjectIn!=null){
                 try {
                    streamObjectIn.close();
                } catch (IOException ioe) {ioe.printStackTrace();}
            }
        }
        return indiv;
    }
    
} // fin de classe