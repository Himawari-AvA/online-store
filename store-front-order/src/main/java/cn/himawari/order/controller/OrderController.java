package cn.himawari.order.controller;


import cn.himawari.order.service.OrderService;
import cn.himawari.param.CartListParam;
import cn.himawari.param.OrderCancelParam;
import cn.himawari.param.OrderParam;
import cn.himawari.param.PageParam;
import cn.himawari.utils.R;
import feign.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("save")
    public R save(@RequestBody @Validated OrderParam orderParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，保存失败");
        }
        return orderService.save(orderParam);
    }

    @PostMapping("list")
    public R list(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，查询失败");
        }
        return orderService.list(cartListParam.getUserId());
    }

    @PostMapping("remove/check")
    public R check(@RequestBody Integer productId){
        return orderService.check(productId);
    }

    @PostMapping("admin/list")
    public R adminList(@RequestBody PageParam pageParam){
        return orderService.adminList(pageParam);
    }

    @PostMapping("cancel")
    public R cancel(@RequestBody @Validated OrderCancelParam orderCancelParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，查询失败");
        }
        return orderService.cancel(orderCancelParam.getOrderId());
    }

    @PostMapping("pay")
    public R pay(@RequestBody @Validated OrderCancelParam orderCancelParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，查询失败");
        }
        return orderService.pay(orderCancelParam.getOrderId());
    }

}
