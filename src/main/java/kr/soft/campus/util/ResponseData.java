package kr.soft.campus.util;

import lombok.Data;

@Data
public class ResponseData {
    private String code;
    private String msg;

    private Object data;
    public ResponseData() {
        this.code = "200";
        this.msg = "success";
    }
}
