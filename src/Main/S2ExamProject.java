package Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAL.DataManager;
import DAL.LoginManager;
import GUI.Controller.ManagerViewController;

/**
 *
 * @author meng
 */
public class S2ExamProject extends Application
  {

    @Override
    public void start(Stage stage) throws Exception
      {
        Parent root = FXMLLoader.load(getClass().getResource("/GUI/View/ManagerView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        DataManager dm = new DataManager();
        dm.getUserInfo(10);
        dm.getUserInfo(36);
        dm.getUserInfo(46);
        //dm.getAllUsers();
        
        LoginManager lm = new LoginManager();
        String date = "20170427";
        //id, date, hours, guildid
        lm.logHours(45, date, 2, 1);
      }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
      {
        launch(args);
      }

  }
