package com.hnu.scw.dao.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hnu.scw.dao.SearchProductDao;
import com.hnu.scw.model.ProductModel;
import com.hnu.scw.model.ProductSearch;
@Repository
public class SearchProductDaoImpl implements SearchProductDao {
	//通过springmvc来进行注入solr服务器
	@Autowired
	private SolrServer solrServer;

	@Override
	public List<ProductModel> searchProduct(ProductSearch productSearch) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		//设置关键字
		solrQuery.setQuery(productSearch.getQueryString());
		//设置默认检索域
		solrQuery.set("df", "product_keywords");
		//设置过滤条件
		if(null != productSearch.getCatalog_name() && !"".equals(productSearch.getCatalog_name())){
			solrQuery.set("fq", "product_catalog_name:" + productSearch.getCatalog_name());
		}
		if(null != productSearch.getPrice() && !"".equals(productSearch.getPrice())){
			//0-9   50-*  对价格进行过滤
			String[] p = productSearch.getPrice().split("-");
			solrQuery.set("fq", "product_price:[" + p[0] + " TO " + p[1] + "]");
		}
		// 价格排序
		if ("1".equals(productSearch.getSort())) {
			solrQuery.addSort("product_price", ORDER.desc);
		} else {
			solrQuery.addSort("product_price", ORDER.asc);
		}
		// 分页
		solrQuery.setStart(0);
		solrQuery.setRows(16);
		// 只查询指定域
		solrQuery.set("fl", "id,product_name,product_price,product_picture");
		// 高亮
		// 打开开关
		solrQuery.setHighlight(true);
		// 指定高亮域
		solrQuery.addHighlightField("product_name");
		// 前缀
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		// 执行查询
		QueryResponse response = solrServer.query(solrQuery);
		// 文档结果集
		SolrDocumentList docs = response.getResults();

		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		for (SolrDocument doc : docs) {
			ProductModel productModel = new ProductModel();
			productModel.setPid((String) doc.get("id"));
			productModel.setPrice((Float) doc.get("product_price"));
			productModel.setPicture((String) doc.get("product_picture"));
			Map<String, List<String>> map = highlighting.get((String) doc.get("id"));
			List<String> list = map.get("product_name");
			
			productModel.setName(list.get(0));
			productModels.add(productModel);
		}
		return productModels;
	}

}
