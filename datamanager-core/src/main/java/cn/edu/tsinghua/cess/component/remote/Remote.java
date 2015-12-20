package cn.edu.tsinghua.cess.component.remote;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by kurt on 2014/9/6.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Remote {

    public String value();
    
    public RequestMethod method() default RequestMethod.POST; 
    
    public String[] paramProperties() default {};

}
