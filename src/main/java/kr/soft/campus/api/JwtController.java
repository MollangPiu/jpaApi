package kr.soft.campus.api;

import kr.soft.campus.util.JWTUtil;
import kr.soft.campus.util.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/jwt")
@Slf4j
public class JwtController {

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/regist")
    public ResponseData regist(@RequestParam String id, @RequestParam String name) {
        ResponseData responseData = new ResponseData();
        HashMap<String, Object> map = (HashMap<String, Object>) jwtUtil.generateToken(new JWTUtil.JWTReq(id, name));

        responseData.setData(map);
        return responseData;

    }

    @GetMapping("/check")
    public ResponseData check(@RequestParam String jwt) {

        ResponseData responseData = new ResponseData();
        jwtUtil.extractKey(jwt);

        return responseData;
    }
}
