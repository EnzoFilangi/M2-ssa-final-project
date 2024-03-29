package com.ssa.team3.backend.controller.annotations.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.UUID;

/**
 * This class is the implementation of the validator for the constraint IsUUID
 */
public class UuidValidator implements ConstraintValidator<IsUUID, UUID> {

    @Override
    public void initialize(IsUUID validUuid) {
    }

    @Override
    public boolean isValid(UUID uuid, ConstraintValidatorContext cxt) {
        String regex = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
        return uuid.toString().matches(regex);
    }
}
