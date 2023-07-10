package com.matias.taskapp.beans;

import com.matias.taskapp.client.TaskClient;
import com.matias.taskapp.client.UserClient;
import com.matias.taskapp.dtos.LoginDto;
import com.matias.taskapp.dtos.TaskDto;
import com.matias.taskapp.dtos.UserDto;
import com.matias.taskapp.enums.TaskStatus;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Named
@ImportAutoConfiguration({FeignAutoConfiguration.class})
@SessionScoped
public class WebBean implements Serializable {

    public WebBean() {
        this.clearUserNew();
    }

    @Autowired
    TaskClient taskClient;

    @Autowired
    UserClient userClient;

    private UserDto userDto;

    private UserDto userDtoNew;

    private TaskDto taskDto;

    private String username;

    private String password;

    private String status;

    public List<TaskDto> getTasks() throws Exception {
        return taskClient.getAllTasks();
    }

    public String login() throws Exception {
        UserDto userLog = this.userClient.login(new LoginDto(this.getUsername(), this.getPassword()));
        this.setUserDto(userLog);
        return "dash";
    }

    public String logout() throws Exception {
        this.setUserDto(null);
        this.setUsername(null);
        this.setPassword(null);
        return "index";
    }

    public void clearUserNew() {
        this.setUserDtoNew(new UserDto());
    }

    public void clearTask() {
        this.setStatus(null);
        this.setTaskDto(new TaskDto());
    }

    public void createUser() throws Exception {
        this.userClient.create(this.getUserDtoNew());
        this.clearUserNew();
        PrimeFaces.current().executeScript("PF('dialogRegistro').hide()");
        PrimeFaces.current().ajax().update("formNew");
    }

    public List<SelectItem> getSelectItemsEstado() {
        List<SelectItem> selectItems = new ArrayList<>();
        for (TaskStatus taskStatusAux : TaskStatus.values()) {
            selectItems.add(new SelectItem(taskStatusAux.name()));
        }
        return selectItems;
    }

    public void createTask() throws Exception {
        TaskDto taskNew = this.getTaskDto();
        taskNew.setStatus(TaskStatus.valueOf(this.getStatus()));
        taskNew.setUser(this.getUserDto());
        this.taskClient.create(taskNew);
        PrimeFaces.current().executeScript("PF('dialogNewTask').hide()");
        PrimeFaces.current().ajax().update("formNewTask", "formDash");
    }

    public void loadTask() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        Long id = Long.parseLong(myRequest.getParameter("id"));
        TaskDto taskEdit = this.taskClient.getTask(id);
        this.setTaskDto(taskEdit);
        if(this.getTaskDto()!=null){
            this.setStatus(this.getTaskDto().getStatus().name());
        }
    }

    public void editTask() throws Exception {
        TaskDto taskNew = this.getTaskDto();
        taskNew.setStatus(TaskStatus.valueOf(this.getStatus()));
        taskNew.setUser(this.getUserDto());
        this.taskClient.edit(taskNew.getId(), taskNew);
        PrimeFaces.current().executeScript("PF('dialogEditTask').hide()");
        PrimeFaces.current().ajax().update("formNewTask", "formDash");
    }

    public void deleteTask() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        Long id = Long.parseLong(myRequest.getParameter("id"));
        this.taskClient.delete(id);
        PrimeFaces.current().ajax().update("formDash");
    }
}
