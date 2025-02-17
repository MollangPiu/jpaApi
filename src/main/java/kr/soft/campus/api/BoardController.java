package kr.soft.campus.api;

import kr.soft.campus.domain.Board;
import kr.soft.campus.repository.BoardRepository;
import kr.soft.campus.service.BoardService;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/board")
public class BoardController {


    Logger logger = LoggerFactory.getLogger(BoardController.class);

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    /**
     * 게시글 등록하기
     * @param req
     * title: 제목
     * content: 내용
     * memberId: Login Id
     */
    @PostMapping("/regist")
    public ResponseEntity<ResponseData> regist(@RequestBody BoardRegistReq req) {
        ResponseData responseData = new ResponseData();
        Board board = new Board();
        board.setContent(req.getContent());
        board.setTitle(req.getTitle());

        boardService.regist(req.memberId, board);

        return ResponseEntity.ok(responseData);
    }

    /**
     * 전체 조회
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<?> list(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "created", required = false, defaultValue = "") String created
    ) {
        ResponseData responseData = new ResponseData();

        List<BoardListRes> lists = boardService.findSearch(keyWord, created).stream().map(board ->
            new BoardListRes(board)
        )
        .collect(toList());

        responseData.setData(lists);
        if(lists == null || lists.size() == 0) {
            responseData.setMsg("null");
        }

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/find")
    public ResponseEntity<?> find(@RequestParam(name = "boardId") long boardId) {
        ResponseData responseData = new ResponseData();
        Board board = boardRepository.findById(boardId);

        BoardDetailRes detail = new BoardDetailRes(board);
        responseData.setData(detail);


        return ResponseEntity.ok(responseData);

    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody BoardModifyReq req) {
        ResponseData responseData = new ResponseData();
        Board board = new Board();
        board.setContent(req.getContent());
        board.setTitle(req.getTitle());
        board.setIdx(req.getBoardId());
        boardService.update(req.memberId, board);

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestBody BoardIdReq
 req) {
        ResponseData responseData = new ResponseData();
        logger.info("req: {}", req.getBoardId());
        if(!boardService.remove(req.getBoardId())) {
            responseData.setCode(500);
            responseData.setMsg("null");
        }

        return ResponseEntity.ok(responseData);
    }
    
    @PostMapping("/good")
    public ResponseEntity<ResponseData> goodUp(@RequestBody BoardIdReq req) {
        ResponseData responseData = new ResponseData();

        logger.info("req: {}", req.getBoardId());
        if(!boardService.boardGood(req.getBoardId())) {
            responseData.setCode(500);
            responseData.setMsg("null");
        }

        return ResponseEntity.ok(responseData);
    }

    @Data
    static class BoardIdReq
 {
        private long boardId;
    }

    @Data
    static class BoardModifyReq {
        private long boardId;
        private String title;
        private String content;
        private String memberId;
    }

    @Data
    static class BoardDetailRes {
        private String title;
        private String content;
        private String memberId;
        private int boardGood;
        private LocalDateTime createdAt;

        BoardDetailRes(Board board) {
            this.title = board.getTitle();
            this.content = board.getContent();
            this.boardGood = board.getGood();
            this.memberId = board.getCreatedBy().getUserId();
            this.createdAt = board.getCreated();
        }
    }

    /**
     * List 용
     */
    @Data
    static class BoardListRes {
        private long boardIdx;
        private String title;
        private String memberId;
        private int boardGood;
        private LocalDateTime createdAt;

        BoardListRes(Board board) {
            this.boardIdx = board.getIdx();
            this.title = board.getTitle();
            this.boardGood = board.getGood();
            this.memberId = board.getCreatedBy().getUserId();
            this.createdAt = board.getCreated();
        }
    }

    /**
     * Regist 용
     */
    @Data
    static class BoardRegistReq{

        private String title;
        private String content;
        private String memberId;
    }
}
