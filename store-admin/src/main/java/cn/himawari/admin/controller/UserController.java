package cn.himawari.admin.controller;

import cn.himawari.admin.service.UserService;
import cn.himawari.param.PageParam;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController{

    @Autowired
    private UserService userService;

    @GetMapping("list")
    public R userList(PageParam pageParam){
        return userService.userList(pageParam);
    }

}