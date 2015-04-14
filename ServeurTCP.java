/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;
import java.net.ServerSocket ;
import java.net.Socket ;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException ;

/**
 *
 * @author bobtary
 */
public class ServeurTCP {
    
     public static void main (String argv[]) {
            ServerSocket serveur=null;
            ObjectInputStream streamObjectIn=null;
            ObjectOutputStream streamObjectOut=null;
            Message message=null;
            Socket socket=null;
            boolean inChat =false;
            
            try { serveur = new ServerSocket (5000);}
            catch (IOException ioe) {ioe.printStackTrace();}
            
            while (true) {
                try {
                    System.out.println ("Waiting for a connection...");
                    socket = serveur.accept (); // attente active dun client
                    // requête
                    System.out.println ("Un client se connecte ...");
                    streamObjectIn= new ObjectInputStream (socket.getInputStream ());
                    System.out.println ("Damned il m'envoie ce message : ");
                    message = (Message) streamObjectIn.readObject ();
                    System.out.println ("["+message.toString ()+"]");
                    
                    
                    // verification que le pseudo n est pas deja utilise, 
                    // entre autres verifications ?
                    inChat=true; // si ok    
                    // réponse
                    streamObjectOut= new ObjectOutputStream (socket.getOutputStream ());
                    do {    
                        System.out.println ("> TALK ");
                        //streamObjectOut= new ObjectOutputStream (socket.getOutputStream ());
                        message = new Message ("TALK");
                        streamObjectOut.writeObject (message);
                        System.out.print("Received message : ");
                        message = (Message) streamObjectIn.readObject ();
                        System.out.println ("["+message.toString ()+"]");
                        
                        // filtrage de la requette  : CONNECT / DISCONNECT / TALK
                        if (message.toString().matches("DISCONNECT")) {
                            inChat=false;
                            System.out.println("Fin de chat : salut...");
                            break; 
                        }
                   
                        
                    } while(inChat);
                    
                    
                 } 
                catch (ClassNotFoundException e) { e.printStackTrace (); }
                catch (IOException e) { e.printStackTrace (); }
                finally {
                    if (socket!=null) {
                        try { socket.close (); } catch (IOException e) { e.printStackTrace (); }
                     }
                 }
               } // boucle while
        }
} // fin clase
