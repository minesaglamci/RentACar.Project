package com.RentACar.business.abstracts;
import java.util.List;

import com.RentACar.business.dtos.BrandListDto;
import com.RentACar.business.requests.brand.CreateBrandRequest;
import com.RentACar.business.requests.brand.DeleteBrandRequest;
import com.RentACar.business.requests.brand.UpdateBrandRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.Result;

public interface BrandService {
	DataResult<List<BrandListDto>> getAll();
	DataResult<BrandListDto> getById(int brandId) throws BusinessException;
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	Result delete (DeleteBrandRequest deleteBrandRequest) throws BusinessException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
}
