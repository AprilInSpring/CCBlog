package com.zxnk.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BeanCopyUtils
 * @Description bean对象复制工具类,使用spring的beanUtils框架进行对象复制
 * @Author cc
 * @Date 2023/4/25 13:13
 * @Version 1.0
 */
public class BeanCopyUtils {

    /**
     * @param source 原对象
     * @param clazz 复制目标对象的类属性
     * @return: V   目标对象的泛型
     * @decription 通过spring的工具类进行对象复制
     * @date 2023/4/25 13:18
    */
    public static <V> V copyBean(Object source,Class<V> clazz){
        V resutl = null;
        try {
            resutl = clazz.newInstance();
            //复制
            BeanUtils.copyProperties(source,resutl);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return resutl;
    }

    /**
     * @param sources 原目标集合
     * @param clazz 目标集合类型
     * @return: java.util.List<V>
     * @decription 通过spring工具类与工作流对集合的全部对象进行属性复制
     * @date 2023/4/25 13:23
    */
    public static <O,V> List<V> copyBeans(List<O> sources,Class<V> clazz){
        List<V> list = sources.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
        return list;
    }
}