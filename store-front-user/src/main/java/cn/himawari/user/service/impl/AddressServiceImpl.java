package cn.himawari.user.service.impl;

import cn.himawari.pojo.Address;
import cn.himawari.user.mapper.AddressMapper;
import cn.himawari.user.service.AddressService;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;
    /**
     * 根据用户id查询地址数据
     *
     * @param userId 已经校验过
     * @return 001 004校验码
     */
    @Override
    public R list(Integer userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);

        R ok = R.ok("查询成功",addressList);
        log.info("AddressServiceImpl.list业务结束，结果：{}",ok);
        return ok;
    }

    /**
     * 插入地址数据成功之后，返回新的地址数据集合
     *
     * @param address 校验完毕
     * @return 地址数据集合
     */
    @Override
    public R save(Address address) {

        int rows = addressMapper.insert(address);

        if(rows==0){
            log.info("AddressServiceImpl.save业务结束,结果:{}","插入地址失败");
            return R.fail("插入地址失败");
        }

        return list(address.getUserId());
    }

    /**
     * 根据id删除地址数据
     *
     * @param id 地址id
     * @return 001 004
     */
    @Override
    public R remove(Integer id) {

        int rows = addressMapper.deleteById(id);
        if(rows == 0){
            log.info("AddressServiceImpl.remove业务结束,结果:{}","删除地址失败");
            return R.fail("删除地址数据失败");
        }
        log.info("AddressServiceImpl.remove业务结束,结果:{}","删除地址成功");
        return R.ok("删除地址数据成功");
    }

    /**
     * 根据id查找一个地址数据
     *
     * @param id
     * @return
     */
    @Override
    public Address getone(Integer id) {
        log.info("本次查询的地址id为:{}",id);
        Address address = addressMapper.selectById(id);
//        if(address == null){
//            log.info("AddressServiceImpl.getone业务失败,结果:{}",address);
//            return R.fail("获取地址数据失败");
//        }
        log.info("AddressServiceImpl.getone业务成功,结果:{}",address);
//        return R.ok("获取地址数据成功",address);
        return address;
    }
}
