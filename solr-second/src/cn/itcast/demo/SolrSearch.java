package cn.itcast.demo;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrSearch {

	@Test
	public void testSolrSearch() throws Exception {
		//1、创建一个SolrServer对象HttpSolrServer
		String baseURL = "http://localhost:8080/solr/collection1";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		//2、创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//3、在solrQuery中设置查询条件，可以参考后台
		//query.setQuery("*:*");
		query.set("q", "*:*");
		//4、执行查询，QueryResonse对象
		QueryResponse queryResponse = solrServer.query(query);
		//5、取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		//6、去查询结果的总记录数
		System.err.println("查询结果的总记录数为"+solrDocumentList.getNumFound());
		//7、遍历文档列表，取查询结果
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_catalog_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_description"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("product_name"));
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_picture"));
			System.out.println(solrDocument.get("product_catalog_name"));
		}
	}
		
	@Test
	public void searchIndexfuza() throws Exception {
		//1、创建solrServer对象的HttpSolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		//2、创建solrQuery对象
		SolrQuery query = new SolrQuery();
		//3、在solrQuery中设置值
		query.setQuery("product_name:小黄人");
		//过滤查询
		query.addFilterQuery("product_price:[0 TO 10]");
		//排序
		query.setSort("product_price",ORDER.asc);
		//设置起始查询
		query.setStart(0);
		//设置查询的最大值
		query.setRows(5);
		query.set("df", "product_keywords");
		//设置高亮
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//4、执行查询，QueryResponse对象
		QueryResponse solrQuery = solrServer.query(query);
		//5、取查询结果
		SolrDocumentList solrDocumentList = solrQuery.getResults();
		//6、去查询结果的总记录数、
		System.out.println("查询结果的总记录数："+ solrDocumentList.getNumFound());
		//取高亮结果
		Map<String, Map<String, List<String>>> highlighting = solrQuery.getHighlighting();
		//7、遍历文档列表，取查询结果
		
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			String name="";
			if(list!=null && list.size()>0){
				name = list.get(0);
			}else{
				name = (String) solrDocument.get("product_name");
			}
			System.out.println(name);
			System.out.println(solrDocument.get("product_price"));
			System.out.println(solrDocument.get("product_picture"));
			
		}
		
	}	
			
}
