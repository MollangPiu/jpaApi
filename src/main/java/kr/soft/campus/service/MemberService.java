package kr.soft.campus.service;

import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Area;
import kr.soft.campus.domain.Member;
import kr.soft.campus.repository.AreaRepository;
import kr.soft.campus.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService
{

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Transactional
    public void regist(Member member, long areaIdx) {

        Area area = areaRepository.findById(areaIdx);
        member.setArea(area);

        memberRepository.save(member);
    }


    public boolean login(Member member) {
        Member member1 = memberRepository.findByUserId(member.getUserId());
        if(member1 != null && member1.getUserPw().equals(member.getUserPw())) {
            return true;
        }

        return false;
    }
}
