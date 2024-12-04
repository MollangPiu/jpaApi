package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = {
   @UniqueConstraint(name="unique_memberId", columnNames = {"userId" }),
   @UniqueConstraint(name="unique_memberName", columnNames = {"userName"})
})
@NamedQuery(
        name = "Member.findByUserId",
        query= "SELECT m from Member m where m.userId = :userId"
)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idx;

    @Column(nullable = false, length = 15)
    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Column(nullable = false, length = 40)
    private String userName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String gender;

    @Embedded
    private InstDate date;
/*    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime insDateTime;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

 @PrePersist
    public void prePersist() {
        if(this.date == null) {
            this.date = new InstDate();
        }
    }
}
