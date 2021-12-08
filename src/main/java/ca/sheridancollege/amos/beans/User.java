package ca.sheridancollege.amos.beans;

import lombok.*;

/**
 * This class represents a user of the web application who has a userId, email, password, and either an admin, member,
 * or guest role
 *
 * @author Amos Turner
 * @since 2021-12-08
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @NonNull
    private Long userId;
    @NonNull
    private String email;
    @NonNull
    private String EncryptedPassword;
    @NonNull
    private Boolean enabled;
    private String [] role = {"ADMIN", "MEMBER", "GUEST"};
}
