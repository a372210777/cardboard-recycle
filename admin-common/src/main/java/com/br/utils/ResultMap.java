package com.br.utils;

import java.util.HashMap;

public class ResultMap extends HashMap<String, Object> {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8055801853819233821L;

    public ResultMap err() {
        this.put("success", Boolean.FALSE);
        putMsg("操作失败");
        putCode("-1");
        return this;
    }

    public ResultMap err(String code, String msg) {
        this.put("success", Boolean.FALSE);
        putMsg(msg);
        putCode(code);
        return this;
    }

    public ResultMap success() {
        this.put("success", Boolean.TRUE);
        if (getCode() == null)
            putCode("200");
        return this;
    }

    public Boolean isSuccess() {
        return (Boolean) this.get("success");
    }

    public ResultMap putCode(String code) {
        this.put("code", code);
        return this;
    }

    public String getCode() {
        return (String) this.get("code");
    }

    public ResultMap putMsg(String msg) {
        this.put("msg", msg);
        return this;
    }

    public String getMsg() {
        return (String) this.get("msg");
    }

    public ResultMap putSubCode(String subCode) {
        this.put("subCode", subCode);
        return this;
    }

    public String getSubCode() {
        return (String) this.get("subCode");
    }

}
