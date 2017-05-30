package DAL;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;

public class ConnectionManager extends ConfigManager
{

    //private static final String CONFIG_FILE_NAME = "src//Resources//Connection.cfg";
    private final SQLServerDataSource ds;

    public ConnectionManager()
    {
        /*Properties props = new Properties();
        try
          {
            props.load(new FileReader(CONFIG_FILE_NAME));
          }
        catch (IOException ex)
          {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection Manager: Missing config file.");
          }*/

        ds = new SQLServerDataSource();
        ds.setServerName(props.getProperty("SERVER"));
        ds.setDatabaseName(props.getProperty("DATABASE"));
        ds.setPortNumber(Integer.parseInt(props.getProperty("PORT")));
        ds.setUser(props.getProperty("USER"));
        ds.setPassword(props.getProperty("PASSWORD"));
    }

    protected Connection getConnection() throws SQLServerException
    {
        return ds.getConnection();
    }

}
