package cn.himawari.clients;

import cn.himawari.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("history-service")
public interface HistoryClient {
    @PostMapping("history/remove/product")
    R remove(@RequestBody Integer productId);
}
