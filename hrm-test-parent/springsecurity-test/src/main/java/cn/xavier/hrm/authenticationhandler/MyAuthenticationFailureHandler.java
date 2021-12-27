package cn.xavier.hrm.authenticationhandler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zheng-Wei Shui
 * @date 12/26/2021
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        Map map = new HashMap<>();
        map.put("success",true);
        map.put("message","authentication failure " + exception.getMessage());
        response.getWriter().print(map.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
