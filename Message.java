/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsonchat;
import java.io.Serializable ;
/**
 *
 * @author bobtary
 */

/* Le message sera a des contraintes a definir : longueur,..
*/
public class Message implements Serializable {
    
    private static final long serialVersionUID = -6500551252073842690L;
    private String data=null;
    public Message (String data) { this.data=data; }
    
    
    /* Mise en forme du message recu : 
    ex :  sur N carac  : [ PSEUDO_EMETTEUR ] data...
          idem         : suite ...
             ....      
    */
    public static String format(String texte,String emetteur, int taille) {
        String texteFormatte="";
        String texteF =  String.format("[ %s ] %s",emetteur,texte) ; // on concatene le pseudo et le message
        int lon = texteF.length() ;
        int mem=0;
        if (lon<taille){return texteF;}
        char [] tabTexteF =  new char[lon] ; 
        tabTexteF = texteF.toCharArray() ;
        int i=0;
        for (i=0; i<lon; i++) {
            if ( (i% taille == 0) & (i>=taille) ) {
            /*     texteFormatte.concat(tabTexteF[i]+"\r\n");}
            else {
                texteFormatte.concat(""+(tabTexteF[i]));
            }*/
                texteFormatte = texteFormatte + 
                        String.copyValueOf(tabTexteF, i-taille, taille) +
                        "\r\n";
                mem=i;
                
            }
           
            //System.out.println(texteFormatte);
        }
         if (mem < i ) {texteFormatte = texteFormatte +  
                    String.copyValueOf(tabTexteF, mem, lon-mem-1) +
                        "\r\n" ;}
        return texteFormatte ;
    }
    
    @Override
    public String toString () { return this.data; }
}