package io.github.gleidsonmt.blockcode;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.FontSmoothingType;
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

    private EventHandler<ActionEvent> onCopying;

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

    private Button createCopyButton() {
        SVGPath icon = new SVGPath();
        icon.setContent("M360-240q-33 0-56.5-23.5T280-320v-480q0-33 23.5-56.5T360-880h360q33 0 56.5 23.5T800-800v480q0 33-23.5 56.5T720-240H360Zm0-80h360v-480H360v480ZM200-80q-33 0-56.5-23.5T120-160v-560h80v560h440v80H200Zm160-240v-480 480Z");
        icon.getStyleClass().add("icon");
        Button btn = new Button("Copy");
        btn.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Group group = new Group(icon);
        icon.setScaleX(0.03);
        icon.setScaleY(0.03);
        btn.setGraphic(group);
        btn.getStyleClass().add("copy-button");
        btn.getStyleClass().add("btn-flat");
        StackPane.setMargin(btn, new Insets(10));
        btn.setOnAction(event -> {

            ClipboardContent content = new ClipboardContent();
            content.putString(this.getContent());
            content.putHtml("<b>Bold</b> text");
            Clipboard.getSystemClipboard().setContent(content);

            if (onCopying != null) onCopying.handle(new ActionEvent(this, this));

//            this.fireEvent(onCopying);

//            context .createSnackBar()
//                    .icon(new IconContainer(Icons.DONE))
//                    .color(SnackColors.SUCCESS)
//                    .message("Copied!")
//                    .show();
        });
        return btn;
    }

    public void setOnCopying(EventHandler<ActionEvent> onCopying) {
        this.onCopying = onCopying;
    }

    public BlockCode build() {
        WebView webView = new WebView();
        webView.setContextMenuEnabled(false);
        webView.getEngine().setJavaScriptEnabled(true);
        webView.setFontSmoothingType(FontSmoothingType.LCD);
        webView.setMouseTransparent(true);
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
        this.getChildren().setAll(webView, createCopyButton());
        return this;
    }
}
