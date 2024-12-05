package kr.soft.campus.service;

import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Board;
import kr.soft.campus.domain.Item;
import kr.soft.campus.domain.Member;
import kr.soft.campus.repository.BoardRepository;
import kr.soft.campus.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Board> findSearch(String keyword, String created) {
        List<Board> boards = boardRepository.findAll();
        boards = search(boards, keyword, created); //seach
        return boards;
    }

    /**
     * 게시판 검색하기
     * @param boards
     * @param keyword
     * @param created
     * @return
     */
    public List<Board> search(List<Board> boards, String keyword, String created) {
        Stream<Board> stream = boards.stream();

        if (keyword != null && !keyword.isEmpty()) {
            String finalKeyword = keyword.toLowerCase();
            stream = stream.filter(i -> i.getTitle().toLowerCase().contains(finalKeyword));
        }

        if (created != null && !created.isEmpty()) {
            String finalKeyword = keyword.toLowerCase();
            stream = stream.filter(i -> i.getCreatedBy().getUserId().toLowerCase().contains(finalKeyword));
        }

        return stream.collect(Collectors.toList());
    }
}
