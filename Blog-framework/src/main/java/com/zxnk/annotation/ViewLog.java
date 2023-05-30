package com.zxnk.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//记录用户的访问信息
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ViewLog {
}
