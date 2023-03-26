package cn.himawari.admin.controller;

import cn.himawari.admin.service.ProductService;
import cn.himawari.admin.utils.AliyunOSSUtils;
import cn.himawari.param.ProductSearchParam;
import cn.himawari.utils.R;
import net.sf.jsqlparser.schema.MultiPartName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private AliyunOSSUtils aliyunOSSUtils;

    @GetMapping("list")
    public R adminList(ProductSearchParam productSearchParam){
        return productService.search(productSearchParam);
    }

    @PostMapping("upload")
    public R adminUpload(@RequestParam("img") MultipartFile img) throws Exception {
        String filename = img.getOriginalFilename();
        filename = UUID.randomUUID().toString().replaceAll("-","")+filename;
        String contentType = img.getContentType();
        byte[] bytes = img.getBytes();
        int hours = 1000;
        String url = aliyunOSSUtils.uploadImage(filename, bytes, contentType, hours);
        System.out.println("url = " + url);
        return R.ok("图片上传成功！",url);
    }
}
