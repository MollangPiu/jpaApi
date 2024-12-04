package kr.soft.campus.service;

import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Category;
import kr.soft.campus.domain.Item;
import kr.soft.campus.repository.CategoryRepository;
import kr.soft.campus.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 등록하기
     * @param item
     * @param categoryIdx
     */
    @Transactional
    public void regist(Item item, int categoryIdx) {

        Category category = categoryRepository.findById(categoryIdx);

        item.setCategory(category);

        itemRepository.save(item);
    }

    public List<Item> findSearch(int categoryIdx, String keyword) {
        List<Item> items = itemRepository.findAll();
        items = search(items, keyword, categoryIdx); //seach
        return items;
    }

    /**
     * 아이템 검색하기
     * @param items
     * @param keyword
     * @param categoryIdx
     * @return
     */
    public List<Item> search(List<Item> items, String keyword, int categoryIdx) {
        Stream<Item> stream = items.stream();

        if (categoryIdx != 0) {
            stream = stream.filter(i -> i.getCategory().getIdx() == categoryIdx);
        }
        if (keyword != null && !keyword.isEmpty()) {
            String finalKeyword = keyword.toLowerCase();
            stream = stream.filter(i -> i.getName().toLowerCase().contains(finalKeyword));
        }

        return stream.collect(Collectors.toList());
    }


}
