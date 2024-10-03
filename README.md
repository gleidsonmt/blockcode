# Block code for JavaFx examples

 This code use an adaption from https://highlightjs.org/
<br>
Block code is a simple web view (javafx) that implements a highlight in its content.

🧬
```
|-- BlockCode (StackPane) 
  |-- web   (WebView)
  
```
### Example
    

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

### More


### Result
  <img src="./screenshot.png"  />


