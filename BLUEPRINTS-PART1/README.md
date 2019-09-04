## Compile and run instructions
Entrar al directorio `ARSW-LAB-03/BLUEPRINTS-PART1`:
* **Para compilar:** Ejecutar `mvn package`
* **Para ejecutar MainPrograma** Ejecutar `mvn exec:java -Dexec.mainClass="edu.eci.arsw.blueprints.programa.MainPrograma"`
* **Para ejecutar las pruebas:** Ejecutar `mvn test`

## Part I
1. *Add the dependencies of Spring. Add the Spring settings. Configure the application - by means of annotations - so that the persistence scheme is injected when the* `BlueprintServices` *bean is created. Complete the* `getBluePrint()` *and* `getBlueprintsByAuthor()` *operations. Implement everything required from the lower layers (for now, the available persistence scheme* `InMemoryBlueprintPersistence`*) by adding the corresponding tests in* `InMemoryPersistenceTest`.

    Se agregó el archivo `applicationContext.xml` en los recursos del proyecto.

    Método `getBluePrint()`:
    ```java
    @Component("InMemoryBlueprintPersistence")
    public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

        private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

        @Override
        public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
            return blueprints.get(new Tuple<>(author, bprintname));
        }
    }
    ```
    Método `getBlueprintsByAuthor()`:
    ```java
    @Component("InMemoryBlueprintPersistence")
    public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

        private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

        @Override
        public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
            Set<Blueprint> res= new HashSet<>();
            Set<Tuple<String,String>> keys = blueprints.keySet();
            for (Tuple<String,String> tuple : keys ){
                if(tuple.getElem1().equals(author)){
                    res.add(blueprints.get(tuple));
                }
            }
            return res;
        }
    }
    ```

    Las pruebas correspondientes:
    ```java
    public class InMemoryPersistenceTest {
        @Test
        public void getBlueprintTest (){
            InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "thepaint",pts);
            Blueprint res=null;
            try {
                ibpp.saveBlueprint(bp);
            } catch (BlueprintPersistenceException ex) {
                fail("Blueprint persistence failed inserting the blueprint.");
            }
            try{
                res= ibpp.getBlueprint("john", "thepaint");
            }catch(BlueprintNotFoundException ex){
                fail("Error al obtener el Blueprint");
            }
            assertEquals(bp, res);
        }
        
        @Test
        public void getBlueprintsByAuthorTest (){
            InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "thepaint",pts);
            Point[] pts1=new Point[]{new Point(1, 1),new Point(11, 11)};
            Blueprint bp1=new Blueprint("john", "pepito",pts1);
            Point[] pts2=new Point[]{new Point(2, 2),new Point(12, 12)};
            Blueprint bp2=new Blueprint("john", "pepe",pts2);
            Set<Blueprint> johns = new HashSet<>();
            johns.add(bp);
            johns.add(bp1);
            johns.add(bp2);
            Set<Blueprint> res=null;
            try {
                ibpp.saveBlueprint(bp);
                ibpp.saveBlueprint(bp1);
                ibpp.saveBlueprint(bp2);
            } catch (BlueprintPersistenceException ex) {
                fail("Blueprint persistence failed inserting the blueprint.");
            }
            try{
                res= ibpp.getBlueprintsByAuthor("john");
            }catch(BlueprintNotFoundException ex){
                fail("Error al obtener el Blueprint");
            }
            assertTrue(johns.equals(res));
        }
    }
    ```

2. *Make a program in which you create (through Spring) an instance of* `BlueprintServices`*, and rectify its functionality: register plans, consult plans, register specific plans, etc.*

    `MainProgram` class:
    ```java
    public class MainPrograma {
        public static void main(String a[]) throws BlueprintPersistenceException, BlueprintNotFoundException {
            ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
            BlueprintsServices bps = ac.getBean(BlueprintsServices.class);
            
            Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
            Blueprint bp=new Blueprint("john", "thepaint",pts);
            Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
            Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
            Point[] pts1=new Point[]{new Point(1, 1),new Point(11, 11)};
            Blueprint bp1=new Blueprint("jack", "thestatue",pts1);
            Point[] pts2=new Point[]{new Point(2, 2),new Point(12, 12)};
            Blueprint bp2=new Blueprint("juanes", "thepaint2.0",pts2);
            Point[] pts3=new Point[]{new Point(3, 3),new Point(13, 13)};
            Blueprint bp3=new Blueprint("juanes", "thestatue2.0",pts2);
            
            bps.addNewBlueprint(bp);
            System.out.println("Añadiendo "+bp);
            bps.addNewBlueprint(bp0);
            System.out.println("Añadiendo "+bp0);
            bps.addNewBlueprint(bp1);
            System.out.println("Añadiendo "+bp1);
            bps.addNewBlueprint(bp2);
            System.out.println("Añadiendo "+bp2);
            bps.addNewBlueprint(bp3);
            System.out.println("Añadiendo "+bp3);
            System.out.println();
            System.out.println("Obteniendo la blueprint john/thepaint");
            System.out.println(bps.getBlueprint("john","thepaint"));
            System.out.println("Obteniendo blueprints de juanes");
            System.out.println(bps.getBlueprintsByAuthor("juanes"));
            System.out.println("Obteniendo todas las blueprints");
            System.out.println(bps.getAllBlueprints());
            
        }
    }
    ```

3. *You want the plan query operations to perform a filtering process, before returning the planes consulted. These filters are looking to reduce the size of the plans, removing redundant data or simply sub-sampling, before returning them. Adjust the application (adding the abstractions and implementations you consider) so that the BlueprintServices class is injected with one of two possible 'filters' (or possible future filters). The use of more than one at a time is not contemplated:*

    Se creó la interfaz `BlueprintFilter`:
    ```java
    public interface BlueprintFilter {
        Blueprint filter(Blueprint blueprint);
    }
    ```
    1. *(A) Redundancy filtering: deletes consecutive points from the plane that are repeated.*

        Se creó la clase `RedundancyBlueprintFilter`:
        ```java
        @Component("redundancy")
        public class RedundancyBlueprintFilter implements BlueprintFilter {

            @Override
            public Blueprint filter(Blueprint blueprint) {
                Set<Point> pointSet = new HashSet<>(blueprint.getPoints());
                Point points[] = new Point[pointSet.size()];
                int index = 0;
                for (Point p : pointSet){
                    points[index++] = p;
                }
                return new Blueprint(blueprint.getAuthor(),blueprint.getName(),points);
            }
        }
        ```
    2. *(B) Subsampling filtering: suppresses 1 out of every 2 points in the plane, interspersed.*

        Se creó la clase `SubsamplingBlueprintFilter`:
        ```java
        @Component("subsampling")
        public class SubsamplingBlueprintFilter implements BlueprintFilter {

            @Override
            public Blueprint filter(Blueprint blueprint) {
                List<Point> points = blueprint.getPoints();
                Point newPoints[] = new Point[points.size()/2];
                for (int i = 0; i < points.size() ; i += 2){
                    newPoints[i] = points.get(i);
                }
                return new Blueprint(blueprint.getAuthor(),blueprint.getName(),newPoints);
            }
        }
        ```
4. *Add the corresponding tests to each of these filters, and test its operation in the test program, verifying that only by changing the position of the annotations - without changing anything else - the program returns the filtered planes in the way (A) or in the way (B).*

    Clase `RedundancyBlueprintFilterTest`:
    ```java
    public class RedundancyBlueprintFilterTest {

        @Test
        public void filterRepeated(){
            RedundancyBlueprintFilter redundancyBlueprintFilter = new RedundancyBlueprintFilter();

            Point points[] = {new Point(1,1),
                    new Point(1,1),
                    new Point(1,1),
                    new Point(3,5),
                    new Point(4,7)};

            List<Point> pointsExpected = new ArrayList<>();
            pointsExpected.add(new Point(1,1));
            pointsExpected.add(new Point(3,5));
            pointsExpected.add(new Point(4,7));

            int before = points.length;
            Blueprint blueprint = new Blueprint("John","ppaint",points);
            blueprint = redundancyBlueprintFilter.filter(blueprint);
            assertTrue(before >= blueprint.getPoints().size());

        }
    }
    ```

    Clase `SubsamplingBlueprintFilterTest`:
    ```java

    public class SubsamplingBlueprintFilterTest {

        @Test
        public void subsamplingFilteringOdd(){
            SubsamplingBlueprintFilter subsamplingBlueprintFilter = new SubsamplingBlueprintFilter();
            Point points[] = {new Point(1,1),
                    new Point(15,1),
                    new Point(2,1),
                    new Point(3,5),
                    new Point(4,7)};

            List<Point> pointsExpected = new ArrayList<>();
            pointsExpected.add(new Point(1,1));
            pointsExpected.add(new Point(2,1));
            pointsExpected.add(new Point(4,7));

            Blueprint blueprint = new Blueprint("David","elcarro",points);
            blueprint = subsamplingBlueprintFilter.filter(blueprint);
            assertTrue(blueprint.getPoints().size() == pointsExpected.size());
            List<Point> ansPoint = blueprint.getPoints();
            for (int i = 0; i < ansPoint.size(); i++){
                assertEquals(ansPoint.get(i),pointsExpected.get(i));
            }
        }

        @Test
        public void subsamplingFilteringEven(){
            SubsamplingBlueprintFilter subsamplingBlueprintFilter = new SubsamplingBlueprintFilter();
            Point points[] = {new Point(1,1),
                    new Point(15,1),
                    new Point(2,1),
                    new Point(3,5),
                    };

            List<Point> pointsExpected = new ArrayList<>();
            pointsExpected.add(new Point(1,1));
            pointsExpected.add(new Point(2,1));

            Blueprint blueprint = new Blueprint("David","elcarro",points);
            blueprint = subsamplingBlueprintFilter.filter(blueprint);
            assertTrue(blueprint.getPoints().size() == pointsExpected.size());
            List<Point> ansPoint = blueprint.getPoints();
            for (int i = 0; i < ansPoint.size(); i++){
                assertEquals(ansPoint.get(i),pointsExpected.get(i));
            }
        }
    }

    ```

    Clase `MainPrograma`:
    ```java
    System.out.println("Probando filtros");
        System.out.println("BPS4 " + bp4 + " " + bps.getBlueprint("ronaldo","escultura").getPoints());
    ``` 