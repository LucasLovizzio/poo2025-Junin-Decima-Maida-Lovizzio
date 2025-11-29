package ar.edu.unnoba.poo2025.torneos.validation;

import ar.edu.unnoba.poo2025.torneos.model.DocType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DocTypeValidator implements ConstraintValidator<ValidDocType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(getErrorMessage())
			       .addConstraintViolation();
			return false;
		}

		for (DocType docType : DocType.values()) {
			if (docType.getValue().equals(value.trim())) {
				return true;
			}
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(getErrorMessage())
		       .addConstraintViolation();
		return false;
	}

	private String getErrorMessage() {
		String allowedValues = Arrays.stream(DocType.values())
		                             .map(DocType::getValue)
		                             .collect(Collectors.joining(", "));
		return "Invalid document type. Allowed values: " + allowedValues;
	}

}
