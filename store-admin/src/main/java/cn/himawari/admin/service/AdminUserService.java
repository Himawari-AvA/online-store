package cn.himawari.admin.service;

import cn.himawari.admin.param.AdminUserParam;
import cn.himawari.admin.pojo.AdminUser;

public interface AdminUserService {

    /**
     * 登陆业务方法
     * @param adminUserParam
     * @return
     */
    AdminUser login(AdminUserParam adminUserParam);
}
