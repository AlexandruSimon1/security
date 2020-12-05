package com.example.security.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity()
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column (unique = true, nullable = false)
    String username;

	@Column (nullable = false)
    String password;

	@Column
    Boolean enabled = false;

	@OneToMany(mappedBy = "user", cascade=ALL, fetch=EAGER)
	Set<UserAuthority> userAuthorities = new HashSet<>();

	@OneToOne(cascade = ALL)
	@JoinColumn(name = "user_personal_info_id")
	private UserPersonalInfo userPersonalInfo;

	public UserPersonalInfo getUserPersonalInfo() {
		return userPersonalInfo;
	}

	public void setUserPersonalInfo(UserPersonalInfo userPersonalInfo) {
		this.userPersonalInfo = userPersonalInfo;
	}

	public User() {}

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.password = user.password;
		this.enabled = user.enabled;
		this.userAuthorities = new HashSet<>(user.getUserAuthorities());
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
	}

	public void addAuthority(String authority) {
		this.userAuthorities.add(new UserAuthority(this, authority));
	}
}
