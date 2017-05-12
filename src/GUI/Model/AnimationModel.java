package GUI.Model;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AnimationModel
  {
    
    private final Image loaderImage = new Image("/Resources/animal.gif");
    
    private final StackPane stackPane;
    private final ImageView imgViewLoader;
    private final VBox vBoxcontainer;
    private final Label lblConnecting;
    
    public AnimationModel()
      {
        stackPane = new StackPane();
        stackPane.getStyleClass().add("loadingScreen");
        vBoxcontainer = new VBox();        
        vBoxcontainer.alignmentProperty().set(Pos.CENTER);
        
        imgViewLoader = new ImageView(loaderImage);
        imgViewLoader.maxWidth(150);
        imgViewLoader.setPreserveRatio(true);
        
        lblConnecting = new Label("Connecting to database...");
        lblConnecting.getStyleClass().add("logintextAndFontLabels");
        lblConnecting.setStyle("-fx-text-fill: #ffffff;");
        vBoxcontainer.getChildren().addAll(imgViewLoader, lblConnecting);
        
        stackPane.getChildren().add(vBoxcontainer);
        
      }
    
    public Image getLoaderImage()
      {
        return loaderImage;
      }

    /**
     * A fade out transition that needs a Duration and it fades in a given Node.
     *
     * @param dur
     * @param node
     * @return
     */
    public FadeTransition fadeInTransition(Duration dur, Node node)
      {
        FadeTransition ft = new FadeTransition(dur, node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        return ft;
      }

    /**
     * A fade out transition that needs a Duration and it fades out a given
     * Node.
     *
     * @param dur
     * @param node
     * @return
     */
    public FadeTransition fadeOutTransition(Duration dur, Node node)
      {
        FadeTransition ft = new FadeTransition(dur, node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
        return ft;
      }
    
    public StackPane getLoadingScreen()
      {
        return stackPane;
      }
    
  }
