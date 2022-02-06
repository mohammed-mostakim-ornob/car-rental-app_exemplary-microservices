package de.uniba.dsg.carrental.carservice.architectureextraction.bean;

import de.uniba.dsg.carrental.carservice.Constants;
import de.uniba.dsg.carrental.carservice.architectureextraction.model.RequestLogDto;
import de.uniba.dsg.carrental.carservice.architectureextraction.properties.InstanceProperties;
import de.uniba.dsg.carrental.carservice.architectureextraction.service.RequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class HttpRequestInterceptor  implements HandlerInterceptor {

    @Value("${springdoc.api-docs.path}")
    String apiDocPath;

    private final RequestLogService requestLogService;
    private final InstanceProperties instanceProperties;

    @Autowired
    public HttpRequestInterceptor(RequestLogService requestLogService, InstanceProperties instanceProperties) {
        this.requestLogService = requestLogService;
        this.instanceProperties = instanceProperties;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!isApiDocRequest(request.getRequestURL().toString()))
            request.setAttribute(Constants.ATTRIBUTE_REQUEST_START_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws IOException {
        if (!isApiDocRequest(request.getRequestURL().toString())) {
            Long startTime = (Long)request.getAttribute(Constants.ATTRIBUTE_REQUEST_START_TIME);
            Long responseTime = System.currentTimeMillis() - startTime;

            requestLogService.storeRequest(new RequestLogDto(
                    request.getHeader(Constants.HEADER_CLIENT_SERVICE_INSTANCE_ID) == null
                            ? "EXTERNAL-CLIENT"
                            : request.getHeader(Constants.HEADER_CLIENT_SERVICE_INSTANCE_ID),
                    instanceProperties.getContainerId(),
                    response.getHeader(Constants.HEADER_METHOD_NAME),
                    response.getHeader(Constants.HEADER_RESPONSE_CODE),
                    startTime,
                    responseTime
            ));
        }
    }

    private boolean isApiDocRequest(String requestUrl) {
        return requestUrl.endsWith(apiDocPath);
    }
}
