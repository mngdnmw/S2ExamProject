/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Desmoswal
 */
public class DataManager extends ConnectionManager {

    public void test() {
        String q = "select * from [user]";
        try (Connection con = super.getConnection()) {
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery(q);
            while(rs.next()) {
                System.out.println(rs.getString("name")+", "+rs.getString("type")+", "+rs.getString("phone")+", "+rs.getString("email")+", "+rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
