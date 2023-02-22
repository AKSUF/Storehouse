package com.storehouse.com.security.oath;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.storehouse.com.config.AppConstants;
import com.storehouse.com.entity.Account;
import com.storehouse.com.entity.Role;
import com.storehouse.com.entity.User;
import com.storehouse.com.entity.UserRole;
import com.storehouse.com.exceptions.OAuth2AuthenticationProcessingException;
import com.storehouse.com.repository.AccountRepository;
import com.storehouse.com.repository.RoleRepository;
import com.storehouse.com.repository.UserRepository;
import com.storehouse.com.repository.UserRoleRepository;
import com.storehouse.com.status.Provider;

@Service
public class CustomOAuthUserService extends DefaultOAuth2UserService{


	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest)
			throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		try {
			return processOAuthUser(userRequest, oAuth2User);
		} catch (OAuth2AuthenticationProcessingException e) {
			throw new OAuth2AuthenticationException(e.getMsg());
		} catch (AuthenticationException e) {
			throw e;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuthUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		OAuthUserInfo oAuthUserInfo = OAuth2UserInfoFactory
				.getOAuth2UserInfo(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		if (StringUtils.isEmpty(oAuthUserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}
		Optional<Account> userOptional = accountRepository.findByEmail(oAuthUserInfo.getEmail());
		Account user;
		if (userOptional.isPresent()) {
			user = userOptional.get();
			if (!user.getProvider()
					.equalsIgnoreCase(Provider.valueOf(userRequest.getClientRegistration().getRegistrationId()).toString())) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + userRequest.getClientRegistration().getRegistrationId()
								+ " account. Please use your " + user.getProvider() + " account to login");
			}
			user = updateExistingUser(user, oAuthUserInfo);
		} else {
			user = registerNewUser(userRequest, oAuthUserInfo);
		}
		return UserPrincipal.create(user, oAuth2User.getAttributes());
	}

	private Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuthUserInfo oAuth2UserInfo) {
		Account account = new Account();
		account.setProvider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()).name());
		account.setEmail(oAuth2UserInfo.getEmail());
		account = accountRepository.save(account);
		User user = new User();
		user.setAccount(account);
		user.setProfile_image(oAuth2UserInfo.getImageUrl());
		user.setName(oAuth2UserInfo.getName());
		userRepository.save(user);
		UserRole userRole = new UserRole();
		userRole.setAccount(account);
		Role memberRole = roleRepository.findById(AppConstants.ROLE_CUSTOMER.longValue()).get();
		userRole.setRole(memberRole);
		userRoleRepository.save(userRole);
		System.out.println("Load ddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
		return accountRepository.save(account);
	}

	private Account updateExistingUser(Account existingUser, OAuthUserInfo oAuth2UserInfo) {
		User user = existingUser.getUser();
		user.setName(oAuth2UserInfo.getName());
		user.setProfile_image(oAuth2UserInfo.getImageUrl());
		return accountRepository.save(existingUser);
	}
	
}
