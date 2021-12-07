package ca.sheridancollege.amos.beans;

import lombok.*;

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

}
