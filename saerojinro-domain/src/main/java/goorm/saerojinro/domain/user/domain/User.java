package goorm.saerojinro.domain.user.domain;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.common.domain.Provider.KAKAO;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import java.util.Collection;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Category;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseTimeEntity implements UserDetails {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(unique = true)
	private String oauthIdentity;

	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	private String profileImage;

	@Column(nullable = false)
	private String name;

	@Enumerated(STRING)
	private BaseRole role;

	@Enumerated(STRING)
	private Provider provider;

	@Enumerated(STRING)
	private Category interest;

	public static User createAdmin(String email, String password, String name) {
		return User.builder()
			.email(email)
			.password(password)
			.name(name)
			.role(ADMIN)
			.build();
	}

	public static User createKakaoUser(String oauthIdentity, String name, String email, String profileImage){
		return User.builder()
			.oauthIdentity(oauthIdentity)
			.name(name)
			.email(email)
			.profileImage(profileImage)
			.provider(KAKAO)
			.role(ATTENDEE)
			.build();
	}

	public void updateName(String name){
		this.name = name;
	}

	public void updateEmail(String email){
		this.email = email;
	}

	public void updateProfileImage(String profileImage){
		this.profileImage = profileImage;
	}

	public void updateInterest(Category interest){
		this.interest = interest;
	}

	public boolean isPasswordMatched(String rawPassword, PasswordEncoder passwordEncoder) {
		return passwordEncoder.matches(rawPassword, password);
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}
}