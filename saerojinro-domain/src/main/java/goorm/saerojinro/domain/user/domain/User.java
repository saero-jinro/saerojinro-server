package goorm.saerojinro.domain.user.domain;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.common.domain.Provider.GOOGLE;
import static goorm.saerojinro.common.domain.Provider.KAKAO;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import org.springframework.security.crypto.password.PasswordEncoder;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Interest;
import goorm.saerojinro.common.domain.Provider;
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
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long id;

	@Column(unique = true)
	private String oauthIdentity;

	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	@Column(nullable = false)
	private String name;

	@Enumerated(STRING)
	private BaseRole role;

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

	public static User createKakaoUser(String oauthIdentity, String name){
		return User.builder()
			.oauthIdentity(oauthIdentity)
			.name(name)
			.provider(KAKAO)
			.role(ATTENDEE)
			.build();
	}

	public static User createGoogleUser(String oauthIdentity, String name, String email){
		return User.builder()
			.oauthIdentity(oauthIdentity)
			.name(name)
			.email(email)
			.provider(GOOGLE)
			.role(ATTENDEE)
			.build();
	}

	public void updateName(String name){
		this.name = name;
	}

	public void updateRole(BaseRole role){
		this.role = role;
	}

	public void updateInterest(Interest interest){
		this.interest = interest;
	}

	public boolean isPasswordMatched(String rawPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(rawPassword, password);
	}
}