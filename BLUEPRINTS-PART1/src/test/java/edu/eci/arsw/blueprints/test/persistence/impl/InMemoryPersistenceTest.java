/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
    }

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
