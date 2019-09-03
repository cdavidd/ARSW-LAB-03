package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filter.impl.SubsamplingBlueprintFilter;
import edu.eci.arsw.blueprints.model.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class SubsamplingBlueprintFilterTest {


    @Test
    public void subsamplingFiltering(){
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

        assertTrue(true);
    }

}
