package com.inventor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delevery_sevice",schema = "themeat")
public class DeleveryService {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleveryService)) return false;
        DeleveryService that = (DeleveryService) o;
        return id == that.id && name.equals(that.name) && phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber);
    }

    //    @OneToOne(targetEntity = DeleveryDataEntity.class,mappedBy = "delevery_data")
//    private DeleveryDataEntity deleveryData;
}
