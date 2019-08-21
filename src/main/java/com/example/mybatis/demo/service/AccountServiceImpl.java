package com.example.mybatis.demo.service;

import com.example.mybatis.demo.domain.Account;
import com.example.mybatis.demo.mapper.AccountMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountMapper accountMapper;

  @Override
  public int saveAccount(Account account) {
    return accountMapper.insertSelective(account);
  }

  @Override
  @Transactional
  public int updateAccount1(Account account,boolean flag) throws Exception {
    log.debug("更新账户信息 START -----------update account：{}",account);

    Account accountByBeforeUpdate = accountMapper.selectByPrimaryKey(account.getId());
    log.debug("更新前 账户信息 beforeUpdate account：{}",accountByBeforeUpdate);

    int  result = accountMapper.updateByPrimaryKeySelective(account);

    Account accountByAfterUpdate = accountMapper.findAccountByUserId(account.getUserId().intValue());
    log.debug("更新后 账户信息 afterUpdate account：{}",accountByAfterUpdate);

    log.debug("更新账户信息 结果 result:{}",result);
    if(flag){
      throw new RuntimeException("被调用端抛出异常,事务回滚");
    }
    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateAccount2(Account account,boolean flag) throws Exception {
    log.debug("更新账户信息 START -----------update account：{}",account);

    Account accountByBeforeUpdate = accountMapper.selectByPrimaryKey(account.getId());
    log.debug("更新前 账户信息 beforeUpdate account：{}",accountByBeforeUpdate);

    int  result = accountMapper.updateByPrimaryKeySelective(account);

    Account accountByAfterUpdate = accountMapper.findAccountByUserId(account.getUserId().intValue());
    log.debug("更新后 账户信息 afterUpdate account：{}",accountByAfterUpdate);

    log.debug("更新账户信息 结果 result:{}",result);

    if(flag){
      throw new Exception("被调用端抛出异常,事务回滚");
    }
    return result;
  }

  /**
   *  REQUIRES_NEW 被已有事务的方法调用，会重新开启一个事务
   * @param account
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
  public int updateAccount3(Account account,boolean flag) throws Exception {
    log.debug("更新账户信息 START -----------update account：{}",account);

    Account accountByBeforeUpdate = accountMapper.selectByPrimaryKey(account.getId());
    log.debug("更新前 账户信息 beforeUpdate account：{}",accountByBeforeUpdate);

    int  result = accountMapper.updateByPrimaryKeySelective(account);

    Account accountByAfterUpdate = accountMapper.findAccountByUserId(account.getUserId().intValue());
    log.debug("更新后 账户信息 afterUpdate account：{}",accountByAfterUpdate);

    log.debug("更新账户信息 结果 result:{}",result);

    if(flag){
      throw new RuntimeException("测试被调用端抛出异常,事务回滚");
    }
    return result;
  }

  /**
   * 被事务方法调用，会起一个新的子事务并设置savepoint（数据库设置子事务）, 在方法已提交，但是调用端事务回滚时，本方法也会回滚
   * @param account
   * @return
   * @throws Exception
   */
  @Override
  @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
  public int updateAccount4(Account account,boolean flag) throws Exception {
    log.debug("更新账户信息 START -----------update account：{}",account);

    Account accountByBeforeUpdate = accountMapper.selectByPrimaryKey(account.getId());
    log.debug("更新前 账户信息 beforeUpdate account：{}",accountByBeforeUpdate);

    int  result = accountMapper.updateByPrimaryKeySelective(account);

    Account accountByAfterUpdate = accountMapper.findAccountByUserId(account.getUserId().intValue());
    log.debug("更新后 账户信息 afterUpdate account：{}",accountByAfterUpdate);

    log.debug("更新账户信息 结果 result:{}",result);

    if(flag){
      throw new OutOfMemoryError("测试被调用端抛出Error,事务回滚");
    }
    return result;
  }

  @Override
  public Account getAccountById(Integer id) {
    return accountMapper.selectByPrimaryKey(id);
  }

  @Override
  public Account getAccountByUserId(Integer userId) {
    return accountMapper.findAccountByUserId(userId);
  }
}
