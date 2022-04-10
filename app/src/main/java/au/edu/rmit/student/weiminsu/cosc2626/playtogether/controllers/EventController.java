package au.edu.rmit.student.weiminsu.cosc2626.playtogether.controllers;

import au.edu.rmit.student.weiminsu.cosc2626.playtogether.exceptions.EventNotFoundException;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.mappers.EventMapper;
import au.edu.rmit.student.weiminsu.cosc2626.playtogether.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventMapper eventMapper;

    @GetMapping("/{eventId}")
    public Event getEvent(@PathVariable("eventId") String eventId) throws EventNotFoundException {
        Event result = eventMapper.getEventById(UUID.fromString(eventId));
        if (result == null) throw new EventNotFoundException("Event " + eventId + " not found!");
        return result;
    }


    // todo: finish implementation
    @PostMapping("/")
    public Event postEvent(
            @RequestBody Event event
    ) {
        return event;
//        Event updatedEvent = eventMapper.updateEvent(event);
//        if (children != null && !children.isEmpty()) {
//            children.forEach(it -> {if(it.getAccountId() == null) it.setAccountId(UUID.randomUUID());});
//            updatedAccount.setChildren(
//                    accountMapper.insertChildren(children)
//            );
//        }
//        return updatedEvent;
    }
}
