package com.jb.ticket_booking_app.service;


import com.jb.ticket_booking_app.entity.Event;

import java.util.List;

public interface EventService {

    List<Event> getAllEvents();

    Event getEventById(Long id);

    Event addEvent(Event event);
    Event updateEvent(Long id, Event updatedEvent);
    void deleteEvent(Long id);

    
}
