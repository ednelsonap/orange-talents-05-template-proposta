package br.com.zup.academy.ednelson.proposta.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {IsBase64Validator.class})
@Target(FIELD)
@Retention(RUNTIME)
public @interface IsBase64 {

	String message() default "A string não é do tipo Base64";
	 
    Class<?>[] groups() default {};
 
    Class<? extends Payload>[] payload() default {};
 
    String value() default "";
    
}
