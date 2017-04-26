
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import DAL.ConnectionManager;

/**
 *
 * @author meng
 */
public class S2ExamProject extends Application
  {

    @Override
    public void start(Stage stage) throws Exception
      {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/View/HourLoginView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
      }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
      {
        launch(args);
      }

  }