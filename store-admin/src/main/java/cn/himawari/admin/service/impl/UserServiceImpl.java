package cn.himawari.admin.service.impl;

import cn.himawari.admin.service.UserService;
import cn.himawari.clients.UserClient;
import cn.himawari.param.PageParam;
import cn.himawari.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserClient userClient;

    /**
     * 用户展示的接口
     *
     * @param pageParam
     * @return
     */
//    @Cacheable(value = "list.user",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    @Override
    public R userList(PageParam pageParam) {

        log.info("UserServiceImpl.userList业务开始，参数：{}",pageParam);
        R r = userClient.adminListPage(pageParam);
        log.info("UserServiceImpl.userList业务结束，结果：{}",r);
        return r;
    }
}
