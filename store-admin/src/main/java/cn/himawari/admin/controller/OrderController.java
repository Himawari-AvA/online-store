package cn.himawari.admin.controller;

import cn.himawari.admin.service.OrderService;
import cn.himawari.param.PageParam;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @GetMapping("/list")
    public R list(PageParam pageParam){
        return orderService.list(pageParam);
    }
}
