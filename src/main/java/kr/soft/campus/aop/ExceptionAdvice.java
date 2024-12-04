package kr.soft.campus.aop;

import kr.soft.campus.util.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData> exception(Exception e) {
        e.printStackTrace();
        ResponseData responseData = new ResponseData();
        responseData.setCode("500");
        responseData.setMsg("error");
        return ResponseEntity.status(500).body(responseData);
    }
}
