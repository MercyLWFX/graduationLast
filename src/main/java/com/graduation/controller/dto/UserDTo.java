package com.graduation.controller.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.graduation.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTo {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String avatarUrl;
    private String token;
    private String rolename;
    private Double money;
    private List<SysMenu> menus;
}
