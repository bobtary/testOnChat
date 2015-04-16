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
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author bobtary
 */
public class ChatServeur {
    protected String adresse ; // TODO a modifier en adresse reseau
    protected int port; 
    
    protected ObjectOutputStream streamObjectOut=null;
    protected ObjectInputStream streamObjectIn=null;
    protected ServerSocket ssocket=null; // sans doute necessaire pour la deconnex
    protected ArrayList<String> onlineChatters = null;
    // initializer
    public ChatServeur(){this.adresse = "localhost"; this.port = 5000;}
    public ChatServeur(String _adr , int _p){this.adresse = _adr; this.port = _p;}
    // get and set
    public String getAdresse() {return this.adresse;} 
    public int getPort() {return this.port;}
    public void setAdresse(String _adr) {this.adresse = _adr;} 
    public void setPort(int _p) {this.port = _p;}
    
    // initilisation du tube de communication
    public void setSockServer() throws IOException {        
        if (this.ssocket!=null){return;} // init deje faite
        try {
            // ouverture d'une connexion TCP
            this.ssocket = new ServerSocket ( this.getPort());
        } catch (IOException ioe) { ioe.printStackTrace (); }
     }
    
    /* traitement d une demande de connexion de la part d un client
        -> initialisation du tube de communication
    
        TODO on gere les tentatives de connexion dans la methode principale
    */
    public boolean connectChatter(Socket socket,ObjectInputStream streamObjectIn,
            ObjectOutputStream streamObjectOut) {
        
        boolean bool=false;       
        Echange messRcv=null;
        
        System.out.println ("Waiting for a connection...");
        try {
            socket = this.ssocket.accept(); // attente active dun client
            // requête
            System.out.println ("Un client se connecte ...");
            streamObjectIn= new ObjectInputStream (socket.getInputStream ());
            System.out.println ("... il m'envoie ce message : ");
            messRcv = (Echange) streamObjectIn.readObject ();
            //System.out.println ("["+mess.toString ()+"]");
            if ( messRcv.getTypeMessage().matches("CONNECT_Q")){
                // identifier du chatter et validation si besoin
                if (!isOnline(messRcv.sender)) {bool=true;}
            } 
        }    
        catch (IOException | ClassNotFoundException ioe) { ioe.printStackTrace (); }
        return bool;
     }
    
    
    public boolean isOnline(String _pseudo) {
        boolean bool = false ;
        String onlinePs=null;
        Iterator itOnlines = this.onlineChatters.iterator() ;
        
        while (itOnlines.hasNext()) {
            onlinePs = itOnlines.next().toString() ;
            if (onlinePs.matches(_pseudo)) {bool=true; break;}
        }
        // recherche du pseudo dans le tableau
        
        
        return bool ;
    } 
    
    
    //public static void main (String argv[]) {
    public void run() {
            
            ObjectInputStream streamObjectIn=null;
            ObjectOutputStream streamObjectOut=null;               
            Socket socket=null;
            Message message=null;
            boolean inChat =false;
            
            
            // initialisation du port du serveur
            ChatServeur chatServ = new ChatServeur() ; 
            
            try { 
                //serveur = new ServerSocket (chatServ.getPort());}
                setSockServer(); 
            } catch (IOException ioe) {ioe.printStackTrace();}
            
            while (true) {
                try {
                    System.out.println ("Waiting for a connection...");
                    socket = this.ssocket.accept(); // attente active dun client
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

    
