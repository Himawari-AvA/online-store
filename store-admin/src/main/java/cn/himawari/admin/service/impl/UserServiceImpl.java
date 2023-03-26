package cn.himawari.admin.service.impl;

import cn.himawari.admin.service.UserService;
import cn.himawari.clients.UserClient;
import cn.himawari.param.CartListParam;
import cn.himawari.param.PageParam;
import cn.himawari.pojo.User;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserClient userClient;

    /**
     * 用户展示的接口
     *
     * @param pageParam
     * @return
     */
    @Cacheable(value = "list.user",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R userList(PageParam pageParam) {

        log.info("UserServiceImpl.userList业务开始，参数：{}",pageParam);
        R r = userClient.adminListPage(pageParam);
        log.info("UserServiceImpl.userList业务结束，结果：{}",r);
        return r;
    }

    /**
     *
     * @param cartListParam
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R userRemove(CartListParam cartListParam) {

        R r = userClient.adminRemove(cartListParam);
        log.info("UserServiceImpl.userRemove业务结束，结果：{}",r);
        return r;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R userUpdate(User user) {
        R r = userClient.adminUpdate(user);
        log.info("UserServiceImpl.userUpdate业务结束，结果：{}",r);
        return r;
    }

    /**
     * 添加用户信息
     *
     * @param user
     * @return
     */
    @CacheEvict(value = "list.user", allEntries = true)
    @Override
    public R save(User user) {
        R r = userClient.adminSave(user);
        log.info("UserServiceImpl.save业务结束，结果：{}",r);
        return r;

    }
}
