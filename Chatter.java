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
import java.util.ArrayList ;
import java.util.Iterator;


public class Chatter {
    protected String pseudo ;    
    protected ChatServeur chatServeur ;
    protected ObjectOutputStream streamObjectOut=null;
    protected ObjectInputStream streamObjectIn=null;
    protected Socket socket=null; // sans doute necessaire pour la deconnex
    protected ArrayList<String> scenario ;
    // ajouter un attribut d instance pour compter le nb de tchatter
    
    // initializer
    public Chatter(String _pseudo) { this.pseudo = _pseudo; this.chatServeur = new ChatServeur() ; }
    
    public Chatter(String _pseudo,ChatServeur _serveur) {this.pseudo = _pseudo;this.chatServeur= _serveur;}
    
    public Chatter (Personne _pers){this.pseudo = _pers.doPseudo();}
    
    public Chatter (Personne _pers,ChatServeur _serveur) {this.pseudo = _pers.doPseudo();this.chatServeur=_serveur;}
   
    //setter
    public void setPseudo(String _pseudo) { this.pseudo = _pseudo ;}
    
    public void setServeurChat(ChatServeur _serv) { this.chatServeur = _serv ;}
    
    // initilisation du tube de communication
    public void setStreamsAndSock() {        
        if (this.socket!=null){return;} // init deje faite
        try {
            // ouverture d'une connexion TCP
            this.socket = new Socket (this.chatServeur.getAdresse(), 
                this.chatServeur.getPort());
            this.streamObjectOut = new ObjectOutputStream (this.socket.getOutputStream ());
            this.streamObjectIn = new ObjectInputStream (this.socket.getInputStream ());
        } catch (IOException ioe) { ioe.printStackTrace (); }
     }
    
    // connection <-> trs  message type CONNECT_Q / rcp message type CONNECT_ACK
    // on gere les tentatives de connexion a un autre niveau
    public boolean connect() {
         boolean bool = false;
         Echange messRcv =null ;
         Echange connexEch = null ;
         
         connexEch = new Echange(Echange.typeEchange.CONNECT_Q, this.getPseudo());
         try {
            this.streamObjectOut.writeObject(connexEch);
            messRcv = (Echange) streamObjectIn.readObject() ;
            if (messRcv.getTypeMessage().toString().matches("CONNECT_ACK")) {
                bool=true;}
            }
          catch (IOException ioe) { ioe.printStackTrace (); }
          catch (ClassNotFoundException e) { e.printStackTrace (); }
          return bool ;
     }
    
    //getter
    public String getPseudo() { return this.pseudo ;}
    public ChatServeur getServeurChat() { return this.chatServeur;}
    public Socket getSocket(){return this.socket;}
    
    // pour tests : utilisation d un scenario <-> ens de phrases etablies
    public void setScenario(ArrayList<String> _scenar){
        this.scenario = _scenar;
        this.scenario.add("DISCONNECT"); // par precaution
       
    }
    
    
    public void dialogue() {
        
        
        Message message=null;
        Message messageOut=null;
        Integer alea = (int) Math.rint(Math.random() * 1000);
        Personne moimeme=new Personne("id_"+alea.toString(), this.pseudo);
        boolean inChat = false;
        try {
            //socket = new Socket (this.chatServeur.getAdresse(), this.chatServeur.getPort()); // ouverture d'une connexion TCP
            //streamObjectOut = new ObjectOutputStream (socket.getOutputStream ());
            // creation du message
            messageOut=new Message (String.format("Salut de la part de %s %s!",moimeme.getPrenom(),moimeme.getNom())); 
            streamObjectOut.writeObject (messageOut); // envoie vers le serveur de cette « requête »       
            
            // lecture de la réponse 
            //streamObjectIn = new ObjectInputStream (socket.getInputStream ());
            message = (Message) streamObjectIn.readObject() ;
            if (message.toString().matches("TALK")) {inChat=true;}
           
            //do {
               
            Iterator it =   this.scenario.iterator() ;
            while (it.hasNext() & inChat) {
                String toSend =  it.next().toString() ;
                messageOut =  new Message(toSend) ; 
                System.out.println(">"+toSend);
                streamObjectOut.writeObject (messageOut);
                if (toSend.matches("DISCONNECT")){inChat=false;break;}
                // lecture de la réponse                 
                message = (Message) streamObjectIn.readObject() ;
                System.out.println("Received :"+message.toString());
            }
            
           //} while(inChat);
          
         
            //streamObjectOut = new ObjectOutputStream (socket.getOutputStream ());
             // envoie vers le serveur de cette « requête »       
            // lecture de la réponse retournée : mise en attente ?
            //streamObjectIn = new ObjectInputStream (socket.getInputStream ());
            //System.out.println ("le serveur retourne : " + (Message) streamObjectIn.readObject ());
            
            
        } catch (IOException e) { e.printStackTrace (); }
         catch (ClassNotFoundException e) { e.printStackTrace (); }
        finally {
            if (socket!=null) {
                try { socket.close (); } 
                catch (IOException e) { e.printStackTrace ();} 
            }
        }
    }
    
    
    
    
}
