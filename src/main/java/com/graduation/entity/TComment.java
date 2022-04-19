package com.graduation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.List;

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
 * @since 2022-04-21
 */
@Getter
@Setter
@TableName("t_comment")
@ApiModel(value = "TComment对象", description = "")
public class TComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("内容")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("评论人id")
    private Long userId;


//    @TableField(exist = false)
//    private String pname;

    @ApiModelProperty("评论时间")
    private String time;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("父id")
    private Integer pid;
    @TableField(exist = false)
    private String pNickname;//父节点的用户昵称

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @TableField(exist = false)
    private Long pUserId;//父节点的用户id

    @ApiModelProperty("最上级评论id")
    private Integer originId;

    @ApiModelProperty("关联文章的id")
    private Integer articleId;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String avatarUrl;

    @TableField(exist = false)
    private List<TComment> children;

}
