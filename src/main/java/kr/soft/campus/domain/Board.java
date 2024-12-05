package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@NamedQuery(
        name = "Board.goodUp",
        query = "update Board b SET b.good = b.good+1 WHERE b.idx = :id"
)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(insertable = false, updatable = false)
    private long idx;                       //게시판 idx

    private String title;                   // 제목

    private String content;                 // 내용

    @Column(name="delete_yn", length = 1)
    private String deleteYn = "N";          //삭제 여부

    @Column
    private int good;                       //좋아요

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime created;          //생성 날짜

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modified;         //수정 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="createBy")
    private Member createdBy;               //생성 자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="modifedBy")
    private Member modifiedBy;              //수정 자

    @PrePersist
    public void perPersist() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }
}
