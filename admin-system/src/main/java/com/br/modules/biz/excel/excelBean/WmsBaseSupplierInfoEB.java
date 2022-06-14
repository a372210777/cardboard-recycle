package com.br.modules.biz.excel.excelBean;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class WmsBaseSupplierInfoEB {

    @ExcelProperty( value = "编码",index = 0)
    private String code;

    @ExcelProperty( value = "简称",index = 1)
    private String shortName;

    @ExcelProperty( value = "全称",index = 2)
    private String fullName;

    @ExcelProperty( value = "签约方式",index = 3)
    private String signType;

    @ExcelProperty( value = "结算方式",index = 4)
    private String settlementType;

    @ExcelProperty( value = "结算周期",index = 5)
    private Integer settlementCycle;

    @ExcelProperty( value = "结算日",index = 6)
    private Integer settlementDays;

    @ExcelProperty( value = "责任客服",index = 7)
    private String username;


}
