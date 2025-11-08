package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        final double amountToDeposit = 100;
        this.bankAccount.deposit(1, amountToDeposit);
        this.bankAccount.chargeManagementFees(1);
        assertEquals(amountToDeposit - StrictBankAccount.MANAGEMENT_FEE - StrictBankAccount.TRANSACTION_FEE, this.bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        final double amountToWithdraw = -1;
        try{
            this.bankAccount.withdraw(1, amountToWithdraw);
        } catch ( final IllegalArgumentException e ) {
            System.out.println("You cant withdraw a negative amount");
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        final double amountToWithdraw = this.bankAccount.getBalance() + 1;
        try{
            this.bankAccount.withdraw(1, amountToWithdraw);
        } catch ( final IllegalArgumentException e ) {
            System.out.println("You cant withdraw more money than you have");
        }
    }
}
