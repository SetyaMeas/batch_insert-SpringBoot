package com.example.batch_insert.shared;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.example.batch_insert.model.InsertType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsertResult {
    private int total;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private InsertType insertType;

    public long getInsertTimeInMiliSecond() {
        return ChronoUnit.MILLIS.between(startAt, endAt);
    }
}
