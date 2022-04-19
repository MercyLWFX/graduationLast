package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author Mercy
 * @since 2022-05-03
 */
@Getter
@Setter
@TableName("application_form")
@ApiModel(value = "ApplicationForm对象", description = "")
public class ApplicationForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("身份证号")
    private String idcard;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("出生年月日")
    private LocalDate birthday;

    @ApiModelProperty("通讯地址")
    private String address;

    @ApiModelProperty("邮编")
    private Integer postCode;

    @ApiModelProperty("手机号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long phone;

    @ApiModelProperty("电子邮件")
    private String email;

    @ApiModelProperty("在读学校")
    private String school;

    @ApiModelProperty("学位层次")
    private String educationalLevel;

    @ApiModelProperty("毕业院校")
    private String graduated;

    @ApiModelProperty("毕业时间")
    private LocalDate graduatedDate;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("工作单位名称")
    private String workplace;

    @ApiModelProperty("工作单位地址")
    private String workAddress;

    @ApiModelProperty("职务")
    private String job;


}
