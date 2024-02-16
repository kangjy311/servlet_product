package com.study.product.dto;

import com.study.product.vo.Product;

import lombok.Builder;
import lombok.Data;

@Builder
@Data

public class InsertProductReqDto {
	private String productName;
	private int productPrice;
	private String productSize;
	
	public Product toVo() {
		return Product.builder()
				.name(productName)
				.price(productPrice)
				.size(productSize)
				.build();
	}
}
