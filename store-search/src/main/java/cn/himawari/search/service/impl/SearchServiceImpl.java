package cn.himawari.search.service.impl;

import cn.himawari.doc.ProductDoc;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Product;
import cn.himawari.search.service.SearchService;
import cn.himawari.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {


    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**
     * 根据关键字和分页查询数据
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {
        SearchRequest searchRequest = new SearchRequest("product");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        String search = productSearchParam.getSearch();
        if(StringUtils.isEmpty(search)){
//            searchRequest.source().query(QueryBuilders.matchAllQuery());
            sourceBuilder.query(QueryBuilders.matchAllQuery());
        }else{
//            searchRequest.source().query(QueryBuilders.matchQuery("all",search));
            sourceBuilder.query(QueryBuilders.matchQuery("all",search));
        }
            switch (productSearchParam.getSortKind()){
                case 0:break;
//                case 1:searchRequest.source().query(QueryBuilders.matchAllQuery()).sort("productSellingPrice", SortOrder.ASC);break;
                case 1:sourceBuilder.sort("product_selling_price",SortOrder.ASC);break;

//                case 2:searchRequest.source().query(QueryBuilders.matchAllQuery()).sort("productSellingPrice", SortOrder.DESC);break;
//                case 3:searchRequest.source().query(QueryBuilders.matchAllQuery()).sort("productSales", SortOrder.ASC);break;
//                case 4:searchRequest.source().query(QueryBuilders.matchAllQuery()).sort("productSales", SortOrder.DESC);break;
                case 2:sourceBuilder.sort("product_selling_price",SortOrder.DESC);break;
                case 3:sourceBuilder.sort("product_sales",SortOrder.ASC);break;
                case 4:sourceBuilder.sort("product_sales",SortOrder.DESC);break;
            }
        sourceBuilder.from((productSearchParam.getCurrentPage()-1)*productSearchParam.getPageSize());
        sourceBuilder.size(productSearchParam.getPageSize());
        searchRequest.source(sourceBuilder);
//        searchRequest.source().from((productSearchParam.getCurrentPage()-1)*productSearchParam.getPageSize());
//        searchRequest.source().size(productSearchParam.getPageSize());

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }
        log.info("------------------------------排序结果1-----------------------");
        log.info("结果：{}",searchResponse);
        log.info("------------------------------排序结果2-----------------------");
        SearchHits hits = searchResponse.getHits();
        long total = hits.getTotalHits().value;
        SearchHit[] hitsHits = hits.getHits();
        List<Product> productList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitsHit : hitsHits) {
            //获取单挑json数据
            String sourceAsString = hitsHit.getSourceAsString();
            Product product = null;
            try {
//                需要忽略没有的属性
                product = objectMapper.readValue(sourceAsString, Product.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            productList.add(product);
        }
        R ok = R.ok(null, productList, total);
        log.info("SearchServiceImpl.search业务结束，结果：{}",ok);
        return ok;
    }

    /**
     * 商品同步：插入与更新
     *
     * @param product
     * @return
     */
    @Override
    public R save(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());

        ProductDoc productDoc = new ProductDoc(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        return R.ok("数据同步成功");
    }

    /**
     * 进行es库的商品删除
     *
     * @param productId
     * @return
     */
    @Override
    public R remove(Integer productId) throws IOException {
        DeleteRequest request = new DeleteRequest("product").id(productId.toString());

        restHighLevelClient.delete(request,RequestOptions.DEFAULT);
        return R.ok("es库的商品数据删除成功！");
    }
}
