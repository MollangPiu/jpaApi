package kr.soft.campus.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class UserInterceptor  implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws  Exception {

        logger.info("============= user_api =============");
        logger.info("Request URI ==>  :"+request.getRequestURI());
        logger.info("============================================");

        //response.sendRedirect("/");

        return true;
        //return super.preHandle(request, response, handle);
    }
}