package cn.himawari.user.service;

import cn.himawari.param.PageParam;
import cn.himawari.param.UserCheckParam;
import cn.himawari.param.UserLoginParam;
import cn.himawari.pojo.User;
import cn.himawari.utils.R;

public interface UserService {
    /**
     * 检查账号是否可用
     * @param userCheckParam 校验完毕的账号参数
     * @return  结果001、004
     */
    R check(UserCheckParam userCheckParam);

    /**
     * 注册业务
     * @param user 已经校验过的
     * @return 结果 001、004
     */
    R register(User user);

    /**
     * 登陆业务
     * @param userLoginParam 已经校验过的账号密码，未加密
     * @return
     */
    R login(UserLoginParam userLoginParam);

    /**
     * 后台管理调用查询全部用户信息
     * @param pageParam
     * @return
     */
    R listPage(PageParam pageParam);

    /**
     * 删除用户
     * @param userId
     * @return
     */
    R remove(Integer userId);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    R update(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    R save(User user);
}
