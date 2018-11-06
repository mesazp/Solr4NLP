package com.hnu.scw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * ������Ʒ��������ͨ��solr��
 * @author scw
 *2018-02-27
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnu.scw.model.ProductModel;
import com.hnu.scw.model.ProductSearch;
import com.hnu.scw.service.impl.SearchProductImpl;
@Controller
public class SearchProductController {
	
	@Autowired
	private SearchProductImpl searchProductImpl ;
	
	/**
	 * ���������Ĵ��������ؼ��֣��۸���𣬻�������ʽ
	 * @param productSearch
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/list")
	public String searchProduct(ProductSearch productSearch , Model model) throws Exception{
		//��ȡ�����������н��
		List<ProductModel> searchProducts = searchProductImpl.searchProduct(productSearch);
		//���û�������
		model.addAttribute("productModels", searchProducts);
		model.addAttribute("queryString", productSearch.getQueryString());
		model.addAttribute("catalog_name", productSearch.getCatalog_name());
		model.addAttribute("price", productSearch.getPrice());
		model.addAttribute("sort", productSearch.getSort());
		return "product_list";
	}
	
	
}
