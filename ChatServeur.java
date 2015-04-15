/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author bobtary
 */
public class ChatServeur {
    private String adresse ; // TODO a modifier en adresse reseau
    private int port; 
    // initializer
    public ChatServeur(){this.adresse = "localhost"; this.port = 5000;}
    public ChatServeur(String _adr , int _p){this.adresse = _adr; this.port = _p;}
    // get and set
    public String getAdresse() {return this.adresse;} 
    public int getPort() {return this.port;}
    public void setAdresse(String _adr) {this.adresse = _adr;} 
    public void setPort(int _p) {this.port = _p;}
    
    //
    //public static void main (String argv[]) {
    public void run() {
            ServerSocket serveur=null;
            ObjectInputStream streamObjectIn=null;
            ObjectOutputStream streamObjectOut=null;
            Message message=null;
            Socket socket=null;
            boolean inChat =false;
            
            
            // initialisation du port du serveur
            ChatServeur chatServ = new ChatServeur() ; 
            
            try { serveur = new ServerSocket (chatServ.getPort());}
            catch (IOException ioe) {ioe.printStackTrace();}
            
            while (true) {
                try {
                    System.out.println ("Waiting for a connection...");
                    socket = serveur.accept (); // attente active dun client
                    // requête
                    System.out.println ("Un client se connecte ...");
                    streamObjectIn= new ObjectInputStream (socket.getInputStream ());
                    System.out.println ("... il m'envoie ce message : ");
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
                        //Echange message = newEchange(Echange.typeEchange _typeEch, String _src,String _dest);
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

    
