package mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;

public class FakeUserRepository implements UserRepository {
	private final List<User> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(1);

	@Override
	public User save(User user) {
		User newUser = User.builder()
			.id(sequence.getAndIncrement())
			.oauthIdentity(user.getOauthIdentity())
			.email(user.getEmail())
			.password(user.getPassword())
			.name(user.getName())
			.role(user.getRole())
			.provider(user.getProvider())
			.interest(user.getInterest())
			.build();

		TestEntityUtils.setCreatedAt(newUser, LocalDateTime.now());
		data.add(newUser);
		return newUser;
	}
}
