# Block code for JavaFx examples

This code use an adaption from https://highlightjs.org/
<br>
Block code is a simple web view (javafx) that implements a highlight in its content.

🧬
```
|-- BlockCode (StackPane) 
  |-- Code   (WebView)
  
```
### 👾 Example

```java
    // Imperative style
    BlockCode block = new BlockCode();
    block.setTheme(Theme.GITHUB);
    block.setCodeType(CodeType.JAVASCRIPT);
    block.setContent("""
                        function hello() { 
                            console.log('Hello') 
                        };""");
    block.build();

    // Declarative style
    BlockCode code = new BlockCode()
            .theme(Theme.GITHUB)
            .codeType(CodeType.JAVASCRIPT)
            .content("function hello() { console.log('Hello') };")
            .build();
```
<p style="text-align: center">
    <img src="./example_block_code.png"  alt="example_block_code"/>
</p>

```
|-- BlockCodeView (TabPane)
    |-- Tab (Tab) 'java'
        |-- BlockCode (StackPane)
            |-- Code   (WebView)
    |-- Tab (Tab) 'FXML'
        |-- BlockCode (StackPane)
            |-- Code   (WebView)
    |-- Tab (Tab) 'Css'
        |-- BlockCode (StackPane)
            |-- Code   (WebView)
    
```
### 👾 Example
```java
    // Or using tabs
    BlockCodeView block = new BlockCodeView();
    block.setJavaCode("""
            Button button = new Button("Cancel");
            """);
    
    block.setFXMLCode("""
            <Button text="button">
                <graphic>
                   <IconContainer icon="DISCOUNT"/>
                </graphic>
            </Button>
            """);
    
    block.setCssCode("""
            .button {
                -fx-accent: red;
            }
            """);
```
### 🎆 The Result
<p style="text-align: center">
  <img src="./example_block_code_view.png" />
</p>

### Setting copy event
```java
block.setOnCopying(e -> {
        ...
});
```

### Styling copy button
```css
.copy-button {
    ...
}

```

