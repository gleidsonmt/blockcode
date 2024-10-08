package io.github.gleidsonmt.blockcode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javax.swing.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/08/2024
 */
public class BlockCodeView extends TabPane {

    private final BlockCode blockJava;
    private final BlockCode blockFXML;
    private final BlockCode blockCSS;

    private EventHandler<ActionEvent> onCopying;

    public BlockCodeView() {
        this(Theme.GITHUB);
    }

    public BlockCodeView(Theme theme) {
        Tab java = createTab("Java");
        Tab fxml = createTab("FXML");
        Tab css  = createTab("CSS");

        blockJava = new BlockCode().content("")
                .theme(theme).codeType(CodeType.JAVA).build();
        blockFXML = new BlockCode().content("")
                .theme(theme).codeType(CodeType.XML).build();
        blockCSS = new BlockCode().content("")
                .theme(theme).codeType(CodeType.CSS).build();

        java.setContent(blockJava);
        fxml.setContent(blockFXML);
        css.setContent(blockCSS);

        this.getTabs().setAll(java, fxml, css);

        for (Tab tab : this.getTabs()) {
            if (tab.getContent() instanceof BlockCode block) {
                block.getChildren().add(createCopyButton(block));
            }
        }
    }

    public void setJavaCode(String javaCode) {
        blockJava.setContent(javaCode);
    }

    public void setFXMLCode(String javaCode) {
        blockFXML.setContent(javaCode);
    }

    public void setCssCode(String cssCode) {
        blockCSS.setContent(cssCode);
    }

    private Tab createTab(String name) {
        Tab tab  = new Tab(name);
        tab.setClosable(false);
        return tab;
    }

    private Button createCopyButton(BlockCode code) {
        Button btn = new Button("Copy");
        btn.getStyleClass().add("copy-button");
        btn.setOnAction(event -> {

            ClipboardContent content = new ClipboardContent();
            content.putString(code.getContent());
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
}
