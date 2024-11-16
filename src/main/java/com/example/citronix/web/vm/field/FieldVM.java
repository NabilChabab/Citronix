package com.example.citronix.web.vm.field;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldVM {

    @Positive(message = "Field area must be positive.")
    private double area;
}