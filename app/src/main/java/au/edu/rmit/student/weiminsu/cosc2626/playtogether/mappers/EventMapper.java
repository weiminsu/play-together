package au.edu.rmit.student.weiminsu.cosc2626.playtogether.mappers;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.ChildEventMapping;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Event;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.UUID;

@Mapper
public interface EventMapper {
    // todo: add select sql and result map
    Event getEventById(UUID fromString);

//    @ResultMap("eventResultMap")
//    @Select("""
//            insert into EVENTS values
//            (eventId, eventImageURL, eventDateTime, location, numberOfParticipants, minAge, maxAge, eventDescription)
//            ON CONFLICT(eventId) DO UPDATE
//            SET
//                eventDateTime = EXCLUDED.eventDateTime,
//                location = EXCLUDED.location,
//                numberOfParticipants = EXCLUDED.numberOfParticipants,
//                minAge = EXCLUDED.minAge,
//                maxAge = EXCLUDED.maxAge,
//                eventDescription = EXCLUDED.eventDescription
//            RETURNING *
//        """
//    )
//    Event upsertEvent(Event event);

    @Select("""
            <script>
            insert into CHILD_EVENT_MAPPING values
            <foreach collection="participants" item="child" separator=",">
            (#{eventId}, #{child.id}, #{child.attending})
            </foreach>
            ON CONFLICT(childId, eventId) DO UPDATE
            SET attending = EXCLUDED.attending
            RETURNING *
            </script>
        """
    )
    ChildEventMapping upsertChildEventMapping(Event event);
}
