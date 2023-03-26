package cn.himawari.product.service.impl;

import cn.himawari.clients.CategoryClient;
import cn.himawari.clients.SearchClient;
import cn.himawari.param.ProductIdsParam;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.pojo.Category;
import cn.himawari.pojo.Picture;
import cn.himawari.pojo.Product;
import cn.himawari.product.mapper.PictureMapper;
import cn.himawari.product.mapper.ProductMapper;
import cn.himawari.product.service.ProductService;
import cn.himawari.to.OrderToProduct;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl extends ServiceImpl<ProductMapper,Product> implements ProductService {


    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private SearchClient searchClient;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private PictureMapper pictureMapper;

    @Cacheable(value = "list.product",key = "#categoryName",cacheManager = "cacheManagerDay")
    @Override
    public R promo(String categoryName) {
        log.info("调用categoryClient之前");
        R r = categoryClient.byName(categoryName);
        log.info("调用categoryClient之后");
        if(r.getCode().equals(R.FAIL_CODE)){
            log.info("ProductServiceImpl.promo业务结束，结果：{}","类别查询失败！");
            return r;
        }

        LinkedHashMap<String,Object> map = (LinkedHashMap<String, Object>) r.getData();
        Integer categoryId = (Integer) map.get("category_id");

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        queryWrapper.orderByDesc("product_sales");

        IPage<Product> page  = new Page<>(1,7);
         page = productMapper.selectPage(page, queryWrapper);
        List<Product> productList = page.getRecords();
        log.info("ProductServiceImpl.promo业务结束，结果是：{}",productList);
        return R.ok("数据查询成功",productList);
    }

    /**
     * 查询类别商品集合
     *
     * @return
     */
    @Override
    public R clist() {
        R r = categoryClient.list();
        log.info("ProductServiceImpl.clist业务结束，结果是：{}",r);
        return r;
    }

    /**
     * 若传入类别id，则查询该类别，否则查询全部
     *
     * @param productIdsParam
     * @return
     */
    @Cacheable(value = "list.product",key = "#productIdsParam.categoryID+'-'+#productIdsParam.currentPage+'-'+#productIdsParam.pageSize")
    @Override
    public R byCategory(ProductIdsParam productIdsParam) {

        //1.拆分请求参数
        List<Integer> categoryID = productIdsParam.getCategoryID();

        //2.请求条件封装
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        if (!categoryID.isEmpty()){
            queryWrapper.in("category_id",categoryID);

        }

        IPage<Product> page = new Page<>(productIdsParam.getCurrentPage(),productIdsParam.getPageSize());

        //3.数据查询
        page = productMapper.selectPage(page, queryWrapper);
        //4.结果封装

        R ok = R.ok("查询成功", page.getRecords(), page.getTotal());
        log.info("ProductServiceImpl.byCategory业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 根据商品id,查询商品详情信息
     *
     * @param productID
     * @return
     */
    @Cacheable(value = "product",key = "#productID")
    @Override
    public R detail(Integer productID) {
        Product product = productMapper.selectById(productID);
        R ok = R.ok(product);
        log.info("ProductServiceImpl.byCategory业务结束，结果:{}",ok);
        return ok;
    }

    /**
     * 查询商品对应的图片详情集合
     *
     * @param productID
     * @return
     */
    @Cacheable(value = "picture",key = "#productID")
    @Override
    public R pictures(Integer productID) {
        //参数封装
        QueryWrapper<Picture> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productID);
        //数据库查询
        List<Picture> pictureList = pictureMapper.selectList(queryWrapper);
        //结果封装
        R ok = R.ok(pictureList);

        log.info("ProductServiceImpl.pictures业务结束，结果:{}",ok);

        return ok;
    }

    /**
     * 搜索服务调用 拿到全部商品的数据集合
     *
     * @return 集合
     */
    @Cacheable(value = "list.category",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public List<Product> allList() {
        List<Product> productList = productMapper.selectList(null);
        log.info("ProductServiceImpl.allList业务结束，结果:{}",productList.size());
        return productList;
    }

    /**
     * 搜索业务
     *
     * @param productSearchParam
     * @return 集合
     */
    @Override
    public R search(ProductSearchParam productSearchParam) {

        R r= searchClient.search(productSearchParam);
        log.info("ProductServiceImpl.search业务结束，结果:{}",r);
        return r;
    }

    /**
     * 根据商品id查询商品id集合
     *
     * @param productIds
     * @return
     */
    @Override
    public List<Product> cartList(List<Integer> productIds) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id",productIds);

        List<Product> productList = productMapper.selectList(queryWrapper);
        log.info("ProductServiceImpl.cartList业务结束，结果：{}",productList);
        return productList;
    }

    /**
     * 修改库存增加销量
     *
     * @param orderToProducts
     * @return
     */
    @Override
    public void subNumber(List<OrderToProduct> orderToProducts) {
        //将集合转换成map，
        Map<Integer, OrderToProduct> map = orderToProducts.stream().collect(Collectors.toMap(OrderToProduct::getProductId, v -> v));
        //获取商品id集合
        Set<Integer> productIds = map.keySet();
        //查询集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);
        for(Product product : productList){
            Integer num = map.get(product.getProductId()).getNum();
            product.setProductNum(product.getProductNum() - num);
            product.setProductSales(product.getProductSales() + num);
        }

        this.updateBatchById(productList);
        log.info("ProductServiceImpl.subNumber业务结束，结果：库存和销售量数据更新完毕");
    }

    /**
     * 根据商品id集合查询商品信息
     *
     * @param productIds
     * @return
     */
    @Cacheable(value = "list.product", key = "#productIds")
    @Override
    public R ids(List<Integer> productIds) {

        QueryWrapper<Product> queryWrapper= new  QueryWrapper<>();
        queryWrapper.in("product_id",productIds);
        List<Product> productList = productMapper.selectList(queryWrapper);
        R ok = R.ok("类别信息查询成功！", productList);
        log.info("ProductServiceImpl.ids业务结束，结果：{}",ok);
        return ok;
    }

    /**
     * 该类别对应的商品数量查询
     *
     * @param categoryId
     * @return
     */
    @Override
    public Long adminCount(Integer categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id",categoryId);
        Long count = baseMapper.selectCount(queryWrapper);
        log.info("ProductServiceImpl.adminCount业务结束，结果：{}",count);
        return count;
    }

}
