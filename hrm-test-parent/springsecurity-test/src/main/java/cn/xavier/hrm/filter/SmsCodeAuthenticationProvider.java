package cn.xavier.hrm.filter;

import cn.xavier.hrm.userdetail.LoginUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * An {@link AuthenticationProvider} implementation that retrieves user details from a
 * {@link UserDetailsService}.
 *
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

	private LoginUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
		return new PhoneCodeAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (PhoneCodeAuthenticationToken.class
				.isAssignableFrom(authentication));
	}

	public void setUserDetailsService(LoginUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
