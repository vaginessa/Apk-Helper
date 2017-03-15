/*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
package apkhelper;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author Oscar (AKA Bittle)
 */
public class KeyStoreFXMLController implements Initializable {
    
    @FXML
    private PasswordField password_field;
    
    @FXML
    private CheckBox show_check_box;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        show_check_box.selectedProperty().addListener((ObservableValue<? 
                extends Boolean> observable, Boolean 
                        oldValue, Boolean newValue) -> {
            
            if(show_check_box.isSelected()){
                System.out.println("turning off");
            }
            else{
                show_check_box.setSelected(false);
            }
        });
        
    }
    
}