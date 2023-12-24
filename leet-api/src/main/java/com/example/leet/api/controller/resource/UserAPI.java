package com.example.leet.api.controller.resource;

import com.example.leet.objects.converter.AppUserConverter;
import com.example.leet.objects.dto.AppUserDTO;
import com.example.leet.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rest/users")
public class UserAPI {

    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService){
        this.userService = userService;
    }

    @PostMapping("create")
    public AppUserDTO creteUser(@RequestBody AppUserDTO appUserDTO){
        return AppUserConverter.entityToDTO(userService.createUser(AppUserConverter.dtoToEntity(appUserDTO)));
    }

    @PostMapping("follow/{username}/{userId}")
    public void subscribeUser(@PathVariable(name = "username")String username, @PathVariable(name = "userId")Integer userId){
        userService.subscribeToLeetCodeUser(username, userId);
    }

}
