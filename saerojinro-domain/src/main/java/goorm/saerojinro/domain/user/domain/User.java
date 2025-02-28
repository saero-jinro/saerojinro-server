package goorm.saerojinro.domain.user.domain;

import static goorm.saerojinro.common.auth.domain.Role.ADMIN;
import static goorm.saerojinro.common.auth.domain.Role.ATTENDEE;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.security.crypto.password.PasswordEncoder;

import goorm.saerojinro.common.auth.domain.Provider;
import goorm.saerojinro.common.auth.domain.Role;
import goorm.saerojinro.common.auth.domain.Interest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@Table(name = "\"user\"")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class User {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	private String password;

	@Column(unique = true)
	private String oauthIdentity;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String name;

	@Enumerated(STRING)
	private Role role;

	@Enumerated(STRING)
	private Provider provider;

	@Enumerated(STRING)
	private Interest interest;

	public static User createAdmin(String email, String password, String name) {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.role(ADMIN)
			.build();
	}

	public static User kakaoOidcCreate(String oauthIdentity, Provider provider, String name){
		return User.builder()
			.oauthIdentity(oauthIdentity)
			.name(name)
			.provider(provider)
			.role(ATTENDEE)
			.build();
	}

	public static User googleOidcCreate(String oauthIdentity, Provider provider, String name, String email){
		return User.builder()
			.oauthIdentity(oauthIdentity)
			.name(name)
			.email(email)
			.provider(provider)
			.role(ATTENDEE)
			.build();
	}

	public void updateEmail(String email){
		this.email = email;
	}

	public void updateName(String name){
		this.name = name;
	}

	public void updateRole(Role role){
		this.role = role;
	}

	public void updateInterest(Interest interest){
		this.interest = interest;
	}

	public boolean isPasswordMatched(String rawPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(rawPassword, password);
	}
}
