package com.ideas2it.groceryshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ResponseDto {
    private String errorMessage;
    private String successMessage;
}
