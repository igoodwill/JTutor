package org.igoodwill.jtutorsb.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.igoodwill.jtutorsb.JTutorSbApplication;
import org.igoodwill.jtutorsb.model.admin.Users;
import org.igoodwill.jtutorsb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {
	@Value("${app.user.verification}")
	private Boolean requireActivation;

	@Value("${app.secret}")
	private String applicationSecret;

	@Autowired
	private UsersRepository repo;

	@Autowired
	private HttpSession httpSession;

	public final String CURRENT_USER_KEY = "CURRENT_USER";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findOneByUsernameOrEmail(username, username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		if (requireActivation && !user.getToken().equals("1")) {
			JTutorSbApplication.log.error("User [" + username + "] tried to login but is not activated");
			throw new UsernameNotFoundException(username + " has not been activated yet");
		}
		httpSession.setAttribute(CURRENT_USER_KEY, user);
		List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), auth);
	}

	public void autoLogin(Users user) {
		autoLogin(user.getUsername());
	}

	public void autoLogin(String username) {
		UserDetails userDetails = this.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);
		if (auth.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
	}

	public Users register(Users user) {
		user.setPassword(encodeUserPassword(user.getPassword()));

		if (this.repo.findOneByUsername(user.getUsername()) == null && this.repo.findOneByEmail(user.getEmail()) == null) {
			String activation = createActivationToken(user, false);
			user.setToken(activation);
			this.repo.save(user);
			return user;
		}

		return null;
	}

	public String encodeUserPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);
	}

	public Boolean delete(Integer id) {
		this.repo.delete(id);
		return true;
	}

	public Users activate(String activation) {
		if (activation.equals("1") || activation.length() < 5) {
			return null;
		}
		Users u = this.repo.findOneByToken(activation);
		if (u != null) {
			u.setToken("1");
			this.repo.save(u);
			return u;
		}
		return null;
	}

	public String createActivationToken(Users user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String activationToken = encoder.encodePassword(user.getUsername(), applicationSecret);
		if (save) {
			user.setToken(activationToken);
			this.repo.save(user);
		}
		return activationToken;
	}

	public String createResetPasswordToken(Users user, Boolean save) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String resetToken = encoder.encodePassword(user.getEmail(), applicationSecret);
		if (save) {
			user.setToken(resetToken);
			this.repo.save(user);
		}
		return resetToken;
	}

	public Users resetActivation(String email) {
		Users u = this.repo.findOneByEmail(email);
		if (u != null) {
			createActivationToken(u, true);
			return u;
		}
		return null;
	}

	public Boolean resetPassword(Users users) {
		Users u = this.repo.findOneByUsername(users.getUsername());
		if (u != null) {
			u.setPassword(encodeUserPassword(users.getPassword()));
			u.setToken("1");
			this.repo.save(u);
			return true;
		}
		return false;
	}

	public void updateUser(Users user) {
		updateUser(user.getUsername(), user);
	}

	public void updateUser(String username, Users newData) {
		this.repo.updateUser(username, newData.getEmail(), newData.getFirstName(), newData.getLastName());
	}

	public Users getLoggedInUser() {
		return getLoggedInUser(false);
	}

	public Users getLoggedInUser(Boolean forceFresh) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Users users = (Users) httpSession.getAttribute(CURRENT_USER_KEY);
		if (forceFresh || httpSession.getAttribute(CURRENT_USER_KEY) == null) {
			users = this.repo.findOneByUsername(username);
			httpSession.setAttribute(CURRENT_USER_KEY, users);
		}
		return users;
	}

	public void updateLastLogin(String username) {
		this.repo.updateLastLogin(username);
	}
}