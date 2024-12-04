package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import kr.soft.campus.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    @Autowired
    private EntityManager em;

    public void save(Category category) {
        em.persist(category);
    }

    public Category findById(int id) {
        return em.find(Category.class, id);
    }

}
