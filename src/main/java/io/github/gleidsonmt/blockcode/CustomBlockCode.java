package io.github.gleidsonmt.blockcode;

import javafx.scene.control.Button;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/04/2025
 */
public class CustomBlockCode extends BlockCode {

    @Override
    protected Button createCopyButton() {
        return new Button("Copy 2");
    }
}
