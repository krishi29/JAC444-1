package multi.threaded;

/** This class creates a shared account */
public class Account {
	
	/** Fields */
	private int balance;
	private String currency;
	
	/**
	 * Two argument constructor
	 * @param balance An integet that represents account balance
	 * @param currency A string that represents account currency
	 */
	Account(int balance, String currency) {
		this.balance = balance;
		this.currency = currency;
	}
	
	/** Setters */
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
	/** Getters */
	public int getBalance() {
		return this.balance;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	
	
	// Deposit
	public synchronized void deposit(int value, String currency) {
		while (getBalance() != 0 && getCurrency() != currency) {
			try {
				System.out.println(("You cannot deposit a different currency."));
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
		if (getCurrency() == currency) {
			setCurrency(currency);
			setBalance(balance += value);
		} else {
			setCurrency(currency);
			setBalance(value);
		}
		
		System.out.println("Deposited: " + value + " " + currency + ".");
		System.out.println("Balance: " + getBalance() + " " + getCurrency() + ".");
		notify();
	}
	
	// Withdraw
	public synchronized void withdraw(int value) {
		while (getBalance() < value) {
			try {
				System.out.println("You cannot withdraw, insuficient balance.");
				wait();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		
		setBalance(getBalance() - value);
		System.out.println("Withdraw: " + value + " " + getCurrency() + ".");
		System.out.println("Balance: " + getBalance() + " " + getCurrency() + ".");
		notify();
	}

}