package com.valko.data.daos;

import com.valko.models.Account;

public interface AccountDao
{
    Account findAccountByCreditCardNumber(String creditCardNumber);
    Account findAccountByIBAN(String IBAN);
    boolean updateAccount(Account account);
}
