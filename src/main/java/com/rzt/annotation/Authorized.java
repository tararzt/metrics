package com.rzt.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This is an annotation to be marked for on fields that should be set to null before sending to the
 * UI if the logged in user is not authenticated
 * 
 * @author deepak
 *
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Authorized {

}
