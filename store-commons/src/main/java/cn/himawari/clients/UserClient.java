package cn.himawari.clients;

import cn.himawari.param.AddressRemoveParam;
import cn.himawari.param.CartListParam;
import cn.himawari.param.PageParam;
import cn.himawari.pojo.Address;
import cn.himawari.pojo.User;
import cn.himawari.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-service")
public interface UserClient {

    /**
     * 后台管理,展示用户信息接口
     * @param pageParam
     * @return
     */
    @PostMapping("/user/admin/list")
    R adminListPage(@RequestBody PageParam pageParam);


    @PostMapping("/user/admin/remove")
    R adminRemove(@RequestBody CartListParam cartListParam);

    @PostMapping("/user/admin/update")
    R adminUpdate(@RequestBody User user);

    @PostMapping("/user/admin/save")
    R adminSave(@RequestBody User user);

    @PostMapping("/user/address/getone")
    Address getone(@RequestBody AddressRemoveParam addressRemoveParam);
}