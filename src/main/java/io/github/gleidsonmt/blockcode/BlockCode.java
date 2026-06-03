package io.github.gleidsonmt.blockcode;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
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
    private Button copyButton;

    private EventHandler<ActionEvent> onCopying = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            ClipboardContent content = new ClipboardContent();
            content.putString(getContent());
            content.putHtml("<b>Bold</b> text");
            Clipboard.getSystemClipboard().setContent(content);
//
//            if (onCopying != null) onCopying.handle(new ActionEvent());
            copyButton.setText("Copied!");
        }
    };

    public BlockCode() {
        this.setMinHeight(300);
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

    public BlockCode copy(Button button) {
        this.copyButton = button;
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

    protected Button createCopyButton() {
        copyButton = new Button("Copy");
        copyButton.getStyleClass().add("copy-button");
        StackPane.setMargin(copyButton, new Insets(20));
        return copyButton;
    }

    public Button getCopyButton() {
        return this.copyButton;
    }

    public void setOnCopying(EventHandler<ActionEvent> onCopying) {
        this.onCopying = onCopying;
    }

    public BlockCode build() {
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webView.getEngine().setJavaScriptEnabled(true);
//        webView.setMouseTransparent(true);
        URL url = App.class.getResource("web/index.html");
//        Font font = Font.loadFont(getClass().getResourceAsStream("font/JetBrains-Mono-Regular.ttf"), 12);

        System.out.println(Font.getFontNames());
        webView.getEngine().getLoadWorker().stateProperty()
                .addListener((obs, oldValue, newValue) -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        if (!content.isEmpty() && !content.isBlank()) {
                            Document doc = webView.getEngine().getDocument();

                            HTMLLinkElement link = (HTMLLinkElement) doc.getElementById("style");

                            String them = theme.name().toLowerCase().replaceAll("_", "-").replaceAll("\\$", "");
                            link.setHref("styles/" + them + ".min.css");

                            Element el = doc.getElementById("block");

                            el.setTextContent(content);
                            el.setAttribute("class", "language-" + codeType.toString().toLowerCase());

//                                Platform.requestNextPulse();
//                                Platform.runLater(() -> {
                            webView.getEngine().executeScript("hljs.highlightAll();");
//                                });

                        }
                    }
                });

        webView.getEngine().load(Objects.requireNonNull(url).toExternalForm());
        if (copyButton == null) {
            copyButton = createCopyButton();
        }

        copyButton.setOnAction(onCopying);
        this.getChildren().setAll(webView, copyButton);
        return this;
    }
}