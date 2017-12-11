package com.onlinebanking.rulefactory;

import com.onlinebanking.customer.Customer;

final public class RuleFactory {
	private RuleFactory(){}

	public static Rule getRule(Customer customer) {
		return Rule.generateRule(customer);
	}
}
