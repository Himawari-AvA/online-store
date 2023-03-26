package cn.himawari.user.service;

import cn.himawari.pojo.Address;
import cn.himawari.utils.R;

public interface AddressService {
    /**
     * 根据用户id查询地址数据
     * @param userId 已经校验过
     * @return 001 004校验码
     */
    R list(Integer userId);

    /**
     * 插入地址数据成功之后，返回新的地址数据集合
     * @param address 校验完毕
     * @return  地址数据集合
     */
    R save(Address address);

    /**
     * 根据id删除地址数据
     * @param id 地址id
     * @return 001 004
     */
    R remove(Integer id);
}
