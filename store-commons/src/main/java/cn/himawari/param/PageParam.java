package cn.himawari.param;

import lombok.Data;

/**
 * 分页属性
 */
@Data
public class PageParam {
    private int currentPage = 1;
    private int pageSize = 15;
}
