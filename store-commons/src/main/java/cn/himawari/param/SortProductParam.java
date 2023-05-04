package cn.himawari.param;

import javax.validation.constraints.NotNull;
import java.util.List;

public class SortProductParam {
    @NotNull
    private List<Integer> categoryID;
    private int sort = 0;
    private int currentPage = 1;
    private int pageSize = 15;
}
