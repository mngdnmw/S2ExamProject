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
public class Admin extends User
{

    public Admin(int id, String name, String email, int type, int phone, String note)
    {
        super(id, name, email, 2, phone, note);
    }
    
}
