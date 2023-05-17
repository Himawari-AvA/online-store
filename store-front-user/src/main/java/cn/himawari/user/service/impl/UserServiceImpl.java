package cn.himawari.user.service.impl;

import cn.himawari.clients.ProductClient;
import cn.himawari.constants.UserConstants;
import cn.himawari.param.*;
import cn.himawari.pojo.Address;
import cn.himawari.pojo.Preference;
import cn.himawari.pojo.Product;
import cn.himawari.pojo.User;
import cn.himawari.user.mapper.PreferenceMapper;
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

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PreferenceMapper preferenceMapper;

    @Autowired
    private ProductClient productClient;

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

    /**
     * 删除用户
     *
     * @param userId
     * @return
     */
    @Override
    public R remove(Integer userId) {
        int i = userMapper.deleteById(userId);

        log.info("UserServiceImpl.remove业务结束，结果：{}",i);
        return R.ok("用户数据删除成功");
    }

    /**
     * 修改用户信息
     *与旧密码相同 不改
     * 与旧密码不同 加密后更新
     * 修改信息
     * @param user
     * @return
     */
    @Override
    public R update(User user) {

        log.info("UserServiceImpl.update业务开始，结果：{}",user);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getUserId());
        queryWrapper.eq("password",user.getPassword());
        Long aLong = userMapper.selectCount(queryWrapper);
        if (aLong == 0) {
            user.setPassword(MD5Util.encode(user.getPassword()+UserConstants.USER_SALT));
        }
        int i = userMapper.updateById(user);
        log.info("UserServiceImpl.update业务结束，结果：{}",i);
        return R.ok("用户信息修改成功");
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @Override
    public R save(User user) {
        //        1.
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",user.getUserName());
        //数据库查询
        Long total = userMapper.selectCount(queryWrapper);
        if (total > 0) {
            log.info("UserServiceImpl.register业务结束，结果：{}","账号存在，注册失败！");
            return R.fail("账号已经存在，不可添加");
        }

//        2.MD5加密
        String finalPwd = MD5Util.encode(user.getPassword()+ UserConstants.USER_SALT);
        user.setPassword(finalPwd);
//        3.
        int rows = userMapper.insert(user);
//        4.
        if(rows == 0){
            log.info("UserServiceImpl.save业务结束，结果：{}","数据添加失败！");
            return R.fail("添加失败，请重试");
        }
        log.info("UserServiceImpl.save业务结束，结果：{}","添加成功！");
        return R.ok("添加成功！");
    }

    /**
     * 获取一个用户的相关信息
     *
     * @param userId
     * @return
     */
    @Override
    public R getOneInfo(Integer userId) {
        User user = userMapper.selectById(userId);
        return R.ok("查询成功",user);
    }

    /**
     * 添加一个偏好
     *
     * @param preferenceParam
     * @return
     */
    @Override
    public R addpreference(PreferenceParam preferenceParam) {

        int i = 0;
        log.info("UserServiceImpl.addPreference业务开始，结果：{}",preferenceParam);
        QueryWrapper<Preference> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",preferenceParam.getUserId());
        queryWrapper.eq("category_id",preferenceParam.getCategoryId());
        Long aLong = preferenceMapper.selectCount(queryWrapper);
        if (aLong == 1) {
            Preference oldPreference = preferenceMapper.selectOne(queryWrapper);
            Integer frequencyOld = oldPreference.getFrequency();
            oldPreference.setFrequency(frequencyOld+preferenceParam.getFrequency());
            i = preferenceMapper.updateById(oldPreference);

        }else{
            Preference newPreference = new Preference();
            newPreference.setUserId(preferenceParam.getUserId());
            newPreference.setCategoryId(preferenceParam.getCategoryId());
            newPreference.setFrequency(preferenceParam.getFrequency());
            i = preferenceMapper.insert(newPreference);
        }

        log.info("UserServiceImpl.addPreference业务结束，结果：{}",i);
        return R.ok("用户偏好添加成功！");
    }

    /**
     * 查询用户的偏好
     *
     * @param userId
     * @return
     */
    @Override
    public R getpreference(Integer userId) {
        List<Product> allCategoryPre = new ArrayList<>();
        int listLength = 3;
        QueryWrapper<Preference> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Preference> preferenceList = preferenceMapper.selectList(queryWrapper);
        if(preferenceList.size()==0){
            log.info("UserServiceImpl.getPreference业务结束，结果：{}","无该用户偏好数据");
            return R.fail("无该用户偏好数据");
        }

        Collections.sort(preferenceList, new Comparator<Preference>() {
            @Override
            public int compare(Preference o1, Preference o2) {
                return o2.getFrequency()-o1.getFrequency();
            }
        });

        if(preferenceList.size()<3){
            listLength = preferenceList.size();
        }
        List<Preference> newList = preferenceList.subList(0,listLength);
        log.info("新List:{}",newList.toString());
        List<Product> productList = new ArrayList<>();
        newList.forEach(preferenceOne ->{
            CategoryParam categoryParam = new CategoryParam();
            categoryParam.setCategoryId(preferenceOne.getCategoryId());
            List<Product> oneCategoryPre = productClient.getPreference(categoryParam);
            allCategoryPre.addAll(oneCategoryPre);
        });

        R ok = R.ok("查询成功",allCategoryPre);
        log.info("UserServiceImpl.getPreference业务结束成功，结果：{}",ok);
        return ok;
    }
}
