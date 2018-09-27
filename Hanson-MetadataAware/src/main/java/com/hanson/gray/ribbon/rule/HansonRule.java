package com.hanson.gray.ribbon.rule;

import com.hanson.gray.ribbon.predicate.HansonPredicate;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.PredicateBasedRule;

/**
 * @author Hanson
 */
public class HansonRule extends PredicateBasedRule {
	
	private CompositePredicate compositePredicate;
	
    public HansonRule() {
        HansonPredicate hansonPredicate = new HansonPredicate(this);
        compositePredicate = createCompositePredicate(hansonPredicate,  new AvailabilityPredicate(this, null));
    }

    private CompositePredicate createCompositePredicate(HansonPredicate p1, AvailabilityPredicate p2) {
        return CompositePredicate.withPredicates(p1, p2)
                             .addFallbackPredicate(p2)
                             .addFallbackPredicate(AbstractServerPredicate.alwaysTrue())
                             .build();
        
    }

	@Override
	public AbstractServerPredicate getPredicate() {
		 return compositePredicate;
	}
}
