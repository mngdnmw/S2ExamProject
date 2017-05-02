package BLL;

public class BLLFacade
{
    DataHandler dataHandler = new DataHandler();
    
    public void addUser(String name, String email, String password, int type, int phone, String address, String note)
    {
        dataHandler.addUser(name, email, password, type, phone, address, note);
    }
}
