module com.client.p2pclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.client.p2pclient to javafx.fxml;
    exports com.client.p2pclient;
}