package com.RentACar.business.requests.car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	private int dailyPrice;
	private String modelYear;
	private String description;
	private int brandId;
	private int colorId;

}
