package BLL;

import BE.User;
import java.util.List;

public class ExportParser {
    public String parseUsers(List<User> users) {
        String output = "";
        output += "Name,Email,Phone,Address Secondary Address"+"\r";
        for (User user : users) {
            //String note = ""; 
            //if(user.getNote() != null) {
            //    note += user.getNote().replace(System.lineSeparator(), "\r");
            //}
            String name = (user.getName() != null) ? user.getName() : "";
            String email = (user.getEmail() != null) ? user.getEmail() : "";
            String phone = (user.getPhone() != 0) ? String.valueOf(user.getPhone()) : "";
            String address = (user.getResidence() != null) ? user.getResidence() : "";
            String address2 = (user.getResidence2() != null) ? user.getResidence2() : "";
            output+= name+","+email+","+phone+","+address+","+address2+"\n";
        }

        return output;
    }
    
    public String parseStats() {
        return "";
    }
    
    
}
