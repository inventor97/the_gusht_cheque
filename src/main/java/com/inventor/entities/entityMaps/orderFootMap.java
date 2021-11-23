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
public class orderFootMap {
    private int count;
    private FoodsEntity food;

}
