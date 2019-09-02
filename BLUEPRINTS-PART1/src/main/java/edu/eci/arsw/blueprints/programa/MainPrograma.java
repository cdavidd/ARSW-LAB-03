/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.programa;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author cdavi_000
 */
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
