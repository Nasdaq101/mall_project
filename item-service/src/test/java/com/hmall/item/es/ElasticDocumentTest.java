package com.hmall.item.es;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.hmall.item.domain.po.Item;
import com.hmall.item.domain.po.ItemDoc;
import com.hmall.item.service.IItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(properties = "spring.profiles.active=local")
public class ElasticDocumentTest {

    private RestHighLevelClient client;
    @Autowired
    private IItemService itemService;

//    @Test
//    void testConnection() {
//        System.out.println("client= " + client);
//    }

    @BeforeEach
    void setUp() {
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }

    @Test
    void testAddDocument() throws IOException {
        // 1.get item by id
        Item item = itemService.getById(100002644680L);
        // 2.copy properties
        ItemDoc itemDoc = BeanUtil.copyProperties(item, ItemDoc.class);
        // 3.itemDTO to json
        String doc = JSONUtil.toJsonStr(itemDoc);

        // 1.prepare request object
        IndexRequest request = new IndexRequest("items").id(itemDoc.getId());
        // 2.prepare json doc
        request.source(doc, XContentType.JSON);
        // 3.send request
        client.index(request, RequestOptions.DEFAULT);
    }
}
