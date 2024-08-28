package io.github.gleidsonmt.blockcode;

import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLLinkElement;

import java.net.URL;
import java.util.Objects;
import java.util.Stack;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/08/2024
 */
public class BlockCodeDeclarative extends StackPane {

    private Theme theme = Theme.GITHUB;
    private CodeType codeType = CodeType.JAVA;
    private String content;


    public BlockCodeDeclarative() {
        this.setMinHeight(150);

    }

    public BlockCodeDeclarative theme(Theme _theme) {
        this.theme = _theme;
        return this;
    }

    public BlockCodeDeclarative codeType(CodeType _codeType) {
        this.codeType = _codeType;
        return this;
    }

    public BlockCodeDeclarative content(String _content) {
        this.content = _content;
        return this;
    }

    public void build() {
        this.setAlignment(Pos.TOP_RIGHT);
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webView.getEngine().setJavaScriptEnabled(true);

        URL url = App.class.getResource("web/index.html");

        webView.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        if (!content.isEmpty() && !content.isBlank()) {
                            Document doc = webView.getEngine().getDocument();

                            HTMLLinkElement link = (HTMLLinkElement) doc.getElementById("style");

                            String them = theme.name().toLowerCase().replaceAll("_", "-").replaceAll("\\$", "");
                            link.setHref("styles/"+ them +".min.css");

                            Element el = doc.getElementById("block");

                            el.setTextContent(content);
                            el.setAttribute("class", "language-" + codeType.toString().toLowerCase());

                            webView.getEngine().executeScript("hljs.highlightAll();");
                        }
                    }
                });

        webView.getEngine().load(Objects.requireNonNull(url).toExternalForm());
        this.getChildren().add(webView);
    }
}
