package com.RentACar.business.abstracts;
import java.util.List;

import com.RentACar.business.dtos.ColorListDto;
import com.RentACar.business.requests.color.CreateColorRequest;
import com.RentACar.business.requests.color.DeleteColorRequest;
import com.RentACar.business.requests.color.UpdateColorRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.Result;

public interface ColorService {
	DataResult<List<ColorListDto>> getAll();
	DataResult<ColorListDto> getById(int colorId) throws BusinessException;
	
	Result add(CreateColorRequest createColorRequest) throws BusinessException;
	Result delete (DeleteColorRequest deleteColorRequest) throws BusinessException;
	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
	
}
