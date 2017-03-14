package apkhelper;

import apkhelper.utils.FileHelper;
import apkhelper.utils.ScriptRunner;
import apkhelper.utils.SoundPlayer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class MainFXMLController implements Initializable{
    FileHelper fileHelper = new FileHelper();
    String option = "Decompile";
    
    @FXML
    private TextField apk_text_field;
    
    @FXML
    private Button submit_button;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle){
        myInit();
    }
    
    private void myInit(){
        // place anything here that you want to initialize when
        // the program first open
        submit_button.setVisible(false);
        
        // when text is changed (removed, backspace etc)
        apk_text_field.textProperty().addListener((obs, oldText, newText)->{
            if((fileHelper.isAPK(newText) && 
                    option.equals("Decompile"))
                    
                    || (fileHelper.isDecompiledApkDir(newText) && 
                    !option.equals("Decompile"))){
                
                
                submit_button.setVisible(true);
            }
            else{
                submit_button.setVisible(false);
            }
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
        if(success){
            SoundPlayer player = new SoundPlayer("dragAndDrop.wav");
            player.play();
        }
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    void submit_button_clicked(MouseEvent event) {
        SoundPlayer soundPlayer = new SoundPlayer("buttonClick.wav");
        soundPlayer.play();
        
        if(option.equals("Decompile")){
            ScriptRunner.runJar("apktool.jar", "d", apk_text_field.getText());
        }
        else{
            if(option.contains("Compile")){
                // compile options
                if(fileHelper.isDecompiledApkDir(apk_text_field.getText())){
                    ScriptRunner.runJar("apktool.jar", "b", 
                            apk_text_field.getText());
                }
            }
            else{
                // create sign key
            }
        }
    }
    
    @FXML
    void search_button_clicked(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose APK file");
        fileChooser.getExtensionFilters().addAll
            (new ExtensionFilter("APK files", "*.apk"));
        File file = fileChooser.showOpenDialog(null);
        if(file!=null){
            apk_text_field.setText(file.getAbsolutePath());
        }
        
        // directory chooser code
        //DirectoryChooser directory = new DirectoryChooser();
        //File f = directory.showDialog(null);
    }
    
    @FXML
    void compile_with_key_clicked(ActionEvent event) {
        if(fileHelper.isDecompiledApkDir(apk_text_field.getText())){
            submit_button.setVisible(true);
            option = "Compile W key";
            submit_button.setText("Compile");
        }
        
    }

    @FXML
    void compile_with_out_key_clicked(ActionEvent event) {
        if(fileHelper.isDecompiledApkDir(apk_text_field.getText())){
            submit_button.setVisible(true);
            option = "Compile W/O key";
            submit_button.setText("Compile");
        }
    }

    @FXML
    void create_sign_key_button_clicked(MouseEvent event) {
        if(fileHelper.isDecompiledApkDir(apk_text_field.getText())){
            submit_button.setVisible(true);
            option = "Create Key";
            submit_button.setText("Create Key");
        }
    }

    @FXML
    void decompile_menu_item_clicked(ActionEvent event) {
        System.out.println("decompile");
        option = "Decompile";
        submit_button.setText("Decompile");
    }
    
    @FXML
    void about_menu_clicked(ActionEvent event) {
         try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutFXML.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));  
            stage.show();
          }catch(Exception e){
              System.out.println("==== "+e.toString());
          }
    }
}
