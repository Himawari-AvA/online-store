package cn.himawari.doc;


import cn.himawari.pojo.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

//商品搜索数据的实体类
@Data
@NoArgsConstructor
public class ProductDoc extends Product {

    private String all;
    public ProductDoc(Product product){
        super(product.getProductId(),product.getProductName(),product.getCategoryId(),
                product.getProductTitle(),product.getProductIntro(),product.getProductPicture(),
                product.getProductPrice(),product.getProductSellingPrice(),product.getProductNum(),product.getProductSales());
        this.all = product.getProductName()+product.getProductTitle()+product.getProductIntro();
    }
}
