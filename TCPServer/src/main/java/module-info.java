module com.server.tcpserver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.server.tcpserver to javafx.fxml;
    exports com.server.tcpserver;
}