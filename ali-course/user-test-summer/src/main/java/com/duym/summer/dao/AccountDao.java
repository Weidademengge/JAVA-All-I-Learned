package com.duym.summer.dao;


import com.duym.summer.domain.Account;

public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}