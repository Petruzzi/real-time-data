package com.ale.userqueryservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;
import org.springframework.data.couchbase.config.*;

@Configuration
@EnableReactiveCouchbaseRepositories("com.ale.userqueryservice.repository")
public class ReactiveCouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    @Override
    public String getConnectionString() {
        return "localhost";
    }

    @Override
    public String getUserName() {
        return "admin";
    }

    @Override
    public String getPassword() {
        return "19011983";
    }

    @Override
    public String getBucketName() {
        return "users";
    }
}
