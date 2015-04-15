/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;

/**
 *
 * @author bobtary
 */
public class Echange extends Message {
    protected enum typeEchange {CONNECT_ACK,CONNECT_REJ,CONNECT_Q,
    DISCONNECT_Q,DISCONNECT_ACK,DIAL,} ;    
    protected String typeMessage;
    protected String sender ;
    //   
    /* types d'echange :  
    -> avec contenu : message de chat std : message a inclure dans le 
                        fil de discussion, 
    soit d un chatter  pour le chat[vers le serveur ]    
    soit du chat [serveur] vers l..* chatter
    -> sans contenu : message emis dans un echange serveur <-> client : soit lord
                        a la connexion, soit a la deconnexion
    */
    
    
    // constructeur pour echange connect/disconnect
    public Echange(typeEchange _typeEch, String _src ){
        super(""); // pas de contenu
        this.typeMessage =  _typeEch.toString(); 
        this.sender = _src; 
    } 
    
    // constructeur pour message sur le chat [ emis ou recu] 
    public Echange(String _data,typeEchange _typeEch,String _src ){
        super(_data);
        this.typeMessage = typeEchange.DIAL.toString();
        this.sender = _src;
    }
    
    //setter STD
    public void setTypeMessage(typeEchange _typeEch) {this.typeMessage=_typeEch.toString();}
    public void setSender(String _src) {this.sender=_src;}

    //getter classique 
    public String getTypeMessage() {return this.typeMessage;}
    public String setSender() {return this.sender;}
   
    
    
    
    
}
