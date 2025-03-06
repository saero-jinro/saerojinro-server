package goorm.saerojinro.common.domain.blacklist.domain;

public interface BlackListRepository {
	void save(BlackList blackList);

	boolean existsById(String accessToken);
}
