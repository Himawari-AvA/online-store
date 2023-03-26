package cn.himawari.search.controller;


import cn.himawari.param.ProductSearchParam;

import cn.himawari.search.service.SearchService;
import cn.himawari.utils.R;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("product")
    public R searchProduct(@RequestBody ProductSearchParam productSearchParam){
        return searchService.search(productSearchParam);
    }
}
