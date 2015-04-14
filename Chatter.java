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
    protected ArrayList<String> scenario ;
    // ajouter un attribut d instance pour compter le nb de tchatter
    
    // initializer
    public Chatter(String _pseudo) {this.pseudo = _pseudo;}
    public Chatter (Personne _pers) {this.pseudo = _pers.doPseudo();}
    public void setPseudo(String _pseudo) { this.pseudo = _pseudo ;}
    public String getPseudo() { return this.pseudo ;}
    
    
    public void setScenario(ArrayList<String> _scenar){
        this.scenario = _scenar;
        this.scenario.add("DISCONNECT"); // precaution
    }
    
    
    public void dialogue() {
        Socket socket=null;
        ObjectOutputStream streamObjectOut=null;
        ObjectInputStream streamObjectIn=null;
        Message message=null;
        Message messageOut=null;
        Integer alea = (int) Math.rint(Math.random() * 1000);
        Personne moimeme=new Personne("id_"+alea.toString(), this.pseudo);
        boolean inChat = false;
        try {
            socket = new Socket ("localhost", 5000); // ouverture d'une connexion TCP
            streamObjectOut = new ObjectOutputStream (socket.getOutputStream ());
            // creation du message
            messageOut=new Message (String.format("Salut de la part de %s %s!",moimeme.getPrenom(),moimeme.getNom())); 
            streamObjectOut.writeObject (messageOut); // envoie vers le serveur de cette « requête »       
            
            // lecture de la réponse 
            streamObjectIn = new ObjectInputStream (socket.getInputStream ());
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
    
    
    
    
}
