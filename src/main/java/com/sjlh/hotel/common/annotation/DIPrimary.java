/**
 * 
 */
package com.sjlh.hotel.common.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *
 */
@Target({TYPE})
 @Retention(RUNTIME)
public @interface DIPrimary {
}