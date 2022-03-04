package com.RentACar.business.abstracts;
import java.util.List;

import com.RentACar.business.dtos.CarListDto;
import com.RentACar.business.requests.car.CreateCarRequest;
import com.RentACar.business.requests.car.DeleteCarRequest;
import com.RentACar.business.requests.car.UpdateCarRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.Result;



public interface CarService {

	DataResult<List<CarListDto>> getAll();
	DataResult<CarListDto> getById(int carId) throws BusinessException;
	
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	Result delete (DeleteCarRequest deleteCarRequest) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	
	DataResult<List<CarListDto>> getByDailyPriceLessThan(int maxDailyPrice);

	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarListDto>> getAllSorted();

}
