package cn.himawari.user.controller;

import cn.himawari.param.*;
import cn.himawari.pojo.Preference;
import cn.himawari.pojo.User;
import cn.himawari.user.service.UserService;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userservice;
    /**
     * 检查账号是否可用的接口
     * @param userCheckParam    接受检查的账号实体，内部有参数校验注解
     * @param result    获取校验结果的实体对象
     * @return  返回封装结果R对象
     */

    @PostMapping("check")
    public R check(@RequestBody @Validated UserCheckParam userCheckParam, BindingResult result){
        boolean b = result.hasErrors();

        if(b){
            return R.fail("账号不可以为空");
        }

        return userservice.check(userCheckParam);
    }

    @PostMapping("register")
    public R register( @RequestBody @Validated User user,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，不可注册");
        }
        return userservice.register(user);
    }

    @PostMapping("login")
    public R login(@RequestBody @Validated UserLoginParam userLoginParam,BindingResult result){
        if(result.hasErrors()){
            return R.fail("参数异常，不可登录");
        }
        return userservice.login(userLoginParam);
    }


    @PostMapping("getone")
    public R getOneInfo(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("用户数据有误，查不到相应数据");
        }
        return  userservice.getOneInfo(cartListParam.getUserId());
    }

    @PostMapping("firstaddpreference")
    public R firstAddPreference(@RequestBody @Validated CartListParam cartListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("用户数据有误，查不到相应数据");
        }
        return  userservice.getOneInfo(cartListParam.getUserId());
    }

    @PostMapping("addpreference")
    public R addPreference(@RequestBody @Validated PreferenceParam preferenceParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("用户数据有误，查不到相应数据");
        }
        return  userservice.addpreference(preferenceParam);
    }

    @PostMapping("getpreference")
    public R getPreference(@RequestBody @Validated AddressListParam addressListParam, BindingResult result){
        if(result.hasErrors()){
            return R.fail("用户数据有误，查不到相应数据");
        }
        return  userservice.getpreference(addressListParam.getUserId());
    }
}
