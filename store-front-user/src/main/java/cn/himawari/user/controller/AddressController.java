package cn.himawari.user.controller;

import cn.himawari.param.AddressListParam;
import cn.himawari.param.AddressRemoveParam;
import cn.himawari.pojo.Address;
import cn.himawari.user.service.AddressService;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("list")
    public R list(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，查询失败");
        }
        return addressService.list(addressListParam.getUserId());
    }

    @PostMapping("save")
    public R save( @RequestBody @Validated Address address,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，保存失败");
        }
        return addressService.save(address);
    }

    @PostMapping("remove")
    public R remove(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，删除失败");
        }
        return addressService.remove(addressRemoveParam.getId());
    }

    @PostMapping("getone")
    public Address getone(@RequestBody @Validated AddressRemoveParam addressRemoveParam, BindingResult result){
//        if(result.hasErrors()){
//            return R.fail("参数异常，删除失败");
//        }
        return addressService.getone(addressRemoveParam.getId());
    }


}
