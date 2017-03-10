package apkhelper;

import apkhelper.utils.FileHelper;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

public class MainFXMLController implements Initializable{
    @FXML
    private TextField apk_text_field;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle){
        myInit();
        System.out.println("init");
    }
    
    private void myInit(){
        // place anything here that you want to initialize when
        // the program first open
        
        
        // when text is changed (removed, backspace etc)
        apk_text_field.textProperty().addListener((obs, oldText, newText)->{
            System.out.println(FileHelper.fileExists(newText)?"File exists":
                    "File doesn't exist");
        });
    }
    
    @FXML
    void apk_text_field_on_drag_over(DragEvent event) {
        // this method is called when a file
        // is hovered over the text field
        Dragboard db = event.getDragboard();
        if(db.hasFiles()){
            event.acceptTransferModes(TransferMode.COPY);
        }
        else{
            event.consume();
        }
    }
    
    @FXML
    void apk_text_field_on_drag_dropped(DragEvent event) {
        // this method is called when a file is dropped
        // inside the text field
        Dragboard db = event.getDragboard();
        
        boolean success = false;
        if(db.hasFiles()){
            success = true;
            String path = null;
            for(File file: db.getFiles()){
                path = file.getAbsolutePath();
                apk_text_field.setText(path);
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }
}
