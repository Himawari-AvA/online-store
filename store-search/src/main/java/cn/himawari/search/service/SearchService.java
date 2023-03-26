package cn.himawari.search.service;

import cn.himawari.param.ProductSearchParam;
import cn.himawari.utils.R;

public interface SearchService {

    /**
     * 根据关键字和分页查询数据
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);
}
