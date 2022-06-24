package cn.com.qjun.cardboard.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author RenQiang
 * @date 2022/6/24
 */
@Data
@ApiModel(value = "出入库统计结果")
public class StockReportDto implements Serializable {
    private static final long serialVersionUID = 5027116903698869207L;

    @ApiModelProperty(value = "单号")
    private String orderId;
    @ApiModelProperty(value = "明细ID")
    private Integer itemId;
    @ApiModelProperty(value = "物料名称")
    private String materialName;
    @ApiModelProperty(value = "物料类别")
    private String materialCategory;
    @ApiModelProperty(value = "仓库名称")
    private String warehouseName;
    @ApiModelProperty(value = "数量")
    private Integer quantity;
    @ApiModelProperty(value = "日期")
    private String date;
    @ApiModelProperty(value = "订单类型")
    private String orderType;
}
