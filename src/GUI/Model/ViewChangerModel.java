package GUI.Model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ViewChangerModel
{

    private String ViewPath = "GUI/View/";
    private String HourLoginString = "HourLoginView.fxml";
    private String ManagerString = "ManagerView.fxml";
    private String ManagerEditString = "ManagerEditView.fxml";
    private String UserInfoString = "UserInfoView.fxml";

    private Window stage;

    /**
     * Changes the view based on number. 0 goes to the UserInfoView, 1 goes to 
     * ManagerView, 2 goes to ManagerEditView, 3 goes to the hourLoginView
     *
     * @param GUINumb
     *
     */
    public void changeView(int GUINumb)
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(switcher(GUINumb)));
   
        try
        {
            AnchorPane page = (AnchorPane) loader.load();
            Stage nxtStage = new Stage();
            nxtStage.initOwner(stage);
            nxtStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            nxtStage.setScene(scene);
            nxtStage.show();

        } catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String switcher(int GUINumb)
    {
        switch (GUINumb)
        {
            case 0:
                return ViewPath + UserInfoString;
            case 1:
                return ViewPath + ManagerString;
            case 2:
                return ViewPath + ManagerEditString;
            case 3:
                return ViewPath + HourLoginString;

        }
        return null;
    }
}
