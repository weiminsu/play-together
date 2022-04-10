package au.edu.rmit.student.weiminsu.cosc2626.playtogether.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Event {
    private UUID eventId;
    private String eventImageURL;
    private Instant eventDateTime;
    private String location;
    private Integer numOfParticipants;
    private Integer minAge;
    private Integer maxAge;
    private String eventDescription;
    private List<ChildEventMapping> participants;
}
