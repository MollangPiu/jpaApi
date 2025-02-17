package kr.soft.campus.api;

import kr.soft.campus.domain.Area;
import kr.soft.campus.repository.AreaRepository;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/area")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AreaRepository areaRepository;

    //regist
    @PostMapping("/regist")
    public ResponseData register(@RequestBody Area area) {
        logger.info(area.toString());
        ResponseData responseData = new ResponseData();
        areaRepository.save(area);

        return responseData;
    }

    //List 불러오기
    @GetMapping("/list")
    public ResponseData list() {

        ResponseData responseData = new ResponseData();

        List<AreaListRes> lists = areaRepository.findAll()
                .stream().map(o -> new AreaListRes(o))
                .collect(toList());

        responseData.setData(lists);

        return responseData;
    }

    @GetMapping("/findById")
    public ResponseData byId(@RequestParam("id") long id) {
        ResponseData responseData = new ResponseData();
        AreaListRes area = new AreaListRes(areaRepository.findById(id));
        responseData.setData(area);

        return responseData;
    }

    @Data
    static class AreaListRes {
        private long idx;
        private String areaName;
        public AreaListRes(Area area) {
            this.idx = area.getIdx();
            this.areaName = area.getAreaName();
        }
    }
}
