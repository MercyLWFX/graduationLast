package com.graduation.entity;

import java.io.Serializable;

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
 * @since 2022-04-11
 */
@Getter
@Setter
  @ApiModel(value = "Files对象", description = "")
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("文件id")
      @JsonFormat(shape = JsonFormat.Shape.STRING)
        private Long id;

      @ApiModelProperty("文件名称")
      private String filename;

      @ApiModelProperty("文件类型")
      private String type;

      @ApiModelProperty("文件大小(KB)")
      private Long size;

      @ApiModelProperty("文件链接")
      private String url;

      @ApiModelProperty("文件上传人")
      @JsonFormat(shape = JsonFormat.Shape.STRING)
      private Long userid;

      @ApiModelProperty("是否删除")
      private Boolean delet;

      @ApiModelProperty("是否禁用链接")
      private Boolean enable;

      @ApiModelProperty("文件md5")
      private String md5;


}
