package com.ale.userqueryservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Document
public class UserDto {
    @Id
    private UUID id = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String email;
}
