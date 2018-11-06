package com.hnu.scw.service;

import java.util.List;
import com.hnu.scw.model.ProductModel;
import com.hnu.scw.model.ProductSearch;

public interface SearchProductService {
	
	public List<ProductModel> searchProduct(ProductSearch productSearch) throws Exception;

}
