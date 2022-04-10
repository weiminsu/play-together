package au.edu.rmit.student.weiminsu.cosc2626.playtogether.controllers;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.exceptions.AccountNotFoundException;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.mappers.AccountMapper;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Account;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Child;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable("accountId") String accountId) throws AccountNotFoundException {
        Account result = accountMapper.getAccountById(UUID.fromString(accountId));
        if (result == null) throw new AccountNotFoundException("Account " + accountId + " not found!");
        return result;
    }

    @PostMapping("/")
    public Account postAccount(
        @RequestBody Account account
    ) {
        Account updatedAccount = accountMapper.upsertAccount(account);
        List<Child> children = account.getChildren();
        if ( children != null && !children.isEmpty()) {
            children.forEach(it -> {if(it.getAccountId() == null) it.setAccountId(UUID.randomUUID());});
            updatedAccount.setChildren(
                accountMapper.upsertChildren(children)
            );
        }
        return updatedAccount;
    }
}
