package com.spp.cp.db;

import com.spp.cp.domain.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

}
