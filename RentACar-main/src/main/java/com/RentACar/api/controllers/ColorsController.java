package com.RentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RentACar.business.abstracts.ColorService;
import com.RentACar.business.dtos.ColorListDto;
import com.RentACar.business.requests.color.CreateColorRequest;
import com.RentACar.business.requests.color.DeleteColorRequest;
import com.RentACar.business.requests.color.UpdateColorRequest;
import com.RentACar.core.exception.BusinessException;
import com.RentACar.core.utilities.results.DataResult;
import com.RentACar.core.utilities.results.Result;


@RestController
@RequestMapping("/api/colors")
public class ColorsController {

	private ColorService colorService;
	
	@Autowired
	public ColorsController(ColorService colorService) {
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<ColorListDto>> getAll(){
		return this.colorService.getAll();
	}
	
	@GetMapping("/getid")
    public DataResult<ColorListDto> getById(int colorId) throws BusinessException {
    	return this.colorService.getById(colorId);
    }
	
	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {
		return this.colorService.add(createColorRequest);	
	}
	
    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) throws BusinessException {
    	return this.colorService.delete(deleteColorRequest);
    }
    
    @PutMapping("/update")
    public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {
    	return this.colorService.update(updateColorRequest);
    }

}