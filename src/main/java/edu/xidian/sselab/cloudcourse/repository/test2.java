package edu.xidian.sselab.cloudcourse.repository;

import java.util.*;

public class test2 {

        public static void main(String[] args) {
         HashMap<String,String>map=new HashMap<String,String>();
         map.put("liu","xudong");
         map.put("a","bubu");
         Object[] key=map.keySet().toArray();
            Arrays.sort(key);
            for (int i = 0; i < key.length; i++) {
                System.out.println(map.get(key[i]));
            }

    }
    }


