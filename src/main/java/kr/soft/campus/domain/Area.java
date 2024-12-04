package kr.soft.campus.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idx;

    private String areaName;

    private String areaCode;

    @OneToMany(mappedBy = "area")
    private List<Member> members = new ArrayList<>();
}
