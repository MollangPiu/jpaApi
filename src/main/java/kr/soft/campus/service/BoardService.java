package kr.soft.campus.service;

import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Board;
import kr.soft.campus.domain.Member;
import kr.soft.campus.repository.BoardRepository;
import kr.soft.campus.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 삽입
     * @param userId
     * @param board
     */
    @Transactional
    public void regist(String userId, Board board) {
        logger.info("regist");
        Member member = memberRepository.findByUserId(userId);

        board.setCreatedBy(member);
        board.setModifiedBy(member);

        boardRepository.save(board);

        logger.info("end");
    }

    @Transactional
    public void update(String userId, Board board) {
        logger.info("modify");
        //사용자 정보 불러오기
        Member member = memberRepository.findByUserId(userId);

        Board detail = boardRepository.findById(board.getIdx());

        board.setCreatedBy(detail.getCreatedBy());  //생성 자
        board.setCreated(detail.getCreated());      //생성 날짜
        board.setModifiedBy(member);
        boardRepository.update(board);
    }

    @Transactional
    public boolean remove(long idx) {
        logger.info("remove");

        if(boardRepository.findById(idx) == null) {
            return false;
        }
        boardRepository.delete(idx);

        return true;
    }

    @Transactional
    public boolean boardGood(long idx) {
        logger.info("modify");
        if(boardRepository.findById(idx) == null) {
            return false;
        }
        Board detail = boardRepository.findById(idx);
        return true;
    }
}
