package goorm.saerojinro.common.domain.blacklist.domain;

public interface BlackListRepository {
	void add(String accessToken, Long ttlInSecond);

	boolean isBlackListed(String accessToken);
}
