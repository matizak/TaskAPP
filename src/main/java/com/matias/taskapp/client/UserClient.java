package com.matias.taskapp.client;

import com.matias.taskapp.config.FeignConfig;
import com.matias.taskapp.dtos.LoginDto;
import com.matias.taskapp.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;

@Lazy
@FeignClient(name = "user-client", url = "${feign.client.config.task.listOfServers}", configuration = FeignConfig.class)
public interface UserClient {
    @PostMapping(value = "/api/users/login")
    UserDto login(LoginDto loginDto) throws Exception;

    @PostMapping(value = "/api/users")
    UserDto create(UserDto userDto) throws Exception;

}

