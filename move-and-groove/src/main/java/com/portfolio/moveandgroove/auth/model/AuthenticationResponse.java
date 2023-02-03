package com.portfolio.moveandgroove.auth.model;

import lombok.*;

@Data
@Value
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    String token;
}
