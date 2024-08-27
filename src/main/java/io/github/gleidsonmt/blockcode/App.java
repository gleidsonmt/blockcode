package io.github.gleidsonmt.blockcode;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        BlockCode blockCodeHtml = new BlockCode(
                """
                        <!doctype html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport"
                                  content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
                            <meta http-equiv="X-UA-Compatible" content="ie=edge">
                            <title>BlockCode</title>
                            <link rel="stylesheet" href="style.css">
                                                    
                        <!--    <link rel="stylesheet" href="styles/github.min.css">-->
                                <link rel="stylesheet" href="styles/intellij-light.min.css">
                            <script src="highlight.min.js"></script>
                                                    
                        </head>
                        <body  style="background-color:  #F5F7FA;">
                        <pre id="cpre" >
                                <code id="block"  class="codeType-java"  style="background-color:  #F5F7FA;">
                                    // Nothing to see
                                </code>
                            </pre>
                        <script>hljs.highlightAll();</script>
                        </body>
                        </html>
                                                """,
                CodeType.HTML, Theme.INTELLIJ_LIGHT);

        BlockCode blockCodeJavascript = new BlockCode(
                """
                            function deepFreeze(obj) {
                            if (obj instanceof Map) {
                                obj.clear = obj.delete = obj.set = function () {
                                    throw new Error('map is read-only');
                                };
                            } else if (obj instanceof Set) {
                                obj.add = obj.clear = obj.delete = function () {
                                    throw new Error('set is read-only');
                                };
                            }

                            // Freeze self
                            Object.freeze(obj);

                            Object.getOwnPropertyNames(obj).forEach(function (name) {
                                var prop = obj[name];

                                // Freeze prop if it is an object
                                if (typeof prop == 'object' && !Object.isFrozen(prop)) {
                                    deepFreeze(prop);
                                }
                            });

                            return obj;
                        }
                                                """,
                CodeType.JAVASCRIPT);

        BlockCode blockCode = new BlockCode(
                """
                                @Override
                                    protected Skin<?> createDefaultSkin() {
                                        return new BlockCodeViewSkin(this);
                                    }

                                    public String getText() {
                                        return text.get();
                                    }

                                    public StringProperty textProperty() {
                                        return text;
                                    }

                                    public void setText(String text) {
                                        this.text.set(text);
                                    }
                        """
                );
//        BlockCodeView blockCodeView = new BlockCodeView(FXCollections.observableArrayList(blockCode));

        Tab tab = new Tab("java", blockCode);
        Tab tabJavascript = new Tab("java", blockCodeJavascript);
        Tab tabCodeHtml = new Tab("java", blockCodeHtml);
        TabPane tabView = new TabPane(tab, tabJavascript, tabCodeHtml);

        Scene scene = new Scene(tabView, 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}