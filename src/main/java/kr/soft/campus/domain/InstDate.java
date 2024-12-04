package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class InstDate {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    private LocalDateTime insDateTime;

    private LocalDateTime uptDateTime;
    private char delteYn;

    public InstDate() {
        this.insDateTime = LocalDateTime.now();
        this.uptDateTime = this.insDateTime;
        this.delteYn = 'N';
    }

}
