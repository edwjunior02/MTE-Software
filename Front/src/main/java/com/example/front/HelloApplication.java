package com.example.front;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.sound.sampled.*;
import javax.swing.*;


import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Thread.sleep;


enum LevelSelector {
    EASY,
    NORMAL,
    HARD
}

enum RangeSelector {
    SOPRANO,
    MEZZO_SOPRANO,
    ALTO,
    TENOR,
    BARITONO,
    BASS
}

/*enum ResultsSelector{
    PERFECT,
    WELL DONE,
    OKEY,
    TRY HARDER,
    MAYBE NEXT TIME,
    NOT GOOD
}*/

public class HelloApplication extends Application {
    LevelSelector ls;
    RangeSelector rs;

    AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

    TargetDataLine targetDataLine;

    private boolean grabar = false;
    private boolean testOk = false;

    private void lastscene(Stage stage){
        BorderPane gridborder = new BorderPane();
        GridPane gridPane = new GridPane();
        HBox hboxdesc = new HBox();
        StackPane stack = new StackPane();
        Text description = new Text("CONGRATULATIONS");
        description.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        description.setTextAlignment(TextAlignment.CENTER);
        hboxdesc.setMargin(description, new Insets(50, 0, 0, 230));
        hboxdesc.getChildren().add(description);
        gridborder.setTop(hboxdesc);

        Label notalbl = new Label("SING EVALUATION: ");
        notalbl.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        String nota = null;
        if(ls == LevelSelector.EASY) nota = "WELL DONE";
        if(ls == LevelSelector.NORMAL) nota = "OKEY";
        if(ls == LevelSelector.HARD) nota = "MAYBE NEXT TIME";
        Label notalbl2 = new Label(nota);
        notalbl2.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        Button buttonhome = new Button("HOME");
        buttonhome.setPrefSize(200, 40);
        EventHandler<ActionEvent> homeevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                firstScene(stage);
            }
        };
        buttonhome.setOnAction(homeevent);

        Button buttonretry = new Button("RETRY");
        buttonretry.setPrefSize(200, 40);
        EventHandler<ActionEvent> retryevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                thirdscene(stage);
            }
        };
        buttonretry.setOnAction(retryevent);

        Button buttonexit = new Button("EXIT");
        buttonexit.setPrefSize(200, 40);
        EventHandler<ActionEvent> exitevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                stage.close();
            }
        };
        buttonexit.setOnAction(exitevent);

        GridPane.setMargin(notalbl, new Insets(20, 0, 0, -100));
        GridPane.setMargin(notalbl2, new Insets(20, 0, 0, -60));
        GridPane.setMargin(buttonhome, new Insets(200, 0, 0, 110));
        GridPane.setMargin(buttonretry, new Insets(200, 0, 0, 80));
        GridPane.setMargin(buttonexit, new Insets(200, 0, 0, 70));

        gridPane.add(notalbl, 2, 1);
        gridPane.add(notalbl2, 3, 1);
        gridPane.add(buttonhome, 1, 3);
        gridPane.add(buttonretry, 2,3);
        gridPane.add(buttonexit, 3, 3);

        gridborder.setCenter(gridPane);
        Scene scene = new Scene(gridborder, 960, 540);
        stage.setScene(scene);
        stage.show();
    }

    class Audio extends Thread {
        byte[] temp = new byte[targetDataLine.getBufferSize() / 1000];
        AudioFileFormat.Type tipo = AudioFileFormat.Type.WAVE;
        File archivo = new File("grabacion.wav");
        public void run() {
            try {
                targetDataLine.open(format);
                targetDataLine.start();
                AudioSystem.write(new AudioInputStream(targetDataLine), tipo, archivo);
            } catch (Exception e){
                System.out.println(e);
            }
        }
    }
    private void thirdscene(Stage stage){
        //Crear aqui segunda escena
        StackPane stack = new StackPane();
        Text title = new Text("Music Tune Education");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextAlignment(TextAlignment.CENTER);
        HBox hbox = addHBox();

        //Imagen
        String nombreFichero = null;
        if(ls == LevelSelector.EASY) nombreFichero = "imagenes\\\\fotos_intervals\\\\soprano\\\\e4001-1.png";
        if(ls == LevelSelector.NORMAL) nombreFichero = "imagenes\\\\fotos_intervals\\\\soprano\\\\m8000-1.png";
        if(ls == LevelSelector.HARD) nombreFichero = "imagenes\\\\fotos_intervals\\\\soprano\\\\d11010-1.png";
        String rutaAbsoluta = new File(nombreFichero).getAbsolutePath();
        System.out.println(rutaAbsoluta);
        ImageView imageView = new ImageView(rutaAbsoluta);

        Button buttonsing = new Button("SING");
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                lastscene(stage);
            }
        };
        buttonsing.setOnAction(event1);
        Media buzzer = new Media(getClass().getResource("/LAmet.wav").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(buzzer);
        buttonsing.setOnAction(event -> {
            if(mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING){
                DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
                try {
                    targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
                    Thread audio = new Audio();
                    audio.start();
                    mediaPlayer.play();
                    sleep(15000);
                    targetDataLine.stop();
                    targetDataLine.close();
                    targetDataLine.flush();
                    lastscene(stage);
                } catch(Exception e) {
                    System.out.println(e);
                }
            }
        });

        Label textlbl = new Label("Press SING to start recording. Sing the notes from the following interval: ");
        textlbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        buttonsing.setPrefSize(200, 40);
        GridPane gridPane = new GridPane();
        gridPane.setMargin(textlbl, new Insets(150, 0, 0, 200));
        gridPane.add(textlbl, 1, 1);
        gridPane.setMargin(imageView, new Insets(50, 0, 0, 150));
        gridPane.add(imageView, 1, 2);
        gridPane.setMargin(buttonsing, new Insets(50, 0, 0, -80));
        gridPane.add(buttonsing, 2, 2);

        BorderPane border = new BorderPane();
        border.setTop(hbox);
        border.setCenter(gridPane);
        Scene scene = new Scene(border, 960, 540);
        stage.setScene(scene);
        stage.show();
    }

    private void secondscene(Stage stage) throws IOException {
        //Crear aqui segunda escena
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(960, 568);
        //Creating all the nodes.
        Circle testcircle = new Circle(50, Color.WHITE);
        testcircle.setLayoutX(482);
        testcircle.setLayoutY(280);
        testcircle.setRadius(46);
        pane.getChildren().add(testcircle);

        Button nextbutton = new Button("NEXT");
        nextbutton.setPrefSize(150, 50);
        nextbutton.setLayoutX(406);
        nextbutton.setLayoutY(387);
        nextbutton.setStyle("-fx-background-color: #336699;");
        nextbutton.setDisable(true); //by default
        pane.getChildren().add(nextbutton);

        Rectangle textField = new Rectangle();
        textField.setLayoutX(262);
        textField.setLayoutY(127);
        textField.setWidth(440);
        textField.setHeight(89);
        textField.setArcHeight(50);
        textField.setArcWidth(50);
        textField.setFill(Color.web("#336699"));
        textField.setSmooth(true);
        textField.setOpacity(0.6);
        pane.getChildren().add(textField);

        Label description = new Label("Select an able Microphone.");
        Label description2 = new Label("A green circle means the microphone selected works correctly.");
        Label description3 = new Label("A red circle means the microphone selected is not working correctly. ");
        Label description4 = new Label("Green circle enables the NEXT button.");
        description.setFont(Font.font("System", 14));
        description2.setFont(Font.font("System", 14));
        description3.setFont(Font.font("System", 14));
        description4.setFont(Font.font("System", 14));
        description.setTextFill(Color.WHITE);
        description2.setTextFill(Color.WHITE);
        description3.setTextFill(Color.WHITE);
        description4.setTextFill(Color.WHITE);
        description.setAlignment(Pos.CENTER);
        description2.setAlignment(Pos.CENTER);
        description3.setAlignment(Pos.CENTER);
        description4.setAlignment(Pos.CENTER);
        description.setLayoutX(395);
        description.setLayoutY(130);
        description2.setLayoutX(298);
        description2.setLayoutY(150);
        description3.setLayoutX(273);
        description3.setLayoutY(170);
        description4.setLayoutX(364);
        description4.setLayoutY(190);
        pane.getChildren().addAll(description, description2, description3, description4);

        ChoiceBox deviceSelection = new ChoiceBox();
        deviceSelection.setPrefSize(200, 40);
        deviceSelection.setLayoutX(382);
        deviceSelection.setLayoutY(341);
        pane.getChildren().add(deviceSelection);

        HBox hbox = addHBox();
        hbox.setAlignment(Pos.TOP_LEFT);
        hbox.setPrefWidth(960);
        pane.getChildren().add(hbox);

        Mixer.Info[] infos = AudioSystem.getMixerInfo(); //tenemos instancias de los dispositivos de audio instalados en el pc.
        LinkedList<Mixer.Info> infos_2 = new LinkedList<>();
        for(Mixer.Info info: infos) {
            if (info.getName().startsWith("Port") == false){
                infos_2.add(info);
            }
        }
        deviceSelection.getItems().addAll(infos_2);
        deviceSelection.setValue(infos_2.getFirst()); //by default the first MIDI device.

        deviceSelection.setOnAction((event) -> {
            int selectedIndex = deviceSelection.getSelectionModel().getSelectedIndex();
            try {
                TargetDataLine mic = AudioSystem.getTargetDataLine(new
                        AudioFormat(44100, 16, 1, true, true), infos_2.get(selectedIndex));
                System.out.println("Device works correctly!!!!");
                testcircle.setFill(Color.rgb(85, 255 , 0));
                this.testOk = true;
                nextbutton.setDisable(false);
            }catch(Exception e){
                testcircle.setFill(Color.RED);
                this.testOk = false;
                nextbutton.setDisable(true);
                System.out.println(e);
            }
        });

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (testOk){
                    thirdscene(stage);
                }
            }
        };

        nextbutton.setOnAction(event);
        Scene scene = new Scene(pane, 960, 540);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    private  HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        StackPane stack = new StackPane();
        Text title = new Text("Music Tune Education");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setTextAlignment(TextAlignment.CENTER);
        hbox.setMargin(title, new Insets(0, 0, 0, 350));
        hbox.getChildren().add(title);

        Rectangle helpIcon = new Rectangle(30.0, 25.0);
        helpIcon.setFill(new LinearGradient(0,0,0,1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                        new Stop(0, Color.web("#4977A3")),
                        new Stop(0.5, Color.web("#B0C6DA")),
                        new Stop(1,Color.web("#9CB6CF")),}));
        helpIcon.setStroke(Color.web("#D0E6FA"));
        helpIcon.setArcHeight(3.5);
        helpIcon.setArcWidth(3.5);

        EventHandler<ActionEvent> helpevent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Alert helpalert = new Alert(Alert.AlertType.INFORMATION);
                helpalert.setTitle("INFORMATION");
                helpalert.setHeaderText("\t\tSOFTWARE DOCUMENTATION");
                helpalert.setContentText("1. First you need to decide the level and your voice range, if you need your voice range you can enter in this link: \nhttps://www.omnicalculator.com/other/vocal-range\n" +
                        "2. Next you need to select a valid micro for singing\n3. We will give you an image with a reference note. You have 15 seconds to sing.");
                helpalert.showAndWait();
            }
        };
        Button helpButton = new Button("?");
        helpButton.setPrefSize(29, 23);
        helpButton.setOnAction(helpevent);

        stack.getChildren().addAll(helpIcon, helpButton);
        stack.setAlignment(Pos.CENTER_RIGHT);     // Right-justify nodes in stack
        StackPane.setMargin(helpButton, new Insets(1, 1, 0, 0));

        hbox.getChildren().add(stack);            // Add to HBox from Example 1-2
        HBox.setHgrow(stack, Priority.ALWAYS);    // Give stack any extra space

        return hbox;
    }

    private void firstScene(Stage stage){
        BorderPane border = new BorderPane();
        HBox hbox = addHBox();
        border.setTop(hbox);

        BorderPane gridborder = new BorderPane();
        border.setCenter(gridborder);
        GridPane gridPane = new GridPane();
        HBox hboxdesc = new HBox();
        StackPane stack = new StackPane();
        Text description = new Text("Welcome to Music Tune Education, here you will practice your intonation skills.\n Choose level and your voice range:");
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font("Arial", 18));
        hboxdesc.setMargin(description, new Insets(50, 0, 0, 150));
        hboxdesc.getChildren().add(description);
        gridborder.setTop(hboxdesc);

        ChoiceBox levelselect = new ChoiceBox();
        levelselect.setPrefSize(200, 40);
        levelselect.getItems().addAll(LevelSelector.values());
        levelselect.setValue(LevelSelector.values()[0]);
        ChoiceBox rangeselect = new ChoiceBox();
        rangeselect.setPrefSize(200, 40);
        rangeselect.getItems().addAll(RangeSelector.values());
        rangeselect.setValue(RangeSelector.values()[0]);
        Button buttonstart = new Button("START");

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                try {
                    secondscene(stage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                rs = (RangeSelector) rangeselect.getValue();
                ls = (LevelSelector) levelselect.getValue();
            }
        };

        buttonstart.setOnAction(event);
        buttonstart.setPrefSize(200, 40);

        GridPane.setMargin(levelselect, new Insets(140, 0, 0, 80));
        GridPane.setMargin(rangeselect, new Insets(140, 0, 0, 40));
        GridPane.setMargin(buttonstart, new Insets(140, 0, 0, 150));

        gridPane.add(levelselect, 1, 1);
        gridPane.add(rangeselect, 2,1);
        gridPane.add(buttonstart, 3, 1);

        gridborder.setCenter(gridPane);

        Scene scene = new Scene(border, 960, 540);
        stage.setTitle("Music Tune education!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {
        firstScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}