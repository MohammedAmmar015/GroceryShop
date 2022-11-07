package com.ideas2it.groceryshop.dto;

import com.ideas2it.groceryshop.model.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StoreLocationResponse {
    private Integer id;

    private Integer pinCode;

    private String area;

    private Date createdAt;

    private Date modifiedAt;

    private Integer createdBy;

    private Integer modifiedBy;

    private Boolean isActive;
}
