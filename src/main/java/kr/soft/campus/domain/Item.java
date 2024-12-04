package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@NamedQuery(
        name = "Item.findByName",
        query = "SELECT i FROM Item i where i.name like concat('%',:item, '%')"
)
@NamedQuery(
        name = "Item.goodUp",
        query = "update Item i SET i.good = i.good+1 WHERE i.idx = :id"
)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idx;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    private int good;
}
