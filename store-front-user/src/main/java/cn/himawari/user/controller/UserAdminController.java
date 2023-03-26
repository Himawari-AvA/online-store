package cn.himawari.user.controller;


import cn.himawari.param.PageParam;
import cn.himawari.user.service.UserService;
import cn.himawari.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserAdminController {
    private UserService userService;

    @PostMapping("admin/list")
    public R listPage(@RequestBody PageParam pageParam){
        return userService.listPage(pageParam);
    }
}
