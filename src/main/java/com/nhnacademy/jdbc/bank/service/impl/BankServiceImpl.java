package com.nhnacademy.jdbc.bank.service.impl;

import com.nhnacademy.jdbc.bank.domain.Account;
import com.nhnacademy.jdbc.bank.exception.AccountAreadyExistException;
import com.nhnacademy.jdbc.bank.exception.AccountNotFoundException;
import com.nhnacademy.jdbc.bank.exception.BalanceNotEnoughException;
import com.nhnacademy.jdbc.bank.repository.AccountRepository;
import com.nhnacademy.jdbc.bank.repository.impl.AccountRepositoryImpl;
import com.nhnacademy.jdbc.bank.service.BankService;

import java.sql.Connection;
import java.util.Optional;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository;

    public BankServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account getAccount(Connection connection, long accountNumber){
        //todo#11 계좌-조회
       return accountRepository.findByAccountNumber(connection, accountNumber).orElse(null);
    }

    @Override
    public void createAccount(Connection connection, Account account){
        //todo#12 계좌-등록
        if (this.isExistAccount(connection, account.getAccountNumber())){
            throw new AccountAreadyExistException(account.getAccountNumber());
        }
        accountRepository.save(connection, account);
    }

    @Override
    public boolean depositAccount(Connection connection, long accountNumber, long amount){
        //todo#13 예금, 계좌가 존재하는지 체크 -> 예금실행 -> 성공 true, 실패 false;
        if (!this.isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        Account account = this.getAccount(connection, accountNumber);
        return accountRepository.deposit(connection, accountNumber, amount) > 0;
        }

    @Override
    public boolean withdrawAccount(Connection connection, long accountNumber, long amount){
        //todo#14 출금, 계좌가 존재하는지 체크 ->  출금가능여부 체크 -> 출금실행, 성공 true, 실폐 false 반환
        if (!this.isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        Account account = this.getAccount(connection, accountNumber);
        if (account.getBalance() < amount){
            throw new BalanceNotEnoughException(accountNumber);
        }
        return accountRepository.withdraw(connection, accountNumber, amount) > 0;
    }

    @Override
    public void transferAmount(Connection connection, long accountNumberFrom, long accountNumberTo, long amount){
        //todo#15 계좌 이체 accountNumberFrom -> accountNumberTo 으로 amount만큼 이체
        this.depositAccount(connection, accountNumberFrom, amount);
        this.withdrawAccount(connection, accountNumberTo, amount);
    }

    @Override
    public boolean isExistAccount(Connection connection, long accountNumber){
        //todo#16 Account가 존재하면 true , 존재하지 않다면 false
        return accountRepository.countByAccountNumber(connection, accountNumber) > 0;
    }

    @Override
    public void dropAccount(Connection connection, long accountNumber) {
        //todo#17 account 삭제
        if (!this.isExistAccount(connection, accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }
        int result = accountRepository.deleteByAccountNumber(connection, accountNumber);
        if (result == 0){
            throw new RuntimeException();
        }
    }
}