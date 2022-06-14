package com.br.modules.biz.service.dto;

import com.br.modules.biz.domain.WmsBaseFloorInfo;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
* @author lin
* @date 2022-06-08
**/
@Data
public class WmsBaseWarehouseAreaInfoDto implements Serializable {

    /** 主键 */
    @ApiModelProperty("主键")
    private Long pkWarehouseArea;

    /** 所属楼层 */
    @ApiModelProperty("所属楼层")
    private WmsBaseFloorInfo floorInfo;

    /** 库区编码 */
    @ApiModelProperty("库区编码")
    private String code;

    /** 库区名称 */
    @ApiModelProperty("库区名称")
    private String name;

    /** 库区类型 */
    @ApiModelProperty("库区类型")
    private Integer type;

    /** 状态（1启用，0禁用） */
    @ApiModelProperty("状态（1启用，0禁用）")
    private Integer status;

    /** 创建人id */
    @ApiModelProperty("创建人id")
    private String createBy;

    /** 创建时间 */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /** 更新人id */
    @ApiModelProperty("更新人id")
    private String updateBy;

    /** 更新时间 */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /** 有效标识（1有效，0无效） */
    @ApiModelProperty("有效标识（1有效，0无效）")
    private Integer isValid;
}