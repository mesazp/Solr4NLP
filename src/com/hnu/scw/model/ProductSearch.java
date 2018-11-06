package com.hnu.scw.model;
/**
 * 搜索商品的实体类
 * @author scw
 *
 */
public class ProductSearch {
	private String queryString;  //关键字
	private String catalog_name; //类别
	private String price;  //价格
	private String sort;  //排序类型
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	
	
	
}
