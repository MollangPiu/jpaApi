package kr.soft.campus;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import kr.soft.campus.domain.Area;
import kr.soft.campus.domain.Category;
import kr.soft.campus.domain.Item;
import kr.soft.campus.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitDB {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitService initService;

    @PostConstruct
    private void init() {
        logger.info("init ★★★★★★★");
        initService.areaInsert();
        initService.memberInsert();
        initService.itemInsert();
    }

    @Component
    static class InitService {

        @Autowired
        private EntityManager em;


        @Transactional
        public void areaInsert() {

            Area area1 = new Area();
            area1.setAreaName("서울");
            em.persist(area1);

            Area area2 = new Area();
            area2.setAreaName("부산");
            em.persist(area2);

            Area area3 = new Area();
            area3.setAreaName("인천");
            em.persist(area3);

            Area area4 = new Area();
            area4.setAreaName("대구");
            em.persist(area4);

            Area area5 = new Area();
            area5.setAreaName("울산");
            em.persist(area5);

            Area area6 = new Area();
            area6.setAreaName("대전");
            em.persist(area6);
        }

        @Transactional
        public void memberInsert() {
            Member member1 = new Member();
            member1.setUserId("hong");
            member1.setUserName("홍길동");
            member1.setUserPw("1234");
            member1.setBirth("1950-11-01");
            member1.setGender("M");
            member1.setEmail("hong@gmail.com");
            member1.setArea(em.find(Area.class, 1));
            em.persist(member1);

            Member member2 = new Member();
            member2.setUserId("super");
            member2.setUserPw("1234");
            member2.setUserName("superMan");
            member2.setBirth("1950-01-01");
            member2.setGender("M");
            member2.setEmail("superMan@gmail.com");
            member2.setArea(em.find(Area.class, 2));
            em.persist(member2);

            Member member3 = new Member();
            member3.setUserId("james");
            member3.setUserPw("1234");
            member3.setUserName("jamesBoy");
            member3.setGender("F");
            member3.setEmail("james@gmail.com");
            member3.setBirth("1995-12-01");
            member3.setArea(em.find(Area.class, 2));
            em.persist(member3);

            Member member4 = new Member();
            member4.setUserId("dol12");
            member4.setUserPw("1234");
            member4.setUserName("dola Uni");
            member4.setGender("M");
            member4.setEmail("dol12@gmail.com");
            member4.setBirth("2001-03-13");
            member4.setArea(em.find(Area.class, 3));
            em.persist(member4);
        }


        @Transactional
        public void itemInsert() {


            Category category1 = new Category();
            category1.setName("도서");
            em.persist(category1);

            Category category2 = new Category();
            category2.setName("전자");
            em.persist(category2);

            Category category3 = new Category();
            category3.setName("생활");
            em.persist(category3);


            Item item1 = new Item();
            item1.setName("Java의 정석");
            item1.setCategory(category1);
            item1.setPrice(15000);
            em.persist(item1);

            Item item2 = new Item();
            item2.setName("노트북");
            item2.setPrice(2000000);
            item2.setCategory(category2);
            em.persist(item2);

            Item item3 = new Item();
            item3.setName("수납장");
            item3.setPrice(300000);
            item3.setCategory(category3);
            em.persist(item3);

            Item item4 = new Item();
            item4.setName("스마트폰");
            item4.setPrice(1400000);
            item4.setCategory(category1);
            em.persist(item4);

        }
    }

}
