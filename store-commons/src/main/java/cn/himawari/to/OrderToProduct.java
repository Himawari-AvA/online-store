package cn.himawari.to;
//订单对发送商品服务的实体

import lombok.Data;

import java.io.Serializable;
@Data
public class OrderToProduct implements Serializable {


    public static final Long serialVersionUID = 1L;
    //商品id
    private Integer productId;
    //购买数量
    private Integer num;
}
