package au.edu.rmit.student.weiminsu.cosc2626.playtogether.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@Data
public class ChildEventMapping {
    UUID childId;
    UUID eventId;
    Boolean attending;
}
