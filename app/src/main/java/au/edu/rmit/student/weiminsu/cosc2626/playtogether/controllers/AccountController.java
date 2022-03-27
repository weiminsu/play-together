package au.edu.rmit.student.weiminsu.cosc2626.playtogether.controllers;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.exceptions.AccountNotFoundException;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.mappers.AccountMapper;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable("accountId") String accountId) {
        Account result = accountMapper.getAccountById(UUID.fromString(accountId));
        if (result == null) throw new AccountNotFoundException("Account " + accountId + " not found!");
        return result;
    }

    @PostMapping("/")
    public Account postAccount(
        @RequestBody Account account
    ) {
        Account updatedAccount = accountMapper.upsertAccount(account);
        if (account.getChildren() != null &&!account.getChildren().isEmpty()) {
            updatedAccount.setChildren(accountMapper.insertChildren(account.getChildren()));
        }
        return updatedAccount;
    }
}
