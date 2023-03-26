package cn.himawari.user.controller;


import cn.himawari.param.CartListParam;
import cn.himawari.param.PageParam;
import cn.himawari.pojo.User;
import cn.himawari.user.service.UserService;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserAdminController {
    @Autowired
    private UserService userService;

    @PostMapping("admin/list")
    public R listPage(@RequestBody PageParam pageParam){
        return userService.listPage(pageParam);
    }

    @PostMapping("admin/remove")
    public R remove(@RequestBody CartListParam cartListParam){

        return userService.remove(cartListParam.getUserId());
    }

    @PostMapping("admin/update")
    public R update(@RequestBody User user){
        return userService.update(user);
    }

    @PostMapping("admin/save")
    public R save(@RequestBody User user){
        return userService.save(user);
    }
}
