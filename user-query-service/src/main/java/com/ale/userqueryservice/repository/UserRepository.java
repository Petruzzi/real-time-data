package com.ale.userqueryservice.repository;


import com.ale.userqueryservice.model.UserDto;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCouchbaseRepository<UserDto, UUID> {
}
