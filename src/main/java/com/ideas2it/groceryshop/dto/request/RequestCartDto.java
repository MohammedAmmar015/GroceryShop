package com.ideas2it.groceryshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestCartDto {
    private Float totalPrice;
    private List<RequestCartDetailsDto> cartDetailsDtos;
}
