package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemRepository {

    @Autowired
    private EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    /**
     * 아이템 전체 찾기
     * @return
     */
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    /**
     * 상세 찾기
     * @param id
     * @return
     */
    public Item findById(int id) {
        return em.find(Item.class, id);
    }

    @Transactional
    public void itemGoodUp(int idx) {
        em.createNamedQuery("Item.goodUp")
                .setParameter("id", idx)
                .executeUpdate();
    }

    public List<Item> findByName(String name) {
        return em.createNamedQuery("Item.findByName")
                .setParameter("item", name).getResultList();
    }

}
