package au.edu.rmit.student.weiminsu.cosc2626.playtogether.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Child {
    private UUID id;
    private UUID accountId;
    private String name;
    private Integer age;
}
