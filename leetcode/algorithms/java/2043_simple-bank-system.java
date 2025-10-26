// Source: https://leetcode.com/problems/simple-bank-system/
// Author: Tom Murphy https://github.com/murphy-codes/
// Date: 2025-10-26
// At the time of submission:
//   Runtime 95 ms Beats 97.49%
//   Memory 101.42 MB Beats 37.83%

/****************************************
* 
* You have been tasked with writing a program for a popular bank that will
* _ automate all its incoming transactions (transfer, deposit, and withdraw).
* _ The bank has `n` accounts numbered from `1` to `n`. The initial balance of
* _ each account is stored in a 0-indexed integer array `balance`, with the
* _ `(i + 1)^th` account having an initial balance of `balance[i]`.
* Execute all the valid transactions. A transaction is valid if:
* • The given account number(s) are between `1` and `n`, and
* • The amount of money withdrawn or transferred from is less than or equal
* __ to the balance of the account.
* Implement the `Bank` class:
* • `Bank(long[] balance)` Initializes the object with the 0-indexed
* __ integer array `balance`.
* • `boolean transfer(int account1, int account2, long money)` Transfers
* __ `money` dollars from the account numbered `account1` to the account
* __ numbered `account2`. Return `true` if the transaction was successful,
* __ `false` otherwise.
* • `boolean deposit(int account, long money)` Deposit `money` dollars into
* __ the account numbered `account`. Return `true` if the transaction was
* __ successful, `false` otherwise.
* • `boolean withdraw(int account, long money)` Withdraw `money` dollars
* __ from the account numbered `account`. Return `true` if the transaction
* __ was successful, `false` otherwise.
*
* Example 1:
* Input
* ["Bank", "withdraw", "transfer", "deposit", "transfer", "withdraw"]
* [[[10, 100, 20, 50, 30]], [3, 10], [5, 1, 20], [5, 20], [3, 4, 15], [10, 50]]
* Output
* [null, true, true, true, false, false]
* Explanation
* Bank bank = new Bank([10, 100, 20, 50, 30]);
* bank.withdraw(3, 10);    // return true, account 3 has a balance of $20, so it is valid to withdraw $10.
* // Account 3 has $20 - $10 = $10.
* bank.transfer(5, 1, 20); // return true, account 5 has a balance of $30, so it is valid to transfer $20.
* // Account 5 has $30 - $20 = $10, and account 1 has $10 + $20 = $30.
* bank.deposit(5, 20);     // return true, it is valid to deposit $20 to account 5.
* // Account 5 has $10 + $20 = $30.
* bank.transfer(3, 4, 15); // return false, the current balance of account 3 is $10,
* // so it is invalid to transfer $15 from it.
* bank.withdraw(10, 50);   // return false, it is invalid because account 10 does not exist.
*
* Constraints:
* • `n == balance.length`
* • `1 <= n, account, account1, account2 <= 10^5`
* • `0 <= balance[i], money <= 10^12`
* • At most `10^4` calls will be made to each function `transfer`, `deposit`, `withdraw`.
* 
****************************************/

class Bank {
    // Simulates a simple banking system supporting deposit, withdraw, and transfer.
    // Each transaction checks for valid account numbers and sufficient funds.
    // Updates are applied directly to an internal balance array in O(1) time.
    // Time Complexity: O(1) per operation. Space Complexity: O(n) for balances.

    private long[] balance;

    public Bank(long[] balance) {
        this.balance = balance;
    }

    public boolean transfer(int account1, int account2, long money) {
        if (!isValid(account1) || !isValid(account2)) return false;
        if (balance[account1 - 1] < money) return false;
        balance[account1 - 1] -= money;
        balance[account2 - 1] += money;
        return true;
    }

    public boolean deposit(int account, long money) {
        if (!isValid(account)) return false;
        balance[account - 1] += money;
        return true;
    }

    public boolean withdraw(int account, long money) {
        if (!isValid(account) || balance[account - 1] < money) return false;
        balance[account - 1] -= money;
        return true;
    }

    // Helper method to validate account number
    private boolean isValid(int account) {
        return account >= 1 && account <= balance.length;
    }
}

/**
 * Your Bank object will be instantiated and called as such:
 * Bank obj = new Bank(balance);
 * boolean param_1 = obj.transfer(account1,account2,money);
 * boolean param_2 = obj.deposit(account,money);
 * boolean param_3 = obj.withdraw(account,money);
 */