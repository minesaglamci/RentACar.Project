package com.RentACar.business.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDto {
	private int carId;
	private int dailyPrice;
	private String modelYear;
	private String description;
	private String brandName;
	private String colorName;
}
