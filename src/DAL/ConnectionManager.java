/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Desmoswal
 */
public class ConnectionManager
  {

    private static final String CONFIG_FILE_NAME = "src//Resources//Connection.cfg";
    private final SQLServerDataSource ds;

    public ConnectionManager()
      {
        Properties props = new Properties();
        try
          {
            props.load(new FileReader(CONFIG_FILE_NAME));
          }
        catch (IOException ex)
          {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Manager: Missing config file.");
          }

        ds = new SQLServerDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
        System.out.println("Connection Established to database");
      }

    public Connection getConnection() throws SQLServerException
      {
        return ds.getConnection();
      }

  }
