module com.example.q {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.intissar.q to javafx.fxml;
    exports com.intissar.q;
}