package com.example.mybatis.demo.service;

import com.example.mybatis.demo.domain.Account;

public interface AccountService {


  int saveAccount(Account account);

  int updateAccount1(Account account, boolean flag) throws Exception;

  int updateAccount2(Account account, boolean flag) throws Exception;

  int updateAccount3(Account account, boolean flag) throws Exception;

  int updateAccount4(Account account, boolean flag) throws Exception;


  Account getAccountById(Integer id);

  Account getAccountByUserId(Integer userId);

}
