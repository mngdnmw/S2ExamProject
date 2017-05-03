package GUI.Model;

import Main.S2ExamProject;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class ViewChangerModel
  {

    private String ViewPath = "GUI/View/";
    private String HourLoginString = "HourLoginView.fxml";
    private String ManagerEditString = "ManagerEditView.fxml";
    private String ManagerString = "ManageView.fxml";
    private String UserInfoString = "UserInfoView.fxml";


    public void changeView(int GUINumb) throws IOException
      {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(S2ExamProject.class.getResource(switcher(GUINumb)));
        AnchorPane page = (AnchorPane) loader.load();

      }

    public String switcher(int GUINumb)
      {
        switch (GUINumb)
          {
            case 0:
                return ViewPath + HourLoginString;
            case 1:
                return ViewPath + ManagerEditString;
            case 2:
                return ViewPath + ManagerString;
            case 3:
                return ViewPath + UserInfoString;

          }
        return null;
      }
  }
