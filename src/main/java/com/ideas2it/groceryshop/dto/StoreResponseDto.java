package com.ideas2it.groceryshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 *     Store Location Response DTO, to hold response details
 * </p>
 * @author Mohammed Ammar
 * @since 03-11-2022
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreResponseDto {
    private Integer id;

    private Integer pinCode;

    private String area;

    private Date createdAt;

    private Date modifiedAt;

    private Integer createdBy;

    private Integer modifiedBy;

    private Boolean isActive;
}
