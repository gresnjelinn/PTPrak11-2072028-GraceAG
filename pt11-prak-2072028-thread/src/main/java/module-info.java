module com.example.pt04prak2072028jdbc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;


    opens com.example.pt04prak2072028jdbc to javafx.fxml;
    exports com.example.pt04prak2072028jdbc;
    exports com.example.pt04prak2072028jdbc.util;
    exports com.example.pt04prak2072028jdbc.model;
    exports com.example.pt04prak2072028jdbc.dao;
}