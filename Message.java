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
    @Override
    public String toString () { return this.data; }
}