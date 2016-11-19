package com.baziuk.spring.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Maks on 11/12/16.
 */
public class PdfViewResolver implements ViewResolver, ApplicationContextAware {

    public PdfViewResolver() {
        log.info("Creating an instance: PdfViewResolver");
    }

    private static final Logger log = Logger.getLogger(PdfViewResolver.class);

    private ApplicationContext applicationContext;

    private Map<String, String> viewMapping = new HashMap<>();

    {
        // We have java config, initializing it where key is name, value - name of a Spring bean
        viewMapping.put("singleTicket", "singleTicketPdfView");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        View result = null;
        if (StringUtils.isNotBlank(viewName) && viewMapping.containsKey(viewName))
            result = (View) applicationContext.getBean(viewMapping.get(viewName));
        return result;
    }
}
