package com.hnu.scw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hnu.scw.dao.impl.SearchProductDaoImpl;
import com.hnu.scw.model.ProductModel;
import com.hnu.scw.model.ProductSearch;
import com.hnu.scw.service.SearchProductService;

@Service
public class SearchProductImpl implements SearchProductService{
	@Autowired
	private SearchProductDaoImpl searchProductDaoImpl;
	
	@Override
	public List<ProductModel> searchProduct(ProductSearch productSearch) throws Exception {
		return searchProductDaoImpl.searchProduct(productSearch);
	}

}
