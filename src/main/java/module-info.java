module ru.cleverence.mobilesmarts {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.cleverence.mobilesmarts to javafx.fxml;
    exports ru.cleverence.mobilesmarts;
}