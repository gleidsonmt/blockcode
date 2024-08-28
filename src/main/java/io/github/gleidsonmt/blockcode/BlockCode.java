package io.github.gleidsonmt.blockcode;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLLinkElement;

import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/08/2024
 */
public class BlockCode extends StackPane {

    private final StringProperty content = new SimpleStringProperty();

    public BlockCode(String text) {
        this( text, CodeType.JAVA);
    }

    public BlockCode(String text, CodeType codeType) {
        this( text, codeType, Theme.GITHUB);
    }

    public BlockCode(String text, CodeType codeType, Theme theme) {
        content.setValue(text);
        this.setMinHeight(150);
        this.setAlignment(Pos.TOP_RIGHT);

        WebView webView = new WebView();
//        webView.setContextMenuEnabled(false);
        webView.getEngine().setJavaScriptEnabled(true);

        URL url = App.class.getResource("web/index.html");

        webView.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) ->
                        Platform.runLater(() -> {

                            if (newValue == Worker.State.SUCCEEDED) {
                                if (!text.isEmpty() && !text.isBlank()) {

                                    Document doc = webView.getEngine().getDocument();
                                    Element el = doc.getElementById("block");

                                    HTMLLinkElement link = (HTMLLinkElement) doc.getElementById("wow");
//                                    link.setHref("styles/intellij-light.min.css");
//                                    String them = theme.name().toLowerCase().replaceAll("_", "-");
//                                    link.setHref("styles/"+ them +".min.css");

                                    URL s = App.class.getResource("web/styles");
                                    File file = new File(s.getFile());
                                    for (String l : file.list()){
                                        String result = l.replaceAll("\\..*", "")
                                                .replaceAll("-", "_");
                                        System.out.println("" + result.toUpperCase() + ",");
                                    }

                                    if (!Objects.equals(codeType, "java")) {
                                        el.setAttribute("class", "codeType-" + codeType);
                                    }
                                    el.setTextContent(text);

                                }

//                                this.getChildren().setAll(webView, btn);
                                webView.getEngine().executeScript("hljs.highlightAll();");

                            }
                        })); // addListener()

        webView.getEngine().load(Objects.requireNonNull(url).toExternalForm());
        this.getChildren().add(webView);
//        URL url = context.g("web/index.html");
    }
}
