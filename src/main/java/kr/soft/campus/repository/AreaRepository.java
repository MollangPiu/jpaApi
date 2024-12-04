package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaRepository {

    @Autowired
    private EntityManager em;

    public Area findById(long id) {
        return em.find(Area.class, id);
    }

    public List<Area> findAll() {
        return em.createQuery("from Area", Area.class).getResultList();
    }

    @Transactional
    public void save(Area area) {
        em.persist(area);
    }
}
