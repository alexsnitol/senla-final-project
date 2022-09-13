package ru.senla.realestatemarket;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.senla.realestatemarket.config.AspectJConfig;
import ru.senla.realestatemarket.config.CoreConfig;
import ru.senla.realestatemarket.config.SpringFoxConfig;
import ru.senla.realestatemarket.config.WebConfig;
import ru.senla.realestatemarket.config.security.WebSecurityConfig;

import javax.servlet.ServletRegistration;

public class MainWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {WebSecurityConfig.class, CoreConfig.class, AspectJConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class, SpringFoxConfig.class};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}
