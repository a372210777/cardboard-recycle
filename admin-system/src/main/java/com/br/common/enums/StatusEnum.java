package com.br.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@AllArgsConstructor
public enum StatusEnum{

    STATUS_ENABLE(1, "启用"),
    STATUS_DISABLE(0, "禁用");

    private final Integer value;
    private final String label;

    public static Integer findValueByLabel(String label) {
        Assert.hasText(label, "StatusEnum.findValueByLabel >> label参数不能为空");
        for (StatusEnum data: StatusEnum.values()) {
            if (data.getLabel().equals(label)) {
                return data.getValue();
            }
        }
        return null;
    }

    public static String findLabelByValue(Integer value) {
        Assert.notNull(value, "StatusEnum.findLabelByValue >> value参数不能为空");
        for (StatusEnum data: StatusEnum.values()) {
            if (data.getValue().equals(value)) {
                return data.getLabel();
            }
        }
        return null;
    }
}
