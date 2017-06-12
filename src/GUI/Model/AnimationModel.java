package GUI.Model;

import com.jfoenix.controls.JFXSnackbar;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class AnimationModel
{

    private final Image loaderImage = new Image("/Resources/animal.gif");

    private StackPane stackPane;
    private ImageView imgViewLoader;
    private VBox vBoxcontainer;
    private Label lblConnecting;

    public AnimationModel()
    {

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
        stackPane = new StackPane();
        stackPane.getStyleClass().add("loadingScreen");
        vBoxcontainer = new VBox();
        vBoxcontainer.alignmentProperty().set(Pos.CENTER);
        
        AnchorPane.setBottomAnchor(stackPane, 0.0);
        AnchorPane.setLeftAnchor(stackPane, 0.0);
        AnchorPane.setRightAnchor(stackPane, 0.0);
        AnchorPane.setTopAnchor(stackPane, 0.0);
        
        imgViewLoader = new ImageView(loaderImage);
        imgViewLoader.maxWidth(150);
        imgViewLoader.setPreserveRatio(true);

        lblConnecting = new Label("Connecting to database...");
        lblConnecting.getStyleClass().add("logintextAndFontLabels");
        lblConnecting.setStyle("-fx-text-fill: #ffffff;");
        vBoxcontainer.getChildren().addAll(imgViewLoader, lblConnecting);

        stackPane.getChildren().add(vBoxcontainer);
        return stackPane;
    }

    public void snackbarPopup(String str, Pane parent)
    {
        int time = 6000;
        JFXSnackbar snackbar = new JFXSnackbar(parent);
        String errorString = ModelFacade.getModelFacade().getErrorString();
        if (errorString == null)
        {
            snackbar.show(str, time);
        }
        else
        {
            snackbar.show(ModelFacade.getModelFacade().getLang(errorString), time);
        }
    }

    public void timedSnackbarPopup(String str, Pane parent, int time)
    {
        JFXSnackbar snackbar = new JFXSnackbar(parent);
        String errorString = ModelFacade.getModelFacade().getErrorString();
        if (errorString == null)
        {
            snackbar.show(str, time);
        }
        else
        {
            snackbar.show(ModelFacade.getModelFacade().getLang(errorString), time);
        }
    }

}
