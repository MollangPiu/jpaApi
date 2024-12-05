package kr.soft.campus.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepository {

    @Autowired
    private EntityManager em;

    /**
     * 저장
     * @param board
     */
    public void save(Board board) {
        em.persist(board);
    }

    /**
     * Board LIST 불러오기
     * @return
     */
    public List<Board> findAll() {
        return em.createQuery("SELECT b from Board b" +
                " join fetch b.createdBy m" +
                " where b.deleteYn = 'N'", Board.class).getResultList();
    }

    /**
     * Board Detail 불러오기
     * @param id
     * @return
     */
    public Board findById(long id) {
        return em.find(Board.class, id);
    }

    /**
     * Update
     * @param board
     */
    public void update(Board board) {
        em.merge(board);
    }

    /**
     * 삭제
     * @param id: index
     */
    public void delete(long id) {
        em.createQuery("update Board b set b.deleteYn='Y' where b.id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public void boardGoodUp(long idx) {
        em.createNamedQuery("Board.goodUp")
                .setParameter("id", idx)
                .executeUpdate();
    }
}
