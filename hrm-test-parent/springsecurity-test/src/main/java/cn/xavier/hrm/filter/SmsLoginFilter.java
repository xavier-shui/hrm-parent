package cn.xavier.hrm.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng-Wei Shui
 * @date 12/28/2021
 */
public class SmsLoginFilter extends AbstractAuthenticationProcessingFilter {
    // ~ Static fields/initializers
    // =====================================================================================

    public static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";
    public static final String SPRING_SECURITY_FORM_CODE_KEY = "code";
    private boolean postOnly = true;

    // ~ Constructors
    // ===================================================================================================

    public SmsLoginFilter() {
        super(new AntPathRequestMatcher("/smsLogin", "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String phone = request.getParameter("phone");
        String code = request.getParameter("code");

        if (phone == null) {
            phone = "";
        }

        if (code == null) {
            code = "";
        }

        phone = phone.trim();

        PhoneCodeAuthenticationToken authRequest = new PhoneCodeAuthenticationToken(phone, code);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }


    protected void setDetails(HttpServletRequest request,
                              PhoneCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

