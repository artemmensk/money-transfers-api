package com.artemmensk.account;

import lombok.*;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
public class Account {
    private final Long id;
    private Integer balance = 0;
}
