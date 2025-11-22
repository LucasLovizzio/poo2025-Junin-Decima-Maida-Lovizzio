package ar.edu.unnoba.poo2025.torneos.validation;

import ar.edu.unnoba.poo2025.torneos.model.DocType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DocTypeValidator implements ConstraintValidator<ValidDocType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.trim().isEmpty()) {
			return false;
		}

		for (DocType docType : DocType.values()) {
			if (docType.getValue().equalsIgnoreCase(value.trim())) {
				return true;
			}
		}

		return false;
	}

}
