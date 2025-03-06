package mock.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import goorm.saerojinro.common.domain.blacklist.domain.BlackList;
import goorm.saerojinro.common.domain.blacklist.domain.BlackListRepository;

public class FakeBlackListRepository implements BlackListRepository {
	private final Map<String, BlackList> data = new ConcurrentHashMap<>();

	@Override
	public void save(BlackList blackList) {
		data.put(blackList.getAccessToken(), blackList);
	}

	@Override
	public boolean existsById(String accessToken) {
		return data.containsKey(accessToken);
	}
}
