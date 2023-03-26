package cn.himawari.param;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
@Data
public class UserCheckParam {

//    字符串不能为null和“”
    @NotBlank
    private String userName;
}
