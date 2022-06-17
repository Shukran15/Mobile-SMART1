package ru.cleverence.mobilesmarts;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class MobileSmartMain extends Application {

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();
    private PropertyLoader propertyLoader = new PropertyLoader();
    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SkTable table = new SkTable();
    private Scene mainScene;
    private Scene firstStartScene;
    private Scene secondStartScene;
    private Scene thirdStartScene;
    private Scene showTableScene;
    private Scene homeScene;

    private Stage primaryStage;


    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        initMainScene();
        initFirstScene();
        initShowTableScene();
        initHomeScene();
        stage.setScene(mainScene);

        stage.show();

    }

    public void initMainScene() {

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
        tempExitButton.setOnAction(e -> {
            primaryStage.setScene(homeScene);
        });

        Button finishButton = new Button("Завершить");
        finishButton.setMaxSize(300, 80);

        finishButton.setOnAction(e -> {
            String json = generateJson();
            //  sendDocument(json);
            System.out.println(json);

            products.clear();
            primaryStage.close();

        });

        VBox startPage = new VBox(10, startButton, showButton, tempExitButton, finishButton);

        startPage.setAlignment(Pos.CENTER);


        mainScene = new Scene(startPage, 640, 480);

    }

    private void initFirstScene() {
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
            if (e.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(mainScene);
            }
        });


    }


    private void initSecondScene(Product product) {
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
        VBox kolvoVbox = new VBox(10, label2, label, textField, nextButton);
        kolvoVbox.setAlignment(Pos.CENTER);
        secondStartScene = new Scene(kolvoVbox, 640, 480);

    }

    private void initThirdScene(Product product) {
        Label nameLabel = new Label(product.getName());
        nameLabel.setMaxSize(300, 80);
        Label label = new Label("Введите описание");
        label.setMaxSize(300, 80);
        TextField textField = new TextField();
        textField.setMaxSize(300, 80);
        Button nextButton = new Button("следующий товар");
        nextButton.setMaxSize(80, 80);
        nextButton.setOnAction(e -> {
            String description = textField.getText();
            product.setDescription(description);

            addToProducts(product);

            initFirstScene();
            primaryStage.setScene(firstStartScene);
        });

        VBox descriptionVbox = new VBox(10, nameLabel, label, textField, nextButton);
        descriptionVbox.setAlignment(Pos.CENTER);
        thirdStartScene = new Scene(descriptionVbox, 640, 480);
    }


    private void initShowTableScene() {

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

    private void initHomeScene() {
        Label sceneNameLabel = new Label("Домашняя страница");
        sceneNameLabel.setMaxSize(300, 80);

        Button toMainButton = new Button("Продолжить");
        toMainButton.setMaxSize(300, 80);

        toMainButton.setOnAction(e -> {
            primaryStage.setScene(mainScene);
        });


        VBox homePage = new VBox(10, sceneNameLabel, toMainButton);
        homePage.setAlignment(Pos.CENTER);

        homeScene = new Scene(homePage, 640, 480);
    }

    private void addToProducts(Product pr) {
        for (Product product : products) {
            if (product.getShk().equals(pr.getShk()) && product.getDescription().equals(pr.getDescription())) {
                product.setKolvo(product.getKolvo() + pr.getKolvo());
                return;
            }
        }
        products.add(pr);
    }


    private String generateJson() {
        String json = "";
        try {
            json = mapper.writeValueAsString(products);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }

    private void sendDocument(String json) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);

        String urlToSend = propertyLoader.readParam("url");

        ResponseEntity<String> response = restTemplate.postForEntity(urlToSend, entity, String.class);
    }

    public static void main(String[] args) {
        launch();
    }
}