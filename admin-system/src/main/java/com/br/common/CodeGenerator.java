package com.br.common;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class CodeGenerator {


    /**
     * 生成库区编码
     * 规则：楼层编码+ '-' + 生成字母
     */
    public static String genAreaCode(String code, Integer areaNums) {
        Assert.hasText(code, "CodeGenerator.genAreaCode >> parameter 'code' not be null");
        return genCode("%s-%s", code, genLetter(areaNums));
    }

    /**
     * 生成楼层编码
     * 规则：仓库编码+ '-' +楼层号‘L’
     */
    public static String genFloorCode(String code, Integer floorNo) {
        Assert.hasText(code, "CodeGenerator.genFloorCode >> parameter 'code' not be null");
        Assert.notNull(floorNo, "CodeGenerator.genFloorCode >> parameter 'floorNo' not be null");
        return genCode("%s-%dL", code, floorNo);
    }

    public static String genCode(String template, Object... params) {
        return String.format(template, params);
    }

    /**
     * 生成字母规则：A到Z，Z满后AA开始
     * "A-Z"[65-90]
     */
    public static String genLetter(Integer areaNums) {
        if (areaNums == null) {
            areaNums = 1;
        }
        if (areaNums.equals(26)) {
            return "Z";
        }
        int cycleNums = areaNums / 26;
        char remainder = 32;
        if (areaNums % 26 != 0) {
            System.out.println("areaNums % 26: "+ (areaNums % 26));
            remainder = (char) (areaNums % 26 + 64);
        }
        return (fillA(cycleNums) + remainder).trim();
    }

    private static String fillA(int cycleNums) {
        if (cycleNums > 0) {
            StringBuilder builder = new StringBuilder();
            while (cycleNums > 0) {
                builder.append("A");
                cycleNums-- ;
            }
            return builder.toString();
        } else {
            return "";
        }
    }
}
