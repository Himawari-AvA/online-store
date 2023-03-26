package cn.himawari.clients;

import cn.himawari.param.ProductSearchParam;
import cn.himawari.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("search-service")
public interface SearchClient {
    @PostMapping("/search/product")
    R search(@RequestBody ProductSearchParam productSearchParam);
}
