package cn.himawari.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class OrderCancelParam {
    @JsonProperty("order_id")
    @NotNull
    private Long orderId;
}
