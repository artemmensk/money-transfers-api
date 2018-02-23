package com.artemmensk;

public class Service implements IService {

    @Override
    public Transfer transfer(String amount, String from, String to) {
        System.out.println(amount + " from " + from + " to " + to);
        return new Transfer();
    }
}
