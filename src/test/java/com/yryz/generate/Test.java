package com.yryz.generate;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Map<Long,String> map  =  new HashMap<Long,String>();
        map.put(1l,"1");
        map.put(1l,"2");

        System.out.println(map.get(1l));
    }
}
