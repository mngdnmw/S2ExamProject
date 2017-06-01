package GUI.Model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ViewChangerModel
{

    private String ViewPath = "GUI/View/";
    private String HourLoginString = "HourLoginView.fxml";
    private String ManagerString = "ManagerView.fxml";
    private String UserInfoString = "UserInfoView.fxml";
    private String ManagerAddUserString = "ManagerAddUserView.fxml";

    private boolean userInfoView = true;
    String title = "";

    private Window stage;
    private Stage nxtStage;
    /**
     * Changes the view based on number. 0 goes to the UserInfoView, 1 goes to
     * ManagerView, 2 goes to ManagerEditView, 3 goes to the hourLoginView, 4
     * goes to ManagerAddUserView
     *
     * @param GUINumb
     *
     */
    public void changeView(int GUINumb)
    {
        FXMLLoader loader = new FXMLLoader();
        String Path = switcher(GUINumb);
        loader.setLocation(getClass().getClassLoader().getResource(Path));

        try
        {
            Pane page = (Pane) loader.load();
            nxtStage = new Stage();
            nxtStage.initOwner(stage);
            nxtStage.initModality(Modality.WINDOW_MODAL);
            setNxtStage(nxtStage);
            Scene scene = new Scene(page);
            nxtStage.setScene(scene);
            nxtStage.show();
            nxtStage.setTitle(title);
        }
        catch (IOException ex)
        {
            Logger.getLogger(ViewChangerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String switcher(int GUINumb)
    {
        switch (GUINumb)
        {
            case 0:
                title = "VMS - User Information";
                userInfoView = true;
                return ViewPath + UserInfoString;
            case 1:
                title = "VMS - Manager";
                return ViewPath + ManagerString;
            case 2:
                title = "VMS - Manager Editing";
                userInfoView = false;
                return ViewPath + UserInfoString;
            case 3:
                title = "VMS - Volunteer Managing System";
                return ViewPath + HourLoginString;
            case 4:
                title = "VMS - Add New volunteer";
                return ViewPath + ManagerAddUserString;

        }
        return null;
    }

    public boolean isUserInfoView()
    {
        return userInfoView;
    }

    public Stage getNxtStage()
    {
        return nxtStage;
    }

    public void setNxtStage(Stage nxtStage)
    {
        this.nxtStage = nxtStage;
    }

}
