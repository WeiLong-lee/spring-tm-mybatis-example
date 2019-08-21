package com.example.mybatis.demo.service;


import com.example.mybatis.demo.domain.Account;
import com.example.mybatis.demo.domain.MUser;
import com.example.mybatis.demo.domain.Product;
import com.example.mybatis.demo.domain.UOrder;
import com.example.mybatis.demo.dto.UserInfo;
import com.example.mybatis.demo.mapper.AccountMapper;
import com.example.mybatis.demo.mapper.MUserMapper;
import com.example.mybatis.demo.mapper.ProductMapper;
import com.example.mybatis.demo.mapper.UOrderMapper;
import java.util.List;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private MUserMapper userMapper;

  @Autowired
  private ProductMapper productMapper;
  @Autowired
  private AccountMapper accountMapper;
  @Autowired
  private UOrderMapper orderMapper;

  @Autowired
  private AccountService accountService;

  @Override
  public int insertUser(MUser user) {

    if(user.getId() ==null){
      userMapper.insertUser(user);
      // 获取返回主键
      return user.getId();
    }
    return userMapper.insert(user);
  }

  @Override
  public MUser getUserById(Integer id) {
    return userMapper.selectUserById(id);
  }

  @Override
  public Map<Integer, MUser> getUserMapByUserName(String userName) {
    return userMapper.selectUserMapByName(userName);
  }

  @Override
  public List<Product> getProduct(Product record) {
    return productMapper.selectProductList(record);
  }

  @Override
  public UserInfo getUserInfo(Integer userId) {
    return userMapper.selectUserInfo(userId);
  }

  @Override
  public Account getAccount(Integer userId) {
    return accountMapper.findAccountByUserId(userId);
  }

  @Override
  public List<UOrder> getOrderList(Integer userId) {
    return orderMapper.findOrderListByUserId(userId);
  }

  /**
   *
   *  更新用户信息 事务管理  声明式事务，默认传播属性为 REQUIRED ，捕获的异常为 RuntimeException，Error
   * @param userInfo
   */
  @Override
  @Transactional
  public boolean updateUserInfo1(UserInfo userInfo,boolean OneFlag,boolean TwoFlag) throws Exception{
    // 更新user 信息 name,pwd
      MUser mUser = userMapper.selectUserById(userInfo.getId());
      log.debug("更新前 用户信息 user：{}",mUser);
      mUser.setName(userInfo.getName());
      mUser.setPwd(userInfo.getPwd());

      Account account = accountMapper.findAccountByUserId(mUser.getId());
      account.setInfo(userInfo.getAccount().getInfo());
      int resultUpdateAccount = accountService.updateAccount1(account,TwoFlag);
       log.debug("更新账户信息结果 resultUpdateAccount：{}",resultUpdateAccount);
      if(resultUpdateAccount ==0){
        return false;
      }
      int resultUpdateUser = userMapper.updateByPrimaryKey(mUser);
      log.debug("更新用户结果 resultUpdateUser：{}",resultUpdateUser);

      if(resultUpdateUser ==0){
        return false;
      }
      MUser mUserAfterUpdate = userMapper.selectUserById(userInfo.getId());
      log.debug("更新后 用户信息 user:{}",mUserAfterUpdate);

    // 1. user 更新成功 抛出异常
    if(OneFlag){
      // 运行时异常
      int i = 10/0 ;
         // 未指明回滚类型时，默认不会回滚
//        throw new Exception("测试抛出Exception异常");
//        throw new RuntimeException("测试抛出RuntimeException异常");
      // 抛出error
//        throw new OutOfMemoryError("测试抛出Error");
    }

 /*   try{
         int i = 10/0 ;
    }catch(Exception e){
        log.error("方法异常 e:{}",e);
        // 手动回滚
//      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }*/

      return true;
  }

  /**
   *  更新用户信息 调用端（A）没有事务，被调用端(B)为事务方法, B 的结果不会引起A的回滚
   * @param userInfo
   * @return
   * @throws Exception
   */
  @Override
  public boolean updateUserInfo2(UserInfo userInfo,boolean OneFlag,boolean TwoFlag) throws Exception {
      // 更新user 信息 name,pwd
      MUser mUser = userMapper.selectUserById(userInfo.getId());
      log.debug("更新前 用户信息 user：{}",mUser);
      mUser.setName(userInfo.getName());
      mUser.setPwd(userInfo.getPwd());
      Account account = accountMapper.findAccountByUserId(mUser.getId());
      account.setInfo(userInfo.getAccount().getInfo());
      int resultUpdateAccount = accountService.updateAccount2(account,TwoFlag);
      log.debug("更新账户信息结果 resultUpdateAccount：{}",resultUpdateAccount);
      if(resultUpdateAccount ==0){
        return false;
      }

      int resultUpdateUser = userMapper.updateByPrimaryKey(mUser);
      log.debug("更新用户结果 resultUpdateUser：{}",resultUpdateUser);

      if(resultUpdateUser ==0){
        return false;
      }
      MUser mUserAfterUpdate = userMapper.selectUserById(userInfo.getId());
      log.debug("更新后 用户信息 user:{}",mUserAfterUpdate);

      if(OneFlag){
        throw new Exception("调用端异常,不会回滚，因为没有事务");
      }

      return true;
  }

  /**
   *
   * @param userInfo
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateUserInfo3(UserInfo userInfo,boolean OneFlag,boolean TwoFlag) throws Exception {
    // 更新user 信息 name,pwd
    MUser mUser = userMapper.selectUserById(userInfo.getId());
    log.debug("更新前 用户信息 user：{}",mUser);
    mUser.setName(userInfo.getName());
    mUser.setPwd(userInfo.getPwd());
    // 方法异常没有捕获，一起回滚
    Account account = accountMapper.findAccountByUserId(mUser.getId());
    account.setInfo(userInfo.getAccount().getInfo());
    int resultUpdateAccount = accountService.updateAccount3(account,TwoFlag);
    log.debug("更新账户信息结果 resultUpdateAccount：{}",resultUpdateAccount);
    if(resultUpdateAccount ==0){
      return false;
    }

    int resultUpdateUser = userMapper.updateByPrimaryKey(mUser);
    log.debug("更新用户结果 resultUpdateUser：{}",resultUpdateUser);
    if(resultUpdateUser ==0){
      return false;
    }
    MUser mUserAfterUpdate = userMapper.selectUserById(userInfo.getId());
    log.debug("更新后 用户信息 user:{}",mUserAfterUpdate);

    // 抛出异常 使当前事务回滚，被调用端事务不回滚
    if(OneFlag){
      throw new Exception("调用端异常回滚,被调用端开启事务方法不会回滚");
    }
    return true;
  }

  /**
   * 方法抛出异常 被调用端开启子事务方法也会回滚
   * @param userInfo
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateUserInfo4(UserInfo userInfo,boolean OneFlag,boolean TwoFlag) throws Exception {
    // 更新user 信息 name,pwd
    MUser mUser = userMapper.selectUserById(userInfo.getId());
    log.debug("更新前 用户信息 user：{}",mUser);
    mUser.setName(userInfo.getName());
    mUser.setPwd(userInfo.getPwd());

    Account account = accountMapper.findAccountByUserId(mUser.getId());
    account.setInfo(userInfo.getAccount().getInfo());
    // 方法异常没有捕获,一起回滚
    int resultUpdateAccount = accountService.updateAccount4(account,TwoFlag);
    log.debug("更新账户信息结果 resultUpdateAccount：{}",resultUpdateAccount);
    if(resultUpdateAccount ==0){
      return false;
    }
    int resultUpdateUser = userMapper.updateByPrimaryKey(mUser);
    log.debug("更新用户结果 resultUpdateUser：{}",resultUpdateUser);
    if(resultUpdateUser ==0){
      return false;
    }
    MUser mUserAfterUpdate = userMapper.selectUserById(userInfo.getId());
    log.debug("更新后 用户信息 user:{}",mUserAfterUpdate);

    // 抛出异常 使所有事务都回滚
    if(OneFlag){
      throw new Exception("调用端异常回滚,被调用端开启子事务方法也回滚");
    }
    return true;
  }


}
