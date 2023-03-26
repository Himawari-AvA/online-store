package cn.himawari.user.service.impl;

import cn.himawari.constants.UserConstants;
import cn.himawari.param.PageParam;
import cn.himawari.param.UserCheckParam;
import cn.himawari.param.UserLoginParam;
import cn.himawari.pojo.User;
import cn.himawari.user.mapper.UserMapper;
import cn.himawari.user.service.UserService;
import cn.himawari.utils.MD5Util;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 检查账号是否可用
     *
     * @param userCheckParam 校验完毕的账号参数
     * @return 结果001、004
     */
    @Override
    public R check(UserCheckParam userCheckParam) {

        //参数封装
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userCheckParam.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        //查询结果处理
        if(total == 0){
            log.info("UserServiceImpl业务结束，结果：{}","账号可以使用！");
            return R.ok("账号不存在，可以注册");
        }
        log.info("UserServiceImpl业务结束，结果：{}","账号不可使用！");
        return R.fail("账号已经存在，不可注册");
    }

    /**
     * 注册业务
     *
     *  1.检查账号是否存在
     *  2.加密密码
     *  3.插入数据库
     *  4.返回结果封装
     *
     * @param user 已经校验过的
     * @return 结果 001、004
     */
    @Override
    public R register(User user) {
//        1.
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        if (total > 0) {
            log.info("UserServiceImpl.register业务结束，结果：{}","账号存在，注册失败！");
            return R.fail("账号已经存在，不可注册");
        }

//        2.MD5加密
        String finalPwd = MD5Util.encode(user.getPassword()+ UserConstants.USER_SALT);
                user.setPassword(finalPwd);
//        3.
        int rows = userMapper.insert(user);
//        4.
        if(rows == 0){
            log.info("UserServiceImpl.register业务结束，结果：{}","数据插入失败！");
            return R.fail("注册失败，请重试");
        }
        log.info("UserServiceImpl.register业务结束，结果：{}","注册成功！");
        return R.ok("注册成功！");
    }

    /**
     * 登陆业务
     *
     * @param userLoginParam 已经校验过的账号密码，未加密
     * @return
     */
    @Override
    public R login(UserLoginParam userLoginParam) {
        String finalPwd = MD5Util.encode(userLoginParam.getPassword()+ UserConstants.USER_SALT);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userLoginParam.getUserName());
        queryWrapper.eq("password",finalPwd);
        //数据库查询
        User user = userMapper.selectOne(queryWrapper);
        if(user == null){
            log.info("UserServiceImpl.login业务结束，结果：{}","账号或密码错误");
            return R.fail("账号或密码错误！");
        }
        log.info("UserServiceImpl.login业务结束，结果：{}","登陆成功");
        user.setPassword(null);//给前端的不返回password属性
        return R.ok("登陆成功",user);
    }

    /**
     * 后台管理调用查询全部用户信息
     *
     * @param pageParam
     * @return
     */
    @Override
    public R listPage(PageParam pageParam) {

        IPage<User> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        page = userMapper.selectPage(page,null);
        List<User> records = page.getRecords();
        long total = page.getTotal();
        return R.ok("用户管理查询成功",records,total);
    }
}
