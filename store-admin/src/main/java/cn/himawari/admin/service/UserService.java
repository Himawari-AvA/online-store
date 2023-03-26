package cn.himawari.admin.service;

import cn.himawari.param.PageParam;
import cn.himawari.utils.R;

public interface UserService {
    /**
     * 用户展示的接口
     * @param pageParam
     * @return
     */
    R userList(PageParam pageParam);
}
