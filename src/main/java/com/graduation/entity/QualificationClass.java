package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
@TableName("qualification_class")
@ApiModel(value = "QualificationClass对象", description = "")
@Data
public class QualificationClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("实施部门编号")
    @TableId(value = "category_id", type = IdType.AUTO)
    private Integer categoryId;


    @ApiModelProperty("实施部门")
    @TableField("category_name")
    private String categoryName;

    @ApiModelProperty("分类")
    private String classification;

    @ApiModelProperty("部门归属类别代码")
    private String ascription;

    @ApiModelProperty("部门描述")
    private String description;

    @TableField(exist = false)
    private List<QualificationExam> exams;

}
