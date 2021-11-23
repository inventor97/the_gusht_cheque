package com.inventor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "foods", schema = "themeat")
public class FoodsEntity {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "price")
    private double price;
    @Basic
    @Column(name = "family_id")
    private int familyId;
    @Basic
    @Column(name= "img")
    private byte[] img;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodsEntity)) return false;
        FoodsEntity that = (FoodsEntity) o;
        return id == that.id && Double.compare(that.price, price) == 0 && familyId == that.familyId && name.equals(that.name) && Arrays.equals(img, that.img);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, price, familyId);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }

    //    @ManyToOne
//    private FoodFamilyEntity family;
}
