package ru.cleverence.mobilesmarts;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobileSmartMain extends Application {


    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SkTable table = new SkTable();
    private Scene mainScene;
    private Scene firstStartScene;
    private Scene secondStartScene;
    private Scene thirdStartScene;
    private Scene showTableScene;

    private Stage primaryStage;



    // TODO: Ловить исключения при вызове метода parsInt
    @Override
    public void start(Stage stage) throws IOException {


        primaryStage = stage;
        initMainScene();
        initFirstScene();
        initShowTableScene();
     // initSecondScene();
        //  initThirdScene();
        stage.setScene(mainScene);

       // stage.setTitle("Привет!");
        stage.show();

    }

    public void initMainScene(){

        Button startButton = new Button("Начать");
        startButton.setMaxSize(300, 80);
        startButton.setOnAction(e -> {
            primaryStage.setScene(firstStartScene);
        });


        Button showButton = new Button("Просмотр");
        showButton.setMaxSize(300, 80);
        showButton.setOnAction(e -> {
            primaryStage.setScene(showTableScene);
        });

        Button tempExitButton = new Button("Временно выйти");
        tempExitButton.setMaxSize(300, 80);
        Button finishButton = new Button("Завершить");
        finishButton.setMaxSize(300, 80);

        VBox startPage = new VBox(10, startButton, showButton, tempExitButton, finishButton);

        startPage.setAlignment(Pos.CENTER);


        mainScene  = new Scene(startPage, 640, 480);

    }

    private void initFirstScene(){

        Product product = new Product();


        SkTable skTable = new SkTable();
        Label label = new Label("Введите шк");
        label.setMaxSize(300, 80);
        TextField textField = new TextField();
        textField.setMaxSize(300, 80);
        Button nextButton = new Button("далее");
        nextButton.setMaxSize(80, 80);
        nextButton.setOnAction(e -> {
            String shk = textField.getText();
            String name = skTable.getNameByShk(shk);
            product.setShk(shk);
            product.setName(name);
            initSecondScene(product);
            primaryStage.setScene(secondStartScene);
        });


        VBox shkVbox = new VBox(10, label, textField, nextButton);
        shkVbox.setAlignment(Pos.CENTER);
        firstStartScene = new Scene(shkVbox, 640, 480);

        firstStartScene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE){
                primaryStage.setScene(mainScene);
            }
        });




    }


    private void initSecondScene(Product product){


        Label label2 = new Label(product.getName());
        label2.setMaxSize(300, 80);
        Label label = new Label("Введите кол-во");
        label.setMaxSize(300, 80);
        TextField textField = new TextField();
        textField.setMaxSize(300, 80);
        Button nextButton = new Button("далее");
        nextButton.setMaxSize(80, 80);
        nextButton.setOnAction(e -> {
            int kolvo = Integer.parseInt(textField.getText());
            product.setKolvo(kolvo);
            initThirdScene(product);
            primaryStage.setScene(thirdStartScene);
        });
        VBox kolvoVbox = new VBox(10,label2, label, textField, nextButton);
        kolvoVbox.setAlignment(Pos.CENTER);
        secondStartScene = new Scene(kolvoVbox, 640, 480);

    }


    private void initThirdScene(Product product) {



        Label nameLabel = new Label(product.getName());
        nameLabel.setMaxSize(300,80);
        Label label = new Label("Введите описание");
        label.setMaxSize(300, 80);
        TextField textField = new TextField();
        textField.setMaxSize(300, 80);
        Button nextButton = new Button("следующий товар");
        nextButton.setMaxSize(80, 80);
        nextButton.setOnAction(e -> {

            String description = textField.getText();
            product.setDescription(description);
            products.add(product);

            initFirstScene();
            primaryStage.setScene(firstStartScene);
        });

        VBox descriptionVbox = new VBox(10, nameLabel, label, textField, nextButton);
        descriptionVbox.setAlignment(Pos.CENTER);
        thirdStartScene = new Scene(descriptionVbox, 640, 480);


    }

    private void initShowTableScene(){

        TableView<Product> productTable = new TableView<>(products);
        productTable.setPrefWidth(250);
        productTable.setPrefHeight(200);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Наименование товара");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productTable.getColumns().add(nameColumn);

        TableColumn<Product, String> kolvoColumn = new TableColumn<>("Количество товара");
        kolvoColumn.setCellValueFactory(new PropertyValueFactory<>("kolvo"));
        productTable.getColumns().add(kolvoColumn);

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("Состояние товара");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        productTable.getColumns().add(descriptionColumn);

        showTableScene = new Scene(productTable, 640, 480);

        showTableScene.setOnKeyPressed(e -> {
                    if (e.getCode() == KeyCode.ESCAPE) {
                        primaryStage.setScene(mainScene);
                    }
                }
        );


    }

    public static void main(String[] args) {
        launch();
    }
}