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
	//ͨ��springmvc������ע��solr������
	@Autowired
	private SolrServer solrServer;

	@Override
	public List<ProductModel> searchProduct(ProductSearch productSearch) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		//���ùؼ���
		solrQuery.setQuery(productSearch.getQueryString());
		//����Ĭ�ϼ�����
		solrQuery.set("df", "product_keywords");
		//���ù�������
		if(null != productSearch.getCatalog_name() && !"".equals(productSearch.getCatalog_name())){
			solrQuery.set("fq", "product_catalog_name:" + productSearch.getCatalog_name());
		}
		if(null != productSearch.getPrice() && !"".equals(productSearch.getPrice())){
			//0-9   50-*  �Լ۸���й���
			String[] p = productSearch.getPrice().split("-");
			solrQuery.set("fq", "product_price:[" + p[0] + " TO " + p[1] + "]");
		}
		// �۸�����
		if ("1".equals(productSearch.getSort())) {
			solrQuery.addSort("product_price", ORDER.desc);
		} else {
			solrQuery.addSort("product_price", ORDER.asc);
		}
		// ��ҳ
		solrQuery.setStart(0);
		solrQuery.setRows(16);
		// ֻ��ѯָ����
		solrQuery.set("fl", "id,product_name,product_price,product_picture");
		// ����
		// �򿪿���
		solrQuery.setHighlight(true);
		// ָ��������
		solrQuery.addHighlightField("product_name");
		// ǰ׺
		solrQuery.setHighlightSimplePre("<span style='color:red'>");
		solrQuery.setHighlightSimplePost("</span>");
		// ִ�в�ѯ
		QueryResponse response = solrServer.query(solrQuery);
		// �ĵ������
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
