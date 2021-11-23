package com.inventor.entities.entityMaps;


import com.inventor.entities.FoodsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class foodFamilyTable {
    private String name;
    private List<FoodsEntity> foods;
}
