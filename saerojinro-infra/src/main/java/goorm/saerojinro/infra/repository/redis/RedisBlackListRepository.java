package goorm.saerojinro.infra.repository.redis;

import org.springframework.data.repository.CrudRepository;

import goorm.saerojinro.domain.blacklist.domain.BlackList;

public interface RedisBlackListRepository extends CrudRepository<BlackList, String> {
}
