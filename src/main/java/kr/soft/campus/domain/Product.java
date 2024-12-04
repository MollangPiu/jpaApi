package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idx;

    @ManyToOne
    @JoinColumn(name = "itemIdx")
    private Item itemIdx;

    private int cnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_idx", insertable = false, updatable = false)
    private Member memberIdx;

    private LocalDateTime created;

    @Column(name="DELETE_YN", length = 1)
    private String deleteYn = "N";
}
