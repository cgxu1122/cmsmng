package com.ifhz.hzfmng.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MonitorInterceptor implements HandlerInterceptor {

    Logger LOGGER = LoggerFactory.getLogger(MonitorInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("(url,{})|(host,{})", new Object[]{"hzfmng" + "_" + request.getRequestURI() + "_preHandle", request.getRemoteHost()});
        Runtime rt = Runtime.getRuntime();
        LOGGER.info("(totalmemory,{})|(freememory,{})", new Object[]{rt.totalMemory(), rt.freeMemory()});
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        LOGGER.info("(url,{})|(host,{})", new Object[]{"hzfmng" + "_" + request.getRequestURI() + "_postHandle", request.getRemoteHost()});
        Runtime rt = Runtime.getRuntime();
        LOGGER.info("(totalmemory,{})|(freememory,{})", new Object[]{rt.totalMemory(), rt.freeMemory()});
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LOGGER.info("(url,{})|(host,{})", new Object[]{"hzfmng" + "_" + request.getRequestURI() + "_afterCompletion", request.getRemoteHost()});
        Runtime rt = Runtime.getRuntime();
        LOGGER.info("(totalmemory,{})|(freememory,{})", new Object[]{rt.totalMemory(), rt.freeMemory()});

    }
}
