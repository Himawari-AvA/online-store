package cn.himawari.admin.inteceptors;


import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginProtectInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return HandlerInterceptor.super.preHandle(request, response, handler);
        Object userInfo = request.getSession().getAttribute("userInfo");
        if (userInfo != null) {
            return true;
        }else{
//            return true;
            response.sendRedirect(request.getContextPath()+"/index.html");
            return false;
        }
//        else if{
//            request.getHeader()
//        }
//        else{
//            response.sendRedirect(request.getContextPath()+"/index.html");
//            return false;
//        }
    }
}
