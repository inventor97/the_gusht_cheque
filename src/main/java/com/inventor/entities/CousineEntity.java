package com.inventor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cousine", schema = "themeat")
public class CousineEntity {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;

//    @OneToMany(fetch =  FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
//    })
//    @JoinColumn(name = "cousine_id", referencedColumnName = "id")
//    List<FoodFamilyEntity> families;
}
