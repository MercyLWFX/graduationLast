package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sys_dict")
public class Dict {
    private String name;
    private String value;
    private String type;
}
