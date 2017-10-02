package com.xiaoleilu.loServer.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)/*保留的时间长短*/
@Inherited/*只用于class，可被子类继承*/
public @interface DELETE {
    String value() default "DELETE";
}
