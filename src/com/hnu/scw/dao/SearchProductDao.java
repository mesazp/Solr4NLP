package com.hnu.scw.dao;

import java.util.List;

import com.hnu.scw.model.ProductModel;
import com.hnu.scw.model.ProductSearch;

public interface SearchProductDao {
	public List<ProductModel> searchProduct(ProductSearch productSearch) throws Exception;
}
