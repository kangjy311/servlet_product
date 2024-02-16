package com.study.product.vo;

import com.study.product.dto.InsertProductRespDto;
import com.study.product.dto.SearchProductRespDto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class Product {
	private int productId;
	private String name;
	private int price;
	private String size; 
	
	public InsertProductRespDto toInsertDto(int successCount) {
		return InsertProductRespDto.builder()
				.successCount(successCount)
				.productId(productId)
				.productName(name)
				.productPrice(price)
				.productSize(size)
				.build();
	}
	
	public SearchProductRespDto toSearchDto() {
		return SearchProductRespDto.builder()
				.productId(productId)
				.productName(name)
				.productPrice(price)
				.productSize(size)
				.build();
	}
}
