package cn.com.qjun.cardboard.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author RenQiang
 * @date 2022/6/24
 */
@Data
@ApiModel(value = "开销统计结果")
public class ExpenseReportDto implements Serializable {
    private static final long serialVersionUID = -4314473396577482464L;

    @ApiModelProperty(value = "开销分类")
    private String category;
    @ApiModelProperty(value = "总金额")
    private BigDecimal money;
    @ApiModelProperty(value = "日期")
    private String date;
}
