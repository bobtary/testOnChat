/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;

import java.io.ObjectInputStream ; 
import java.io.ObjectOutputStream ;
import java.net.Socket ;
import java.net.UnknownHostException;
import java.io.IOException;
// ajout pour tests
import java.lang.Math;
import java.lang.Thread ;
import java.lang.InterruptedException;
/**
 *
 * @author bobtary
 */
public class ClientTCP {
    
    public static void main (String[] args) {
        Socket socket=null;
        ObjectOutputStream streamObjectOut=null;
        ObjectInputStream streamObjectIn=null;
        Message message=null;
        Integer alea = (int) Math.rint(Math.random() * 1000);
        Personne moimeme=new Personne("num_"+alea.toString(),"steph");
        boolean inChat=false;
        try {
            socket = new Socket ("localhost", 5000); // ouverture d'une connexion TCP
            streamObjectOut = new ObjectOutputStream (socket.getOutputStream ());
            // creation du message
            message=new Message (String.format("Salut de la part de %s %s!",moimeme.getPrenom(),moimeme.getNom())); 
            streamObjectOut.writeObject (message); // envoie vers le serveur de cette « requête »       
            
            // lecture de la réponse 
           streamObjectIn = new ObjectInputStream (socket.getInputStream ());
           message = (Message) streamObjectIn.readObject() ;
           if (message.toString().matches("TALK")) {inChat=true;}
           
           do {
                message =  new Message("Mui bien ! ") ; 
                //System.out.println("output is up shutdown on client TCP? " + !socket.isOutputShutdown());
                streamObjectOut.writeObject (message);
            
           } while(inChat);
         
            //streamObjectOut = new ObjectOutputStream (socket.getOutputStream ());
             // envoie vers le serveur de cette « requête »       
            // lecture de la réponse retournée : mise en attente ?
            //streamObjectIn = new ObjectInputStream (socket.getInputStream ());
            //System.out.println ("le serveur retourne : " + (Message) streamObjectIn.readObject ());
            
            
        } catch (UnknownHostException e) { e.printStackTrace (); }
         catch (IOException e) { e.printStackTrace (); }
         catch (ClassNotFoundException e) { e.printStackTrace (); }
        finally {
            if (socket!=null) {
                try { socket.close (); } 
                catch (IOException e) { e.printStackTrace ();} 
            }
        }
    }
    
} // fin de classe
