package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.BlueprintFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import org.springframework.stereotype.Component;

@Component("redundancy")
public class RedundancyBlueprintFilter implements BlueprintFilter {

    @Override
    public Blueprint filter(Blueprint blueprint) {
        return  null;
    }

}
