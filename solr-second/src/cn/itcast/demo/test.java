package cn.itcast.demo;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class test {
	
	@Test
	public void testSolr() throws Exception {
		SolrServer solrServler = new HttpSolrServer("localhost:8080/solr");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "c0001");
		document.addField("title", "使用solrJ添加的文档");
		/*document.addField("content_ik", "文档的内容");
		document.addField("product_name", "商品名称");*/
		//把document对象添加到索引库中
		solrServler.add(document);
				//提交修改
		solrServler.commit();
	}

}
