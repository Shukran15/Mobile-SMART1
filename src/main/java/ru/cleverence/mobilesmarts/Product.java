package ru.cleverence.mobilesmarts;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {

    private SimpleStringProperty shk;
    private SimpleStringProperty name;
    private SimpleIntegerProperty kolvo;
    private SimpleStringProperty description;


    public Product() {
        shk = new SimpleStringProperty();
        name = new SimpleStringProperty();
        kolvo = new SimpleIntegerProperty();
        description = new SimpleStringProperty();
    }

    public String getShk() {
        return shk.get();
    }

    public SimpleStringProperty shkProperty() {
        return shk;
    }

    public void setShk(String shk) {
        this.shk.set(shk);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getKolvo() {
        return kolvo.get();
    }

    public SimpleIntegerProperty kolvoProperty() {
        return kolvo;
    }

    public void setKolvo(int kolvo) {
        this.kolvo.set(kolvo);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
