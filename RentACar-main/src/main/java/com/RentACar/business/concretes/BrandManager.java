package com.RentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.RentACar.business.dtos.BrandListDto;
import com.RentACar.business.requests.brand.CreateBrandRequest;
import com.RentACar.business.requests.brand.DeleteBrandRequest;
import com.RentACar.business.requests.brand.UpdateBrandRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.mapping.ModelMapperService;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.ErrorResult;
import com.RentACar.core.utilities.results.Result;
import com.RentACar.core.utilities.results.SuccessDataResult;
import com.RentACar.core.utilities.results.SuccessResult;
import com.RentACar.dataAccess.abstracts.BrandDao;
import com.RentACar.business.abstracts.BrandService;
import com.RentACar.entities.concretes.Brand;


@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {
		var result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<BrandListDto>>(response);
	}

	@Override
	public DataResult<BrandListDto> getById(int brandId) throws BusinessException {
		var result = this.brandDao.getByBrandId(brandId);
		if (result == null) {
			throw new BusinessException("Bu ID'ye sahip bir marka bulunmuyor.");
		} else {
			BrandListDto response = this.modelMapperService.forDto().map(result, BrandListDto.class);
			return new SuccessDataResult<BrandListDto>(response);
		}
	}
	
	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		if (checkBrandNames(brand)) {
			this.brandDao.save(brand);
			return new SuccessResult("Brand.Added");
		}
		return new ErrorResult("Brand.NotFound");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		var result = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
		if (checkBrandId(result)) {
			this.brandDao.delete(result);
			return new SuccessResult("Brand.Deleted");
		}
		else {
			return new ErrorResult("Brand.NotFound");
		}
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		var result = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		if (checkBrandId(result) && checkBrandNames(result)) {
			this.brandDao.save(result);
			return new SuccessResult("Brand.Updated");
		}
		else {
			return new ErrorResult("Brand.NotFound");
		}
	}
	
	
	private boolean checkBrandNames(Brand brand) throws BusinessException {
		var result = this.brandDao.getByBrandName(WordUtils.capitalize(brand.getBrandName()));
		if (result == null) {
			return true;
		} else {
			throw new BusinessException("Bu marka var.");
		}
	}
	
	private boolean checkBrandId(Brand brand) throws BusinessException {
		var result = this.brandDao.getByBrandId(brand.getBrandId());
		if (result == null) {
			throw new BusinessException("Bu ID'ye sahip bir marka bulunmuyor.");
		} else {
			return true;
		}
	}
}
