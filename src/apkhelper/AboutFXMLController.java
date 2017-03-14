/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package apkhelper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Oscar (AKA Bittle)
 */
public class AboutFXMLController implements Initializable {
    
    
    
    @FXML
    private Pane pane;
    
    @FXML
    private Text text;
        
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        text.setText("hey2\nhey3\nhey\nhey\nhey\nhey\nhey\nhey"
                + "\nhey\nhey\nhey\nhey\nhey\nhey\nhey\nhey\nhey\nhey"
                + "\nhey\nhey\nhey\nhey");

        Timeline timeline = new Timeline();
        final double ORIGIN_Y = text.getY();
        // Duration.seconds params: The bigger the number, the slower
        KeyFrame updateFrame = new KeyFrame(Duration.seconds(3 / 60d), (ActionEvent event) -> {
            double tH = text.getLayoutBounds().getHeight();
            double pH = pane.getHeight();
            double layoutY = text.getLayoutY();
            
            if (tH <= pH) {
                // stop, if the pane is large enough and the position is correct
                text.setLayoutY(ORIGIN_Y);
                timeline.stop();
            } else {
                System.out.println("tH = "+tH+", pH = "+pH+","
                    + " LayoutY = "+layoutY+"\n");
                
                if (Math.abs(layoutY)+100 >= tH) {
                    //bounds are reached
                    layoutY = ORIGIN_Y;
                }
                
                // update position
                layoutY -= 1;
                text.setLayoutY(layoutY);
            }
            
            
        });
        
        timeline.getKeyFrames().add(updateFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        
        // listen to bound changes of the elements to start/stop the animation
        InvalidationListener listener = o -> {
            double textHeight = text.getLayoutBounds().getHeight();
            double paneHeight = pane.getHeight();
            if (textHeight > paneHeight
                    && timeline.getStatus() != Animation.Status.RUNNING) {
                timeline.play();
            }
        };
        
        text.layoutBoundsProperty().addListener(listener);
        pane.widthProperty().addListener(listener);
    }
}
