package com.ian.novelia.global.annotaion;

import com.ian.novelia.global.validator.NotEmptyMultipartFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
public @interface NotEmptyMultipartFile {

    String message() default "파일을 등록해주세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
