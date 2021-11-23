package com.inventor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "food_family", schema = "themeat")
public class FoodFamilyEntity {

    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "cousine_id")
    private int cousineId;

//    @ManyToOne
//    private CousineEntity cousine;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
//    @JoinColumn(name = "family_id", referencedColumnName = "id")
//    private List<FoodsEntity> foods;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodFamilyEntity)) return false;
        FoodFamilyEntity that = (FoodFamilyEntity) o;
        return id == that.id && cousineId == that.cousineId && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cousineId);
    }
}
