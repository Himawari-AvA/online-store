package cn.himawari.product.service;

import cn.himawari.param.ProductIdsParam;
import cn.himawari.param.ProductSaveParam;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.param.SortProductParam;
import cn.himawari.pojo.Product;
import cn.himawari.to.OrderToProduct;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProductService extends IService<Product> {
    R promo(String categoryName);

    /**
     * 查询类别商品集合
     * @return
     */
    R clist();

    /**
     * 若传入类别id，则查询该类别，否则查询全部
     * @param productIdsParam
     * @return
     */
    R byCategory(ProductIdsParam productIdsParam);

    /**
     * 根据商品id,查询商品详情信息
     * @param productID
     * @return
     */
    R detail(Integer productID);

    /**
     * 查询商品对应的图片详情集合
     * @param productID
     * @return
     */
    R pictures(Integer productID);

    /**
     * 搜索服务调用 拿到全部商品的数据集合
     * @return 集合
     */
    List<Product> allList();
    /**
     * 搜索业务
     * @return 集合
     */
    R search(ProductSearchParam productSearchParam);

    /**
     * 根据商品id查询商品id集合
     * @param productIds
     * @return
     */
    List<Product> cartList(List<Integer> productIds);

    /**
     * 修改库存增加销量
     * @param orderToProducts
     * @return
     */
    void subNumber(List<OrderToProduct> orderToProducts);

    /**
     * 根据商品id集合查询商品信息
     * @param productIds
     * @return
     */
    R ids(List<Integer> productIds);

    /**
     * 该类别对应的商品数量查询
     * @param categoryId
     * @return
     */
    Long adminCount(Integer categoryId);

    /**
     * 商品添加
     * @param productSaveParam
     * @return
     */
    R adminSave(ProductSaveParam productSaveParam);

    /**
     * 商品信息更新
     * @param product
     * @return
     */
    R adminUpdate(Product product);

    /**
     * 商品删除
     * @param productId
     * @return
     */
    R adminRemove(Integer productId);

    /**
     * 通过目录id查找销量最高的三个商品给出推荐
     * @param categoryId
     * @return
     */
    List<Product> getPreference(Integer categoryId);

    /**
     * 根据排序需求搜索商品传回
     * @param sortProductParam
     * @return
     */
    R listBySort(SortProductParam sortProductParam);
}
