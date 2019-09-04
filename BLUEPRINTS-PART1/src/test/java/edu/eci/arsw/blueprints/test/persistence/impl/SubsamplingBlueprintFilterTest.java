package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.SubsamplingBlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

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
