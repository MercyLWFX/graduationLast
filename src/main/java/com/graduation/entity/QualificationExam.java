package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

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
 * @since 2022-04-14
 */
@Getter
@Setter
@TableName("qualification_exam")
@ApiModel(value = "QualificationExam对象", description = "")
public class QualificationExam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("资格证书id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("资格证书名称")
    private String examName;

    @ApiModelProperty("考试类别代码")
    private String ascription;

    @ApiModelProperty("资格类别")
    private String classes;

    @ApiModelProperty("实施部门")
    private String dept;

    @ApiModelProperty("设定依据")
    private String basis;
    @ApiModelProperty("报名人数")
    private Integer count;

    @ApiModelProperty("开始时间")
    private String start;

    @ApiModelProperty("报名截止时间")
    private String end;

    @ApiModelProperty("资格证书图像")
    private String imgurl;

    @ApiModelProperty("详情介绍")
    private String detail;

    @ApiModelProperty("报名费用")
    private double expense;

}
