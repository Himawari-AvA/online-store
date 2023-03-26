package cn.himawari.clients;

import cn.himawari.param.ProductCollectParam;
import cn.himawari.param.ProductIdParam;
import cn.himawari.pojo.Product;
import cn.himawari.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("/product/list")
    List<Product> allList();

    @PostMapping("/product/cart/detail")
    Product productDetail(@RequestBody ProductIdParam productIdParam);

    @PostMapping("/product/cart/list")
    List<Product> cartList(@RequestBody ProductCollectParam productCollectParam);
}
