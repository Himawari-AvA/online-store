package cn.himawari.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@TableName("address")
public class Address implements Serializable {
    public static final Long serialVersionUID = 1L;
    //包装类型 不为空就行

    @TableId(type = IdType.AUTO)
    private Integer id;
    //不能为空字符串
    @NotBlank
    private String linkman;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
    @NotNull
    @TableField("user_id")
    private Integer userId;

}
