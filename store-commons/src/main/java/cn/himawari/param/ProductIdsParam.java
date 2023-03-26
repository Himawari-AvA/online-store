package cn.himawari.param;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProductIdsParam extends PageParam{
    @NotNull
    private List<Integer> categoryID;
//    private int currentPage = 1; //默认值
//    private int pageSize = 15;

}
