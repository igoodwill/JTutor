package org.igoodwill.jtutorsb.adapters;

import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;

@Configuration
public class LoginAdapter implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
	@Autowired
	private UsersService usersService;

	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		usersService.updateLastLogin(event.getAuthentication().getName());
	}

}
