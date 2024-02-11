package br.com.m4rc310.budget.services;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.m4rc310.budget.data.dto.AuthUser;
import br.com.m4rc310.budget.repositories.AuthUserRepository;
import br.com.m4rc310.gql.dto.MUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableCaching
public class MServiceCache {
	public static final String CACHE_FIRST_USER_MODE = "cache.first.user.mode";
	public static final String CACHE_USER = "cache.user";
	public static final String CACHE_MUSER = "cache.muser";
	public static final String CACHE_AUTH_USER = "cache.auth.user";
	public static final String CACHE_HASH_USERNAME = "cache.hash.username";
	public static final String CACHE_AUTHENTICATE = "cache.authenticate";

	@Autowired
	protected AuthUserRepository authUserRepository;

	@Autowired
	protected PasswordEncoder encoder;

	@Cacheable(value = CACHE_AUTHENTICATE, key = "T(java.util.Objects).hash(#username, #password)")
	public MUser login(String username, Object password) throws Exception {
		String psw = String.valueOf(password);
		
		MUser user = fromUsername(username);
		if (encoder.matches(psw, user.getPassword())) {
			return user;
		}
		return null;
	}
	
	@Cacheable(value = CACHE_MUSER, key = "#username")
	public MUser fromUsername(String username) throws Exception {
		AuthUser user = authUserRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));
		return AuthUser.toMUser(user);
	}
	
	public MUser fromCode(Long code) throws Exception {
		AuthUser user = authUserRepository.findById(code).orElse(null);
		if (Objects.isNull(user)) {
			return null;
		}
		return AuthUser.toMUser(user);
	}
	
	public AuthUser store(AuthUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return authUserRepository.saveAndFlush(user);
	}
	
}
