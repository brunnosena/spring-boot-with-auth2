package com.brunnosena.pocspringauth2.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String login;
    private String avatarUrl;
    private String email;
    private String name;
    private String bio;
}
