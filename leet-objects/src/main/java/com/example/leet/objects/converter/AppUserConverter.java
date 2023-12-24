package com.example.leet.objects.converter;

import com.example.leet.dao.entity.AppUser;
import com.example.leet.objects.dto.AppUserDTO;

public class AppUserConverter {

    private AppUserConverter(){}

    public static AppUserDTO entityToDTO(AppUser appUser){
        AppUserDTO appUserDTO = new AppUserDTO();
        appUserDTO.setEmail(appUser.getEmail());
        return appUserDTO;
    }

    public static AppUser dtoToEntity(AppUserDTO appUserDTO){
        AppUser appUser = new AppUser();
        appUser.setEmail(appUserDTO.getEmail());
        return appUser;
    }
}
