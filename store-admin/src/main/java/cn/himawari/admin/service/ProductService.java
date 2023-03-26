package cn.himawari.admin.service;

import cn.himawari.param.ProductSearchParam;
import cn.himawari.utils.R;

public interface ProductService {
    /**
     * 搜索查询和全部商品查询
     * @param productSearchParam
     * @return
     */
    R search(ProductSearchParam productSearchParam);
}
