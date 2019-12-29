package kr.co.heracles.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import kr.co.heracles.zuul.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class PostFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostFilter.class);

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_POST_SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        // TODO Auto-generated method stub
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        Map<String , String> headerMap = requestContext.getZuulRequestHeaders();

        String body = null;
        try (InputStream inputStream = requestContext.getResponseDataStream();){
            body = StreamUtils.copyToString(inputStream , Charset.forName("UTF-8"));
            requestContext.setResponseBody(body);
        } catch (IOException e) {
            log.error("PostFilter InputStream Error", e);
        }

        log.info("=================== PostFilter Infomation ===================");
        log.info("GID_KEY:[" + headerMap.get(Constant.GID_KEY) + "]" );
        log.info("Method:[" + httpServletRequest.getMethod() + "]");
        log.info("URL:[" + httpServletRequest.getRequestURL().toString() + "]");
        log.debug("Headers:[" + headerMap.toString() + "]");
        log.debug("Body:[" + body + "]");
        log.info("=============================================================");

        return null;
    }

    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_POST;
    }

    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_POST_ORDER;
    }
}
