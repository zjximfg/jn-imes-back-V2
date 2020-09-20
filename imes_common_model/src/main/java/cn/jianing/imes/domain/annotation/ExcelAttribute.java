package cn.jianing.imes.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAttribute {

    // 列名
    String name() default "";

    int exportCellNum() default -1;

    int importCellNum() default -1;

    String format() default "";

}
