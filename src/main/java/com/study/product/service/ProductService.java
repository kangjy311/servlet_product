package com.study.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.study.product.dao.ProductDao;
import com.study.product.dto.InsertProductReqDto;
import com.study.product.dto.InsertProductRespDto;
import com.study.product.dto.SearchProductRespDto;
import com.study.product.vo.Product;

public class ProductService {
	private static ProductService instance;
	private ProductDao productDao;
	
	private ProductService() {
		productDao = ProductDao.getInstance();
	}
	
	public static ProductService getinstance() {
		if(instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	public boolean isDuplicatedProductName(String productName) {
		
		return productDao.findProductByName(productName) != null;
	}
	
	public InsertProductRespDto addProduct(InsertProductReqDto insertProductReqDto) {
		Product product = insertProductReqDto.toVo();
		
		int successCount = productDao.saveProduct(product);
		
		return product.toInsertDto(successCount);
	}
	
	public List<SearchProductRespDto> searchProducts() {
		List<SearchProductRespDto> searchProductRespDtos = new ArrayList<>();
		
		List<Product> products =productDao.getProductList();
		
		for(Product product : products) {
			searchProductRespDtos.add(product.toSearchDto());
		}
		
		return searchProductRespDtos;
		
		
//		return productDao.getProductList().stream()
//			.map(vo -> vo.toSearchDto())
//			.collect(Collectors.toList());
		
//		return productDao.getProductList().stream()
//				.map(Product::toSearchDto)
//				.collect(Collectors.toList());
		// return 같은 코드
		
		
		
	}
	
	
}
