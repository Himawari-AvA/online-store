package cn.himawari.carousel.service.Impl;

import cn.himawari.carousel.mapper.CarouselMapper;
import cn.himawari.carousel.service.CarouselService;
import cn.himawari.pojo.Carousel;
import cn.himawari.utils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarouselServiceImpl implements CarouselService {


    @Autowired
    private CarouselMapper carouselMapper;

//    按优先级返回数据，使用stream流进行内存数据切割
    @Cacheable(value = "list.carousel",key = "#root.methodName",cacheManager = "cacheManagerDay")
    @Override
    public R list() {

        QueryWrapper<Carousel> carouselQueryWrapper = new QueryWrapper<>();
        carouselQueryWrapper.orderByDesc("priority");
        List<Carousel> list = carouselMapper.selectList(carouselQueryWrapper);
        List<Carousel> collect = list.stream().limit(6).collect(Collectors.toList());
        R ok = R.ok(collect);
        log.info("CarouselServiceImpl.list业务结束，结果：{}",ok);
        return ok;
    }
}
