package DAL;

public class DALFacade
{
    DataManager dataManager = new DataManager();
    
    public void addUser(String name, String email, String password, int type, int phone, String address, String note)
    {
        dataManager.addUser(name, email, password, type, phone, address, note);
    }
}
