package com.artemmensk.account;

import lombok.*;

import java.util.concurrent.atomic.AtomicLong;

@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
public class Account {
    private static final AtomicLong AUTO_INCREMENT = new AtomicLong(1);
    private final Long id = AUTO_INCREMENT.getAndIncrement();
    private Integer balance = 0;
}
