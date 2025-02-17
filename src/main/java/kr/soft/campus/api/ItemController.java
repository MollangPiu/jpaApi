package kr.soft.campus.api;

import kr.soft.campus.domain.Item;
import kr.soft.campus.repository.ItemRepository;
import kr.soft.campus.service.ItemService;
import kr.soft.campus.util.ResponseData;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @PostMapping("/regist")
    public ResponseEntity<ResponseData> regist(@RequestBody RegistItemReq req) {
        ResponseData data = new ResponseData();
        Item item = new Item();
        item.setName(req.getName());
        item.setPrice(req.getPrice());

        //저장
        itemService.regist(item, req.getCategoryId());

        return ResponseEntity.ok(data);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseData> getAll(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyWord,
            @RequestParam(value = "categoryIdx", required = false, defaultValue = "") String category
    ) {
        ResponseData data = new ResponseData();

        int categoryIdx = 0;
        if(!category.equals("")) {
            categoryIdx = Integer.parseInt(category);
        }

        List<ItemListRes> items = itemService.findSearch(categoryIdx, keyWord)
                .stream().map(o -> new ItemListRes(o))
                .collect(toList());


        data.setData(items);

        return ResponseEntity.ok(data);
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseData> findById(@RequestParam(name="itemIdx", required = false) int id) {
        ResponseData data = new ResponseData();

        Item item = itemRepository.findById(id);
        ItemListRes itemListRes = new ItemListRes(item);
        data.setData(itemListRes);


        return ResponseEntity.ok(data);
    }

    @GetMapping("/findByName")
    public ResponseEntity<ResponseData> findByName(@RequestParam(name="name", required = false) String name) {
        ResponseData data = new ResponseData();
        List<ItemListRes> items = itemRepository.findByName(name)
                .stream().map(o -> new ItemListRes(o))
                .collect(toList());

        data.setData(items);
        return ResponseEntity.ok(data);

    }

    @GetMapping("/good")
    public ResponseEntity<ResponseData> getGood(@RequestParam(name="itemIdx", required = false) int id) {
        ResponseData data = new ResponseData();
        itemRepository.itemGoodUp(id);

        return ResponseEntity.ok(data);
    }



    @Data
    static class ItemListRes {
        private int itemIdx;
        private String name;
        private int price;
        private int good;
        private int categoryId;
        private String categoryName;

        public ItemListRes() {}
        public ItemListRes(Item item) {
            this.itemIdx = item.getIdx();
            this.name = item.getName();
            this.price = item.getPrice();
            this.good = item.getGood();
            this.categoryId = item.getCategory().getIdx();
            this.categoryName = item.getCategory().getName();
        }
    }

    @Data
    static class RegistItemReq {
        private String name;
        private int price;
        private int good;
        private int categoryId;
    }
}
