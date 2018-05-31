/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poas_td;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import poas_td.model.GameData;
import poas_td.view.GUI;

/**
 *
 * @author Avaddonica
 */
public class POAS_TD extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        /*
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);

        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        GUI gui = new GUI(primaryStage);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);
    }  
}