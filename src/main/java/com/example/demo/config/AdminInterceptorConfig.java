package com.example.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Protege todas las rutas /admin/** : si no hay sesión de administrador, redirige al login. */
@Configuration
public class AdminInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
                if (req.getSession().getAttribute("admin") == null) {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return false;
                }
                return true;
            }
        }).addPathPatterns("/admin/**");
    }
}
