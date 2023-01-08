module com.example.pinocchio {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pinocchio to javafx.fxml;
    exports com.example.pinocchio;
}