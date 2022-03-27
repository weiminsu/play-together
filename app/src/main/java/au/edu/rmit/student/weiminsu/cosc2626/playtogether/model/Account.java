package au.edu.rmit.student.weiminsu.cosc2626.playtogether.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Account {
    private UUID accountId;
    private String nickname;
    private String email;
    private String password;
    private String description;
    private String suburb;
    private List<Child> children;
    private String profileImageUrl;
}
