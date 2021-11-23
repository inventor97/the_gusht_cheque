package com.inventor.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.formula.eval.UnaryMinusEval;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "delevery_data", schema = "themeat")
public class DeleveryDataEntity {
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "count")
    private double count;
    @Basic
    @Column(name = "date_created")
    private Date dateCreated;
    @Basic
    @Column(name = "client_id")
    private int clientId;
    @Basic
    @Column(name = "food_id")
    private int foodId;
    @Basic
    @Column(name = "delivery_id")
    private int deliveryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeleveryDataEntity)) return false;
        DeleveryDataEntity that = (DeleveryDataEntity) o;
        return id == that.id && Double.compare(that.count, count) == 0 && clientId == that.clientId && foodId == that.foodId && deliveryId == that.deliveryId && dateCreated.equals(that.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count, dateCreated, clientId, foodId, deliveryId);
    }

    //    @OneToOne(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
//    })
//    @JoinColumn(name = "client_id", referencedColumnName = "id")
//    private ClientEntity client;
//
//    @OneToOne(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
//    })
//    @JoinColumn(name = "delivery_id", referencedColumnName = "id")
//    private DeleveryService delevery;
//
//    @OneToMany(fetch = FetchType.LAZY, cascade = {
//            CascadeType.PERSIST,CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH
//    })
//    @JoinTable(name = "foods", joinColumns = {@JoinColumn(name = "id", referencedColumnName = "food_id")})
//    private List<FoodsEntity> orderedFoods;

}
