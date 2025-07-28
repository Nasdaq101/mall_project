package com.yunfei.item.es;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.yunfei.item.domain.po.Item;
import com.yunfei.item.domain.po.ItemDoc;
import com.yunfei.item.service.IItemService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
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

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("items");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    public void testGetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("items");
        client.indices().get(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testSearch() throws IOException {
        // from frontend
        int pageSize = 10, pageNo = 2;

        // search ( term / match / range)
        SearchRequest request = new SearchRequest("items");
        request.source().query(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery("name","milk"))
                        .filter(QueryBuilders.termQuery("brand", "wow"))
                        .filter(QueryBuilders.rangeQuery("price").gte(1).lt(3))
        );

        // sort & pagination
        request.source().from((pageNo -1)*pageSize).size(pageSize);
        request.source()
                .sort("sold", SortOrder.DESC);

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

//        parseResponseResult(response);

    }

//    @Test
//    public void testBulkDoc() throws IOException {
//        int pageNo = 1, pagesize = 500;
//        while (true) {
//            Page<Item> page = itemService.lambdaQuery()
//                    .eq(Item::getStatus, 1)
//                    .page(Page.of(pageNo, pagesize));
//            List<Item> records = page.getRecords();
//            if (records == null || records.isEmpty()) {
//                return;
//            }
//
//            BulkRequest request = new BulkRequest();
//            for (Item item : records) {
//                request.add(new IndexRequest("items")
//                        .id(item.getId().toString())
//                        .source(JSONUtil.toJsonStr(BeanUtils.copyProperties(item,itemDoc.class);),XContentType.JSON));
//            }
//            request.add(new DeleteRequest("items").id("1"));
//            client.bulk(request,RequestOptions.DEFAULT);
//              pageNo++;
//          }
//
//    }

    public static final String MAPPING_TEMPLATE = "{\n" +
            "  \"mappings\": {\n" +
            "    \"properties\": {\n" +
            "      \"id\": {\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"name\":{\n" +
            "        \"type\": \"text\",\n" +
            "        \"analyzer\": \"ik_max_word\"\n" +
            "      },\n" +
            "      \"price\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"stock\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"image\":{\n" +
            "        \"type\": \"keyword\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"category\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"brand\":{\n" +
            "        \"type\": \"keyword\"\n" +
            "      },\n" +
            "      \"sold\":{\n" +
            "        \"type\": \"integer\"\n" +
            "      },\n" +
            "      \"commentCount\":{\n" +
            "        \"type\": \"integer\",\n" +
            "        \"index\": false\n" +
            "      },\n" +
            "      \"isAD\":{\n" +
            "        \"type\": \"boolean\"\n" +
            "      },\n" +
            "      \"updateTime\":{\n" +
            "        \"type\": \"date\"\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";


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
