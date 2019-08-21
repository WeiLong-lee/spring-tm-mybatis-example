package com.example.mybatis.demo.params;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UserParams {

  private Long userId;
  private String userName;
  private String userPwd;
  private String phone;
  private String address;

  private Long pId;
  private BigDecimal price;
  private String pName;
  private String pCategories;

}
