package cn.jianing.imes.common.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetUtils {
    public static <T> Set<T> getDiffSet(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);
        set1.removeAll(set2);
        return set1;
    }
}
