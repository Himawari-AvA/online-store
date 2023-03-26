package cn.himawari.param;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
@Data
public class ProductHistoryParam {
    @NotEmpty
    private List<Integer> productIds;
}
