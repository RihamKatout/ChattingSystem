module com.client.p2pclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.client.p2pclient to javafx.fxml;
    exports com.client.p2pclient;
}