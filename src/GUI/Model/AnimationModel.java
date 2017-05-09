package GUI.Model;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class AnimationModel
  {

    /**
     * A fade out transition that needs a Duration and it fades in a given Node.
     *
     * @param dur
     * @param node
     * @return
     */
    private Image loaderImage = new Image("file:Resources/animal.gif");

    public Image getLoaderImage()
      {
        return loaderImage;
      }

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

  }
