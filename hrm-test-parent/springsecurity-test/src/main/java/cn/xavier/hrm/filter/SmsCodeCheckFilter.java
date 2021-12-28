package cn.xavier.hrm.filter;

import cn.xavier.hrm.authenticationhandler.MyAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Zheng-Wei Shui
 * @date 12/28/2021
 */

public class SmsCodeCheckFilter extends OncePerRequestFilter {
    // 不受spring管理的类不能自动注入
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        // 如果是/smsLogin则比对验证码
        if ("/smsLogin".equals(requestURI)) {
            String phone = request.getParameter("phone");
            String code = request.getParameter("code");
            String sms_code_in_session = (String) request.getSession().getAttribute("SMS_CODE_IN_SESSION");
            if (!StringUtils.isEmpty(code) && code.equalsIgnoreCase(sms_code_in_session)) {
                filterChain.doFilter(request, response);
            } else {
                myAuthenticationFailureHandler.onAuthenticationFailure(request, response, new BadCredentialsException("验证码错误!"));
            }
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void setMyAuthenticationFailureHandler(MyAuthenticationFailureHandler myAuthenticationFailureHandler) {
        this.myAuthenticationFailureHandler = myAuthenticationFailureHandler;
    }

}