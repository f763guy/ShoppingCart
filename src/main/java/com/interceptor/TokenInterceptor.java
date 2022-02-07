package com.interceptor;

import com.common.TokenForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(TokenInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //第一步：獲得調用處理方法
        HandlerMethod hm=(HandlerMethod) handler;
        TokenForm tokenForm = hm.getMethodAnnotation(TokenForm.class);


        //第二步：判斷是否有Token註解
        if (tokenForm!=null) {
            HttpSession session = request.getSession();
            if (tokenForm.create()==true) {
                session.setAttribute("token", UUID.randomUUID().toString());
                LOGGER.debug("打印出來的token:"+session.getAttribute("token"));
            }
            if (tokenForm.remove()==true) {
                //判斷表單的Token與伺服器的Token是否相同
                String formToken = request.getParameter("token");
                Object sessionToken = session.getAttribute("token");
                //request傳遞過來的Token與session的Token相同，允許操作，並且刪除session的Token
                if (formToken.equals(sessionToken)){
                    session.removeAttribute("token");
                }else{
                    //Redirect到指定的路徑
                    String invoke = request.getParameter("token.invoke");
                    response.sendRedirect(request.getContextPath()+invoke);
                    return false;
                }
            }

        }
        return true;
    }
}
