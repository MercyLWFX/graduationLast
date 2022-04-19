package com.graduation.entity;

import java.io.Serializable;

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
 * @since 2022-04-25
 */
@Getter
@Setter
@Data
@ApiModel(value = "Certify对象", description = "")
public class Certify implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("申请人id")
    private Long userId;

    @ApiModelProperty("企业法人营业执照")
    private String businessLicence;

    @ApiModelProperty("企业认证公函")
    private String certificationLetter;

    @ApiModelProperty("商标注册证")
    private String registrationCertificate;

    @ApiModelProperty("税务登记证")
    private String taxCertificate;

    @ApiModelProperty("是否认证通过")
    private Boolean agree;


}
