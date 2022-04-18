package com.graduation.controller.dto;


import com.graduation.entity.SysMenu;
import lombok.Data;

import java.util.List;

@Data
public class UserDTo {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String password;
    private String avatarUrl;
    private String token;
    private String rolename;
    private List<SysMenu> menus;
}
