package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;


public class Main extends Application {

    VBox mainBox;
    HBox topBox;
    HBox bottomBox;
    HBox middleBox;
    HBox searchBox;

    Button BrowseButton;
    Button EncryptingButton;

    ChoiceBox choiceBox = new ChoiceBox();
    CheckBox checkBox = new CheckBox();

    TextField directory;
    TextField CipherKey;
    Label status;
    Label keyStatus;

    int selectedItem;

    @Override
    public void start(Stage primaryStage) throws Exception {
//******************************* Window GUI ****************************************
        Stage window = primaryStage;
        window.setTitle("Classic Cipher");
        window.setMinWidth(880);
        window.setMinHeight(200);

        mainBox = new VBox(10);
        mainBox.setPadding(new Insets(10, 10, 10, 10));
        topBox = new HBox(50);
        topBox.setPadding(new Insets(10, 10, 10, 10));
        bottomBox = new HBox(150);
        bottomBox.setPadding(new Insets(10, 10, 10, 10));
        middleBox = new HBox(50);
        middleBox.setPadding(new Insets(10, 10, 10, 10));
        searchBox = new HBox(50);
        searchBox.setPadding(new Insets(10, 10, 10, 10));


        status = new Label("Waiting For Input");

        status.setAlignment(Pos.CENTER_RIGHT);
        status.setPadding(new Insets(10, 10, 10, 10));


        EncryptingButton = new Button("Encrypt");
        BrowseButton = new Button("Browse");
        choiceBox.getItems().addAll("Caesar Cipher", "Playfair Cipher",
                 "Hill Cipher","Vigenère Cipher","Vernam Cipher");
        keyStatus = new Label();
        keyStatus.setVisible(false);
        checkBox.setVisible(false);
        directory = new TextField();
        directory.setEditable(true);
        directory.setMinWidth(500);
        directory.setPromptText("Select File...");

        CipherKey = new TextField();
        CipherKey.setEditable(true);
        CipherKey.setMinWidth(300);
        CipherKey.setPromptText("Enter Cipher Key Here...");

//**************************** Handling Requests **************************************
        BrowseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select directory");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            //fileChooser.setInitialDirectory(new File("C:\\"));
            File selectedFile = fileChooser.showOpenDialog(window);
            if(selectedFile == null){
                //No Directory selected
            }else{
                directory.setText(selectedFile.getAbsolutePath());
            }
        });

        choiceBox.setOnAction((event) -> {
            keyStatus.setVisible(false);
            checkBox.setVisible(false);
            selectedItem = choiceBox.getSelectionModel().getSelectedIndex();
            if(selectedItem == 0){ // Caesar Cipher
                keyStatus.setText("Enter Key as a number < 26");
                keyStatus.setVisible(true);
            }
            else if(selectedItem == 1){ // Playfair Cipher
                keyStatus.setText("Enter Key as word");
                keyStatus.setVisible(true);
            }
            else if(selectedItem == 2){ // Hill Cipher
                keyStatus.setText("Enter Key as a Matrix 2x2 or 3x3 on this form {{1,2},{3,4}}");
                keyStatus.setVisible(true);
            }
            else if(selectedItem == 3){ // Vigenère Cipher
                keyStatus.setText("Key as word,check box for Auto mode");
                keyStatus.setVisible(true);
                checkBox.setVisible(true);
            }
            else if(selectedItem == 4){ // Vernam Cipher
                keyStatus.setText("Enter Key as a word");
                keyStatus.setVisible(true);
            }
            else
                ;
            //System.out.println("Selection made: [" + selectedIndex + "] " + selectedItem);
            //System.out.println("   ChoiceBox.getValue(): " + choiceBox.getValue());
        });

        EncryptingButton.setOnAction(e -> {
            status.setText("Reading ");

            try {
                File myObj = new File(choiceBox.getSelectionModel().getSelectedItem().toString()+".txt");
                if (myObj.createNewFile()) {
                    System.out.println("File created: " + myObj.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException s) {
                System.out.println("An error occurred.");
                s.printStackTrace();
            }
            try {

                FileWriter myWriter = new FileWriter(choiceBox.getSelectionModel().getSelectedItem().toString()+".txt");
                FileReader fr = new FileReader(directory.getText());  //Creation of File Reader object
                BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
                String s;

                //************************ Execution of Cipher **************************
                while((s=br.readLine())!=null)   //Reading Content from the file
                {
                    if(selectedItem == 0){ // Caesar Cipher
                        CaesarCipher cipher = new CaesarCipher();
                        myWriter.write( cipher.Encrypt(s,Integer.parseInt(CipherKey.getText())) +"\n");
                    }
                    else if(selectedItem == 1){ // Playfair Cipher
                        PlayfairCipher cipher = new PlayfairCipher();
                        myWriter.write( cipher.Encrypt(s,CipherKey.getText())+"\n");
                    }
                    else if(selectedItem == 2){ // Hill Cipher
                        HillCipher cipher = new HillCipher();
                        myWriter.write( cipher.Encrypt(s,CipherKey.getText())+"\n");
                    }
                    else if(selectedItem == 3){ // Vigenère Cipher
                        VigenereCipher cipher = new VigenereCipher();
                        myWriter.write( cipher.Encrypt(s,CipherKey.getText(),checkBox.isSelected())+"\n");
                    }
                    else if(selectedItem == 4){ // Vernam Cipher
                        VernamCipher cipher = new VernamCipher();
                        myWriter.write( cipher.Encrypt(s,CipherKey.getText())+"\n");
                    }
                    else
                        ;
                }
                fr.close();
                //***********************************************************************
                myWriter.close();
                System.out.println("Successfully wrote to the file.");

            } catch (Exception o) {
                status.setText("Error!!!");
            }
            status.setText("Done.");
        });


//**************************** Scene Building ***************************************
        mainBox.getChildren().addAll(topBox, middleBox, searchBox,bottomBox);
        topBox.getChildren().addAll(choiceBox);
        middleBox.getChildren().addAll(CipherKey, keyStatus, checkBox);
        bottomBox.getChildren().addAll(status,EncryptingButton);
        searchBox.getChildren().addAll(directory, BrowseButton);
        // System.out.println(Arrays.deepToString(stringToMatrix("{{12,2},{3,4}}")));
        Scene scene = new Scene(mainBox);
        window.setScene(scene);
        window.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
