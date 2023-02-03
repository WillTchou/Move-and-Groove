package com.portfolio.moveandgroove.activity.model;


import lombok.*;

@Data
@Value
@Builder
@AllArgsConstructor
public class ActivityRequest {
    String name;
    String duration;
}
