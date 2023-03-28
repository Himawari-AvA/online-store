package cn.himawari.param;

import cn.himawari.pojo.Product;
import lombok.Data;

@Data
public class ProductSaveParam extends Product {

    private String pictures;
}
