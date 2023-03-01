package com.chatreejs.smarthomeapi.bean.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class FoodRequest {

    @NotBlank(message = "Invalid Name: name is empty")
    @NotNull(message = "Invalid Name: name is null")
    private String name;

    @NotNull(message = "Invalid Quantity: quantity is null")
    @Min(0)
    private Double quantity;

    @NotBlank(message = "Invalid Unit: unit is empty")
    @NotNull(message = "Invalid Unit: unit is null")
    private String unit;

    @NotNull(message = "Invalid Buy Date: buyDate is null")
    private Date buyDate;

    @NotNull(message = "Invalid Expire Date: expireDate is null")
    private Date expireDate;
}
