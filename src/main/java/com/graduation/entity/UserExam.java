package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.tracing.dtrace.ArgsAttributes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author Mercy
 * @since 2022-04-18
 */
@Getter
@Setter
@NoArgsConstructor
@Data
@TableName("user_exam")
@ApiModel(value = "UserExam对象", description = "")
public class UserExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("报名者的id")
    private Long userId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("相关考试id")
    private Long examId;

    @ApiModelProperty("是否缴费")
    private Boolean ispay;

    @ApiModelProperty("成绩")
    private Double score;

    @TableField(exist = false)
    private List<QualificationExam> exams;

    @TableField(exist = false)
    private List<Competition> competitions;

    @TableField(exist = false)
    private List<SysUser> users;


}
