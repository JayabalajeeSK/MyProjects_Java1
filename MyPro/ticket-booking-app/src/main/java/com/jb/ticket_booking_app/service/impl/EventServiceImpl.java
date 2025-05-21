package com.jb.ticket_booking_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.ticket_booking_app.entity.Event;
import com.jb.ticket_booking_app.repo.EventRepository;
import com.jb.ticket_booking_app.service.EventService;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepo;

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepo.findById(id).orElseThrow();
    }

    ///////////////////////////////////////////////////

    public Event addEvent(Event event) {
        return eventRepo.save(event);
    }

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = eventRepo.findById(id).orElseThrow();

        event.setName(updatedEvent.getName());
        event.setDate(updatedEvent.getDate());
        event.setPrice(updatedEvent.getPrice());
        event.setAvailableSeats(updatedEvent.getAvailableSeats());
        return eventRepo.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepo.deleteById(id);
    }


}
