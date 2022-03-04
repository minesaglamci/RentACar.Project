package com.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.RentACar.business.abstracts.CarService;
import com.RentACar.business.dtos.CarListDto;
import com.RentACar.business.requests.car.CreateCarRequest;
import com.RentACar.business.requests.car.DeleteCarRequest;
import com.RentACar.business.requests.car.UpdateCarRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.mapping.ModelMapperService;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.ErrorResult;
import com.RentACar.core.utilities.results.Result;
import com.RentACar.core.utilities.results.SuccessDataResult;
import com.RentACar.core.utilities.results.SuccessResult;
import com.RentACar.dataAccess.abstracts.CarDao;
import com.RentACar.entities.concretes.Car;


@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		// super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {
		var result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<CarListDto> getById(int carId) throws BusinessException {
		var result = this.carDao.getByCarId(carId);
		if (result == null) {
			throw new BusinessException("Bu ID'ye sahip bir araba bulunmuyor.");
		} else {
			CarListDto response = this.modelMapperService.forDto().map(result, CarListDto.class);
			return new SuccessDataResult<CarListDto>(response);
		}

	}
	
	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car.Added");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest, Car.class);
		if (checkCarId(car)) {
			this.carDao.delete(car);
			return new SuccessResult("Car.Deleted");
		}
		return new ErrorResult("Car.NotFound");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		if (checkCarId(car)) {
			this.carDao.save(car);
			return new SuccessResult("Car.Updated");
		}
		return new ErrorResult("Car.NotFound");
	}
	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1, pageSize);
		List<Car> result=this.carDao.findAll(pageable).getContent();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted() {
		Sort sort=Sort.by(Sort.Direction.ASC,"dailyPrice");
		List<Car> result=this.carDao.findAll(sort);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<List<CarListDto>> getByDailyPriceLessThan(int maxDailyPrice) {
		List<Car> result=this.carDao.findByDailyPriceLessThanEqual(maxDailyPrice);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response);
	}

	private boolean checkCarId(Car car) throws BusinessException {
		var result = this.carDao.getByCarId(car.getCarId());
		if (result == null) {
			throw new BusinessException("Bu ID'ye sahip bir araba bulunmuyor.");
		} else {
			return true;
		}
	}
}
