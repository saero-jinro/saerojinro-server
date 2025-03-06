package mock.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import goorm.saerojinro.common.domain.blacklist.domain.BlackListRepository;

public class FakeBlackListRepository implements BlackListRepository {
	private final Map<String, String> data = new ConcurrentHashMap<>();

	@Override
	public void add(String accessToken, Long ttlInSecond) {
		data.put(accessToken, ttlInSecond.toString());
	}

	@Override
	public boolean isBlackListed(String accessToken) {
		return data.containsKey(accessToken);
	}
}
