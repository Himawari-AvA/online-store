package cn.himawari.carousel.controller;

import cn.himawari.carousel.service.CarouselService;
import cn.himawari.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("carousel")
public class CarouselController {
    @Autowired
    private CarouselService carouselService;

    @PostMapping("list")
    public R list(){
        return carouselService.list();
    }
}
