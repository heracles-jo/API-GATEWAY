package kr.co.heracles.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import kr.co.heracles.zuul.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

public class PreFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(PreFilter.class);

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_PRE_SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        // TODO Auto-generated method stub
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        requestContext.addZuulRequestHeader(Constant.GID_KEY, UUID.randomUUID().toString());

        Map<String , String> headerMap = requestContext.getZuulRequestHeaders();

        String body = null;
        try (InputStream inputStream = (InputStream) requestContext.get("requestEntity");){
            body = StreamUtils.copyToString(inputStream==null ? requestContext.getRequest().getInputStream() : inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("IOException e", e);
        }

        log.info("=================== PreFilter Infomation ===================");
        log.info("GID_KEY:[" + headerMap.get(Constant.GID_KEY) + "]" );
        log.info("Method:[" + httpServletRequest.getMethod() + "]");
        log.info("URL:[" + httpServletRequest.getRequestURL().toString() + "]");
        log.debug("Headers:[" + headerMap.toString() + "]");
        log.debug("Body:[" + body + "]");
        log.info("============================================================");

        return null;
    }

    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_PRE;
    }

    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_PRE_ORDER;
    }
}
