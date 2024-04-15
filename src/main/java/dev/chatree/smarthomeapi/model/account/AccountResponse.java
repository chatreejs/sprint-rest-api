package dev.chatree.smarthomeapi.model.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean hasHome;
}
