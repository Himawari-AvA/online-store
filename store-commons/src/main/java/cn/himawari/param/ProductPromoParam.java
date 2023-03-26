package cn.himawari.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ProductPromoParam {
    @NotBlank
    private String categoryName;
}
