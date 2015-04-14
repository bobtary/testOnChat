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
    protected enum typeEchange {CONNECT,DISCONNECT,DIALOGUE} ;
    protected enum sousTypeEchange {CONNECT,DISCONNECT};
    protected String typeMessage;
    protected String sender ;
    protected String receiver;
    //
    /*public Echange(typeEchange _typeofmess ){
        super("");
        this.typeMessage =  _typeofmess.toString();
    }*/
    /* types d'echange :  
    -> avec contenu : message de chat std : message a inclure dans le 
                        fil de discussion, le destinataire est soit le serveur
                        soit un partcipant du chat dont l emetteur
    -> sans contenu : message emis dans un echange serveur <-> client : soit lord
                        a la connexion, soit a la deconnexion
    */
    
    // constructeur pour message de chat 
    public Echange(String _data,String _src,String _dest ){
        super(_data);
        this.typeMessage = typeEchange.DIALOGUE.toString();
    }
    // constructeur pour echange connect/disconnect
    public Echange(sousTypeEchange _typeEch, String _src,String _dest ){
        super("");
        this.typeMessage =  _typeEch.toString(); 
    } 
    
}
