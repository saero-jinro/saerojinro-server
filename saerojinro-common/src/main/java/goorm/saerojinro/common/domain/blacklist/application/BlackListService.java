package goorm.saerojinro.common.domain.blacklist.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.blacklist.domain.BlackList;
import goorm.saerojinro.common.domain.blacklist.domain.BlackListRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlackListService {
	private final BlackListRepository blackListRepository;

	public void add(String accessToken) {
		BlackList blackList = BlackList.of(accessToken);
		blackListRepository.save(blackList);
	}

	public boolean isBlackListed(String accessToken) {
		return blackListRepository.existsById(accessToken);
	}
}
