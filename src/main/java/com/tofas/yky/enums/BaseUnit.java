package com.tofas.yky.enums;
/* T40127 @ 25.10.2015. */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum BaseUnit {

    QTY("unitTypeQty", Arrays.asList("11", "25", "22", "01")),
    KG("unitTypeKg", Arrays.asList("18", "09")),
    METER("unitTypeMeter", Arrays.asList("26", "21")),
    NA("Na", Collections.singletonList(""));

    private List<String> typeArr;
    private String label;

    BaseUnit(String label, List<String> typeArr) {
        this.label = label;
        this.typeArr = typeArr;
    }

    public String getLabel() {
        return label;
    }

    private static List<BaseUnit> all = Arrays.asList(QTY, KG, METER);

    public static BaseUnit getUnit(String type) {
        for (BaseUnit baseUnit : all) {
            for (String s : baseUnit.typeArr) {
                if(type.equals(s)) {
                    return baseUnit;
                }
            }
        }

        return NA;
    }


}
