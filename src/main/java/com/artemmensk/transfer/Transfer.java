package com.artemmensk.transfer;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "uuid")
@Getter
@ToString
public class Transfer {
    private final String uuid = UUID.randomUUID().toString();
    private final Timestamp timestamp = new Timestamp(Instant.now().getEpochSecond());
    private final Long from;
    private final Long to;
    private final Integer amount;
}