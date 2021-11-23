package com.inventor.entities.entityMaps;


import com.inventor.entities.FoodsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class orderTableMap {
    private int no;
    private String foodName;
    private double price;
    private int count;
    private double sumPrice;
    private FoodsEntity foodClass;

    public orderTableMap setValues(orderFootMap obj, int order) {
        orderTableMap tableMap = new orderTableMap();
        tableMap.setNo(order);
        tableMap.setFoodName(obj.getFood().getName());
        tableMap.setPrice(obj.getFood().getPrice());
        tableMap.setCount(obj.getCount());
        tableMap.setSumPrice(obj.getCount()*obj.getFood().getPrice());
        tableMap.setFoodClass(obj.getFood());
        return tableMap;
    }

    @Override
    public String toString() {
        return foodName + "--" + count +"*" + price+"\n";
    }
}
