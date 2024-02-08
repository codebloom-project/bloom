package com.green.bloom.chatbot;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleInfo {
    private String title;
    private boolean categori;
    private LocalDateTime start;
    private LocalDateTime end;
}
