package cn.himawari.admin.service;

import cn.himawari.param.CartListParam;
import cn.himawari.param.PageParam;
import cn.himawari.pojo.User;
import cn.himawari.utils.R;

public interface UserService {
    /**
     * 用户展示的接口
     * @param pageParam
     * @return
     */
    R userList(PageParam pageParam);

    /**
     * 删除用户数据
     * @param
     * @return
     */
    R userRemove(CartListParam cartListParam);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    R userUpdate(User user);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    R save(User user);
}
