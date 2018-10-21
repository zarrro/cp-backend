package com.spp.cp.db;

import com.spp.cp.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
