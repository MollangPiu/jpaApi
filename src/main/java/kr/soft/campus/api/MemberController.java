package kr.soft.campus.api;

import jakarta.persistence.Column;
import kr.soft.campus.domain.Member;
import kr.soft.campus.repository.MemberRepository;
import kr.soft.campus.service.MemberService;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/api/member")
public class MemberController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @PostMapping("/list")
    public ResponseEntity<?> list() {
        ResponseData data = new ResponseData();

        List<Member> members = memberRepository.findAllWithArea();
        List<MemberListRes> list = members.stream()
                .map(m -> new MemberListRes(m))
                .collect(toList());

        data.setData(list);
        return ResponseEntity.ok(data);
    }

    @PostMapping("/findId")
    public ResponseEntity<?> find(@RequestBody FindIdReq findIdReq) {
        ResponseData data = new ResponseData();
        logger.info("find: {}", findIdReq.getId());
        Member m = memberRepository.findByUserId(findIdReq.getId());
        if(m == null) {
            data.setData("Y");
        } else if( m != null) {
            data.setData("N");
            data.setMsg("no regist");
        }

        return ResponseEntity.ok(data);
    }

    /**
     *
     * @param member
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member) {

        ResponseData data = new ResponseData();
        logger.info("login: {}", member);
        if(memberService.login(member))  {
            data.setData("Y");
        } else {
            data.setData("N");
        }

        return ResponseEntity.ok(data);
    }

    /**
     * 회원가입 기능
     * @param memberRegistReq
     * @return
     */
    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody MemberRegistReq memberRegistReq) {
        ResponseData data = new ResponseData();
        logger.info("regist: {}", memberRegistReq);

        Member member = memberRegistReq.getMember();


        memberService.regist(member, memberRegistReq.getAreaIdx());

        return ResponseEntity.ok(data);
    }

    @Data
    static class MemberRegistReq {
        private String userId;
        private String userPw;
        private String userName;
        private String email;
        private String birth;
        private String gender;
        private long areaIdx;

        public Member getMember() {
            Member member = new Member();
            member.setUserId(userId);
            member.setUserPw(userPw);
            member.setUserName(userName);
            member.setEmail(email);
            member.setBirth(birth);
            member.setGender(gender);
            return member;
        }
    }

    @Data
    static class FindIdReq {
        private String id;
    }


    @Data
    static class Test{
        private String name;
        private String id;
    }

    @Data
    static class MemberListRes {
        private long idx;
        private String id;
        private String name;
        private long areaIdx;
        private String areaName;
        public MemberListRes(Member member) {
            this.idx = member.getIdx();
            this.id = member.getUserId();
            this.name = member.getUserName();
            this.areaIdx = member.getArea().getIdx();
            this.areaName = member.getArea().getAreaName();
        }
    }


}
