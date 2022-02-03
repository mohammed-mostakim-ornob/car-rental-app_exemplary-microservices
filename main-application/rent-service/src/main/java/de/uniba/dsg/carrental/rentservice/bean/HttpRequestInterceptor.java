package de.uniba.dsg.carrental.rentservice.bean;

import de.uniba.dsg.carrental.rentservice.Constants;
import de.uniba.dsg.carrental.rentservice.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpRequestInterceptor  implements HandlerInterceptor {

    @Value("${springdoc.api-docs.path}")
    String apiDocPath;

    private final RequestLogService requestLogService;

    @Autowired
    public HttpRequestInterceptor(RequestLogService requestLogService) {
        this.requestLogService = requestLogService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isApiDocRequest(request.getRequestURL().toString()))
            request.setAttribute(Constants.ATTRIBUTE_REQUEST_START_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (!isApiDocRequest(request.getRequestURL().toString())) {
            Long startTime = (Long)request.getAttribute(Constants.ATTRIBUTE_REQUEST_START_TIME);
            Long responseTime = System.currentTimeMillis() - startTime;

            requestLogService.storeRequest(
                    request.getHeader(Constants.HEADER_CLIENT_SERVICE_INSTANCE_ID),
                    response.getHeader(Constants.HEADER_METHOD_NAME),
                    response.getHeader(Constants.HEADER_RESPONSE_CODE),
                    startTime,
                    responseTime
            );
        }
    }

    private boolean isApiDocRequest(String requestUrl) {
        return requestUrl.endsWith(apiDocPath);
    }
}
