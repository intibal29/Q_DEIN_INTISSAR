module com.example.q {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.q to javafx.fxml;
    exports com.example.q;
}