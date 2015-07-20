package com.cihankaptan.android.whounfollowedme.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cnkaptan on 20/07/15.
 */
public class ListUtil {

    public static <T> List<T> union(List<T> list1, List<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new ArrayList<T>(set);
    }

    public static <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

    public static  <T> List<T>  difference(List<T> list1,List<T> list2){
        List<T> list = new ArrayList<T>(list1);
        list.removeAll(list2);
        return list;
    }

}