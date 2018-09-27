
package com.hanson.gray.ribbon.predicate;



import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.netflix.zuul.context.RequestContext;

/**
 * @author Hanson
 */
public class HansonPredicate extends  AbstractServerPredicate{
	
	@Value("${containsAll:false}")
	private boolean containsAll;

	@Override
	public boolean apply(PredicateKey input) {
		Object loadBalancerKey = RequestContext.getCurrentContext().get(FilterConstants.LOAD_BALANCER_KEY);
		if(input != null && input.getServer() instanceof DiscoveryEnabledServer && loadBalancerKey !=null && loadBalancerKey instanceof Map) {
			DiscoveryEnabledServer discoveryEnabledServer = (DiscoveryEnabledServer)input.getServer();
			Map<String, String> metadata = discoveryEnabledServer.getInstanceInfo().getMetadata();
			//attributes 为 request header 中的路由标识
			@SuppressWarnings("unchecked")
			Map<String,String> key = (Map<String,String>)RequestContext.getCurrentContext().get(FilterConstants.LOAD_BALANCER_KEY);
			if(containsAll) {
//				and 可以使用containsAll
				final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(key.entrySet());
				return metadata.entrySet().containsAll(attributes);
			}else {
				//此处选择使用or,取交集，长度大于0则为命中
				Set<Map.Entry<String, String>> attributes = new HashSet<Map.Entry<String, String>>();
				attributes.addAll(key.entrySet());
				attributes.retainAll(metadata.entrySet());
				return attributes.size()>0;
			}
		}
		return false;
	}

	public HansonPredicate(IRule rule) {
        super(rule);
    }
}
