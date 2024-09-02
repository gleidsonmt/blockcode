package io.github.gleidsonmt.blockcode;

import javafx.concurrent.Worker;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLLinkElement;

import java.net.URL;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/08/2024
 */
@SuppressWarnings("unused")
public class BlockCode extends StackPane {

    private Theme theme = Theme.GITHUB;
    private CodeType codeType = CodeType.JAVA;
    private String content;


    public BlockCode() {
        this.setMinHeight(150);
        this.setAlignment(Pos.TOP_RIGHT);

    }

    public BlockCode theme(Theme _theme) {
        this.theme = _theme;
        return this;
    }

    public BlockCode codeType(CodeType _codeType) {
        this.codeType = _codeType;
        return this;
    }

    public BlockCode content(String _content) {
        this.content = _content;
        return this;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public CodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BlockCode build() {
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

                            System.out.println("language-" + codeType.toString().toLowerCase());

                            Element el = doc.getElementById("block");

                            el.setTextContent(content);
                            el.setAttribute("class", "language-" + codeType.toString().toLowerCase());

                            webView.getEngine().executeScript("hljs.highlightAll();");
                        }
                    }
                });

        webView.getEngine().load(Objects.requireNonNull(url).toExternalForm());
        this.getChildren().setAll(webView);
        return this;
    }
}
