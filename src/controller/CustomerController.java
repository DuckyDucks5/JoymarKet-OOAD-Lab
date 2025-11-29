package controller;

import model.Customer;
import repository.CustomerDA;

public class CustomerController {

    public String topUpBalance(Customer customer, double amount) {
        String result = customer.topUpBalance(amount);

        if(!"SUCCESS".equals(result)) {
            return result;
        }

        // save data ke database lewat CustomerDA(Data Access)
        CustomerDA da = new CustomerDA();
        boolean saved = da.saveDA(customer);
        
        if(saved) {
            return "SUCCESS";
        } else {
            return "Failed to update balance!";
        }
    }
}