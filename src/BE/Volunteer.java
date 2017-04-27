/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BE;

/**
 *
 * @author Desmoswal
 */
public class Volunteer extends User
{

    public Volunteer(int id, String name, String email, int type, int phone, String note)
    {
        super(id, name, email, 0, phone, note);
    }
    
}
