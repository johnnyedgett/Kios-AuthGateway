package com.kios.app.agw.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.slf4j.Logger;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.kios.app.agw.security.UserPrincipal;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class RequestFilter extends ZuulFilter {
	private static final Logger LOG = LogManager.getLogger(RequestFilter.class);
	@Override
	public Object run() {
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			// do nothing, user is not authenticated
		} else {
			String customerId = String.valueOf(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
			RequestContext ctx = RequestContext.getCurrentContext();
				ctx.addZuulRequestHeader("CUSTOMERID", customerId);
		}
		return null;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // run before PreDecoration
	}

	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return !ctx.containsKey(FilterConstants.FORWARD_TO_KEY) // a filter has already forwarded
				&& !ctx.containsKey(FilterConstants.SERVICE_ID_KEY); // a filter has already determined serviceId
	}
}
