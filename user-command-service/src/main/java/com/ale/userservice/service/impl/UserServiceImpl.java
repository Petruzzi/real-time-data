package com.ale.userservice.service.impl;

import com.ale.userservice.dto.UserDto;
import com.ale.userservice.entity.User;
import com.ale.userservice.properties.TopicProperties;
import com.ale.userservice.repository.UserRepository;
import com.ale.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final UserRepository userRepository;
    private final TopicProperties topicProperties;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @Transactional
    public Long createUser(UserDto userDto) {
        User user = new User();
        user.setFirstname(userDto.getFirstName());
        user.setLastname(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        publishEvent(userDto);
        return user.getId();
    }

    @Override
    public void updateUser(UserDto userDto) {

    }

    private void publishEvent(UserDto dto) {
        try {
            String value = OBJECT_MAPPER.writeValueAsString(dto);
            kafkaTemplate.send(topicProperties.getName(), value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
