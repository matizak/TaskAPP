package com.matias.taskapp.client;

import com.matias.taskapp.config.FeignConfig;
import com.matias.taskapp.dtos.TaskDto;
import feign.Body;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Lazy
@FeignClient(name = "task-client", url = "${feign.client.config.task.listOfServers}",  configuration = FeignConfig.class)
public interface TaskClient {
    @GetMapping(value = "/api/tasks/all")
    List<TaskDto> getAllTasks() throws Exception;

    @GetMapping(value = "/api/tasks/{id}")
    TaskDto getTask(@PathVariable("id") Long id) throws Exception;

    @PostMapping(value = "/api/tasks")
    TaskDto create(TaskDto taskDto) throws Exception;

    @PutMapping(value = "/api/tasks/{id}")
    TaskDto edit(@PathVariable("id") Long id, TaskDto taskDto) throws Exception;

    @DeleteMapping(value = "/api/tasks/{id}")
    TaskDto delete(@PathVariable("id") Long id) throws Exception;

}
