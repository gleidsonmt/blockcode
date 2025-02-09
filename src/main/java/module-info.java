module io.github.gleidsonmt.blockcode {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.web;
    requires java.desktop;
    requires jdk.xml.dom;

    opens io.github.gleidsonmt.blockcode to javafx.fxml;

    exports io.github.gleidsonmt.blockcode;
}