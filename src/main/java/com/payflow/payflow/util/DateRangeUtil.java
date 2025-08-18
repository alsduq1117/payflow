package com.payflow.payflow.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class DateRangeUtil {

    public DateRange of(LocalDate from, LocalDate to) {
        LocalDate f = from, t = to;

        if (f == null && t == null) f = t = LocalDate.now();
        if (f == null) f = t;
        if (t == null) t = f;
        if (f.isAfter(t)) throw new IllegalArgumentException("from이 to보다 이후일 수 없습니다.");

        return new DateRange(f.atStartOfDay(), t.plusDays(1).atStartOfDay());
    }

    @Getter
    public static class DateRange {
        private final LocalDateTime start;        // 포함
        private final LocalDateTime end; // 미포함

        public DateRange(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
    }
}
