package io.github.gleidsonmt.blockcode;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/08/2024
 */
@SuppressWarnings("unused")
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

    }

    @Deprecated
    public void setJavaCode(String javaCode) {
        blockJava.setContent(javaCode);
    }

    @Deprecated
    public void setFXMLCode(String javaCode) {
        blockFXML.setContent(javaCode);
    }

    @Deprecated
    public void setCssCode(String cssCode) {
        blockCSS.setContent(cssCode);
    }

    public void setJava(String code) {
        blockJava.setContent(code);
    }

    public void setCss(String code) {
        blockCSS.setContent(code);
    }

    public void setFXML(String code) {
        blockFXML.setContent(code);
    }

    private Tab createTab(String name) {
        Tab tab  = new Tab(name);
        tab.setClosable(false);
        return tab;
    }

    public void setOnCopying(EventHandler<ActionEvent> onCopying) {
        this.onCopying = onCopying;
    }
}
