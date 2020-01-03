package kr.co.heracles.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import kr.co.heracles.zuul.constant.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class ErrorFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public boolean shouldFilter() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_ERROR_SHOULD_FILTER;
    }

    @Override
    public Object run() throws ZuulException {
        // TODO Auto-generated method stub
        HttpServletResponse httpServletResponse = RequestContext.getCurrentContext().getResponse();

        RequestContext requestContext = RequestContext.getCurrentContext();
        Map<String, String> headerMap = requestContext.getZuulRequestHeaders();

        String requestBody = null;
        try (InputStream inputStream = (InputStream) requestContext.get("requestEntity");){
            requestBody = StreamUtils.copyToString(inputStream==null ? requestContext.getRequest().getInputStream() : inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("IOException e", e);
        }

        String responseBody = null;
        try (InputStream inputStream = requestContext.getResponseDataStream();){
            responseBody = StreamUtils.copyToString(inputStream , Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("IOException e", e);
        }

        log.info("=================== ErrorFilter Infomation ===================");
        log.info("GID_KEY:[" + headerMap.get(Constant.GID_KEY) + "]" );
        log.info("Response Status :[" + httpServletResponse.getStatus() + "]");
        log.info("Response Headers:[" + headerMap.toString() + "]");
        log.info("Response Body:[" + responseBody + "]");
        log.info("Request Body:[" + requestBody + "]");
        log.info("=============================================================");

        return null;
    }

    @Override
    public String filterType() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_ERROR;
    }

    @Override
    public int filterOrder() {
        // TODO Auto-generated method stub
        return Constant.FILTER_TYPE_ERROR_ORDER;
    }
}
