package com.example.mybatis.demo.controller;



import com.example.mybatis.demo.domain.Account;
import com.example.mybatis.demo.domain.MUser;
import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.mybatis.demo.service.UserService;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = "/add",method = RequestMethod.POST)
  public String addUser(@RequestBody MUser user){
      int result = userService.insertUser(user);
    if( result !=0){
      return "SUCCESS id:"+ result;
    }
    return "ERROR";
  }
  @GetMapping(value = "/get/{userId}")
  public MUser getUser(@PathVariable Integer userId){
     return userService.getUserById(userId);
  }
  @GetMapping(value = "/get")
  public Map<Integer, MUser> getUser(@RequestParam String userName){
    return userService.getUserMapByUserName(userName);
  }

  @GetMapping(value = "/get-user-info")
  public UserInfo getUserInfo(@RequestParam Integer userId){
    return userService.getUserInfo(userId);
  }

  @GetMapping(value = "/product")
  public List<Product> getProduct(Product record){
    return userService.getProduct(record);
  }


  @GetMapping(value = "/get-account")
  public Account getAccount(@RequestParam Integer userId){
    return userService.getAccount(userId);
  }

  @GetMapping(value = "/get-order-list")
  public List<UOrder> getOrderList(@RequestParam Integer userId){
    return userService.getOrderList(userId);
  }
  @PostMapping(value = "update-user-info/{methCode}")
  public String updateUserInfo(@PathVariable String methCode,@RequestBody UserInfo userInfo,@RequestParam Boolean throwOneFlag,@RequestParam Boolean throwTwoFlag){

    try {
      boolean result = false;
      switch (methCode){
        case "1": result = userService.updateUserInfo1(userInfo,throwOneFlag,throwTwoFlag); break;
        case "2": result = userService.updateUserInfo2(userInfo,throwOneFlag,throwTwoFlag);break;
        case "3": result = userService.updateUserInfo3(userInfo,throwOneFlag,throwTwoFlag);break;
        case "4": result = userService.updateUserInfo4(userInfo,throwOneFlag,throwTwoFlag);break;
        default:
          break;
      }

      if(result){
        return "SUCCESS";
      }
    } catch (Exception e) {
       log.error("更新失败 抛出异常 e:{}",e);
       return "Exception";
    }catch (Error e){
      log.error("更新失败 抛出失败 e:{}",e);
      return "ERROR";
    }
    return "NULL";
  }
}
