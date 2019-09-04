## Compile and run instructions
Entrar al directorio `ARSW-LAB-03/GRAMAR-CHECKER`:
* **Para compilar:** Ejecutar `mvn package`
* **Para ejecutar Grammar-Checker:** Ejecutar `mvn exec:java -Dexec.mainClass="edu.eci.arsw.springdemo.ui.Main"`


## Part I - Basic workshop 
1. *Open the project sources in NetBeans.*
2. *Review the Spring configuration file already included in the project (src / main / resources). It indicates that Spring will automatically search for the 'Beans' available in the indicated package.*
3. *Making use of the Spring configuration based on annotations mark with the annotations @Autowired and @Service the dependencies that must be injected, and the 'beans' candidates to be injected -respectively-:*
    1. *GrammarChecker will be a bean, which depends on something like 'SpellChecker'.*
    
        Atributo `@AutoWired` y `@Service`:
        ```java
        @Service
        public class GrammarChecker{
            @Autowired
            SpellChecker sc;
            ...
        }
        ```
    2. *EnglishSpellChecker and SpanishSpellChecker are the two possible candidates to be injected. One must be selected, or another, but NOT both (there would be dependency resolution conflict). For now, have EnglishSpellChecker used.*

        Clase `SpanishSpellChecker`:
        ```java
        @Component("Spanish")
        public class SpanishSpellChecker implements SpellChecker {

            @Override
            public String checkSpell(String text) {
                return "revisando ("+text+") con el verificador de sintaxis del espanol";           
            }
        }
        ```

        Clase `EnglishSpellChecker`:
        ```java
        @Component("English")
        public class EnglishSpellChecker implements SpellChecker {

            @Override
            public String checkSpell(String text) {		
                return "Checked with english checker:"+text;
            }       
        }
        ```

4. *Make a test program, where an instance of GrammarChecker is created by Spring, and use it:*
    ```java
    public static void main(String[] args) {
        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        GrammarChecker gc=ac.getBean(GrammarChecker.class);
        System.out.println(gc.check("la la la "));
    }
    ```