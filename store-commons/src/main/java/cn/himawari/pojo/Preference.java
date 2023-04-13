package cn.himawari.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data
@TableName("preference")
public class Preference  implements Serializable {

    public static final Long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonProperty("user_id")
    private Integer userId;
    @JsonProperty("category_id")
    private Integer  categoryId; //订单编号,选择使用时间戳
//    @JsonProperty("frequency")
    private Integer frequency;

}
