package apkhelper;

import apkhelper.utils.FileHelper;
import apkhelper.utils.ScriptRunner;
import apkhelper.utils.SoundPlayer;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class MainFXMLController implements Initializable{
    FileHelper fileHelper = new FileHelper();
    
    @FXML
    private TextField apk_text_field;
    
    @FXML
    private Button submit_button;
    
    // "or drag Folder below" text
    @FXML
    private Text drag_info_field;
    
    @Override
    public void initialize(URL url, ResourceBundle bundle){
        myInit();
    }
    
    private void myInit(){
        // place anything here that you want to initialize when
        // the program first open
        
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
        String option = submit_button.getText();
        
        if(option.equals("Decompile")){
            System.out.println("decompile");
            ScriptRunner.runJar("apktool.jar", "d", apk_text_field.getText());
        }
        else{
            if(option.contains("Compile")){
                // compile options
                System.out.println("compile");
                if(fileHelper.isDecompiledApkDir(apk_text_field.getText())){
                    ScriptRunner.runJar("apktool.jar", "b", 
                            apk_text_field.getText());
                }
                else{
                    System.out.println("Not an extracted apk folder");
                }
            }
            else{
                // create sign key
            }
        }
    }
    
    @FXML
    void search_button_clicked(MouseEvent event) {
        String option = drag_info_field.getText();
        
        if(option.toLowerCase().contains("apk")){
            chooseFile();
        }
        else{
            chooseDir();
        }
    }
    
    private void chooseFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose APK file");
        fileChooser.getExtensionFilters().addAll
            (new ExtensionFilter("APK files", "*.apk"));
        File file = fileChooser.showOpenDialog(null);
        if(file!=null){
            apk_text_field.setText(file.getAbsolutePath());
        }
    }
    
    private void chooseDir(){
        DirectoryChooser directory = new DirectoryChooser();
        File file = directory.showDialog(null);
        if(file!=null){
            apk_text_field.setText(file.getAbsolutePath());
        }
    }
    
    @FXML
    void compile_with_key_clicked(ActionEvent event) {
        submit_button.setText("Compile");
        drag_info_field.setText("or drag Folder below");
        
    }

    @FXML
    void create_sign_key_button_clicked(ActionEvent event) {
        //submit_button.setText("Create Key");
        // create sign key form here
        
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().
                    getResource("KeyStoreFXML.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Key Store");
            stage.setScene(new Scene(root));
            stage.show();
        }catch(java.io.IOException ex){
            System.out.println(ex.toString());
        }
    }

    @FXML
    void decompile_menu_item_clicked(ActionEvent event) {
        submit_button.setText("Decompile");
        drag_info_field.setText("or drag APK below");
    }
    
    @FXML
    void about_menu_clicked(ActionEvent event) {
         try{
             
            // open controller that holds About.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AboutFXML.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("About");
            stage.setScene(new Scene(root));  
            stage.setOnHiding((WindowEvent ev) -> {
                Platform.runLater(() -> {
                    AboutFXMLController.TIMELINE.stop();
                });
            });
            stage.show();
          }catch(Exception e){
              System.out.println("==== "+e.toString());
          }
    }
}
