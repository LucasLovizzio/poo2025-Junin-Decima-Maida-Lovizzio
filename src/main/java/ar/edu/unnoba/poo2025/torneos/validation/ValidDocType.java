package ar.edu.unnoba.poo2025.torneos.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocTypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDocType {

	String message() default "Invalid document type. Allowed values: DNI, PASSPORT";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
