package cn.himawari.search.service.impl;

import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Product;
import cn.himawari.search.service.SearchService;
import cn.himawari.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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
        String search = productSearchParam.getSearch();
        if(StringUtils.isEmpty(search)){
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else{
            searchRequest.source().query(QueryBuilders.matchQuery("all",search));
        }

        searchRequest.source().from((productSearchParam.getCurrentPage()-1)*productSearchParam.getPageSize());
        searchRequest.source().size(productSearchParam.getPageSize());

        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("查询错误");
        }

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
}
