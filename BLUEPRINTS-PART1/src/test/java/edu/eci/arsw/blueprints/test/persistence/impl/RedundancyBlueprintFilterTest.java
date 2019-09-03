package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.RedundancyBlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

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
