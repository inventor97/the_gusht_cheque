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
@Table(name = "client", schema = "themeat")
public class ClientEntity {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;
    @Basic
    @Column(name = "address")
    private String address;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientEntity)) return false;
        ClientEntity that = (ClientEntity) o;
        return id == that.id && name.equals(that.name) && phoneNumber.equals(that.phoneNumber) && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phoneNumber, address);
    }

    //    @OneToOne(targetEntity = DeleveryDataEntity.class,mappedBy = "delevery_data")
//    private DeleveryDataEntity deleveryData;
}
