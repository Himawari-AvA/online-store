package cn.himawari.admin.service.impl;

import cn.himawari.admin.mapper.AdminUserMapper;
import cn.himawari.admin.param.AdminUserParam;
import cn.himawari.admin.pojo.AdminUser;
import cn.himawari.admin.service.AdminUserService;
import cn.himawari.constants.UserConstants;
import cn.himawari.utils.MD5Util;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    /**
     * 登陆业务方法
     *
     * @param adminUserParam
     * @return
     */
    @Override
    public AdminUser login(AdminUserParam adminUserParam) {

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",adminUserParam.getUserAccount());
        queryWrapper.eq("user_password", MD5Util.encode(adminUserParam.getUserPassword() + UserConstants.USER_SALT));
        AdminUser user = adminUserMapper.selectOne(queryWrapper);
        log.info("AdminUserServiceImpl.login业务结束，结果：{}",user);
        return user;

    }
}
