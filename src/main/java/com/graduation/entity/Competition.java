package com.graduation.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @since 2022-04-19
 */
@Getter
@Setter
@Data
@ApiModel(value = "Competition对象", description = "")
public class Competition implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("竞赛id")
    private Long id;

    @ApiModelProperty("比赛名称")
    private String name;

    @ApiModelProperty("报名开始时间")
    private LocalDate start;

    @ApiModelProperty("报名结束时间")
    private LocalDate end;

    @ApiModelProperty("比赛性质")
    private String types;

    @ApiModelProperty("主办方")
    private String master;

    @ApiModelProperty("图片地址")
    private String imgurl;

    @ApiModelProperty("比赛详情信息")
    private String detail;

    @ApiModelProperty("比赛地址")
    private String address;

    @ApiModelProperty("报名人数")
    private Integer count;

    @ApiModelProperty("报名费用")
    private Double expense;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("企业id")
    private Long userId;

    @TableField(exist = false)
    private Double score;

}
