package cn.xavier.hrm.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.PRIVATE_MEMBER;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KillOrderVo {
    private Long killCourseId;
    private BigDecimal price;
    private Long userId;
    private String orderSn;
    private String sessionNumber;
    private String killCourseName;
    private int killCount;
}
