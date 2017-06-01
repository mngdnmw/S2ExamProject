package Main;

import BE.Guild;
import DAL.ConfigManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class S2ExamProject extends Application
  {

    @Override
    public void start(Stage stage) throws Exception
      {
        new ConfigManager();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/View/HourLoginView.fxml"));
        String title = "VMS - Volunteer Management System";
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
        stage.setTitle(title);
        
      }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
      {
        launch(args);
      }

  }
