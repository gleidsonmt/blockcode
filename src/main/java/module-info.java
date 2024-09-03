module io.github.gleidsonmt.blockcode {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires jdk.xml.dom;


    opens io.github.gleidsonmt.blockcode to javafx.fxml;
    exports io.github.gleidsonmt.blockcode;
}