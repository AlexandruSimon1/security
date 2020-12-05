package com.example.security.services;

import com.example.security.entities.Resolution;
import com.example.security.entities.User;
import com.example.security.entities.UserPersonalInfo;
import com.example.security.repositories.ResolutionRepository;
import com.example.security.repositories.UserPersonalInfoRepository;
import com.example.security.repositories.UserRepository;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ResolutionInitializer implements SmartInitializingSingleton {
	private final UserRepository users;
	private final ResolutionRepository resolutions;
	private final PasswordEncoder passwordEncoder;
	private final UserPersonalInfoRepository userPersonalInfoRepository;

	public ResolutionInitializer(UserRepository users, ResolutionRepository resolutions, PasswordEncoder passwordEncoder, UserPersonalInfoRepository userPersonalInfoRepository) {
		this.users = users;
		this.resolutions = resolutions;
		this.passwordEncoder = passwordEncoder;
		this.userPersonalInfoRepository = userPersonalInfoRepository;
	}


		@Override
	@Transactional
	public void afterSingletonsInstantiated() {

		Long joshId = 2L;
		Long carolId = 1L;

		this.resolutions.save(new Resolution("Read War and Peace", joshId));
		this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", joshId));
		this.resolutions.save(new Resolution("Hang Christmas Lights", joshId));

		this.resolutions.save(new Resolution("Run for President", carolId));
		this.resolutions.save(new Resolution("Run a Marathon", carolId));
		this.resolutions.save(new Resolution("Run an Errand", carolId));

		String joshPassword = this.passwordEncoder.encode("josh");
		String carolPassword = this.passwordEncoder.encode("carol");


		User josh = new User(joshId, "josh", joshPassword);
		josh.addAuthority("READ");

		User carol =new User(carolId, "carol", carolPassword);
		carol.addAuthority("READ");
		carol.addAuthority("WRITE");
		carol.addAuthority("ADMIN");
		UserPersonalInfo userPersonalInfo = new UserPersonalInfo();
		userPersonalInfo.setEmail("carol@gmail.com");
		userPersonalInfo.setFirstName("carol");
		userPersonalInfo.setLastName("carol");
		userPersonalInfo.setPhoneNumber("07888888888");
		carol.setUserPersonalInfo(userPersonalInfo);
		carol.setEnabled(true);


		this.save(josh);
		this.save(carol);
	}

	private void save(User user){
		users.save(user);
	}
}
