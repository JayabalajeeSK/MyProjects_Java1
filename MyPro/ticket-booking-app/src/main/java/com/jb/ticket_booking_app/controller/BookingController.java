package com.jb.ticket_booking_app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jb.ticket_booking_app.entity.Booking;
import com.jb.ticket_booking_app.entity.Event;
import com.jb.ticket_booking_app.service.impl.BookingServiceImpl;
import com.jb.ticket_booking_app.service.impl.EventServiceImpl;

@RestController
@RequestMapping("/api/user")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private EventServiceImpl eventService;

    @GetMapping("/events") //
    public ResponseEntity<List<Event>> viewEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/bookings") //
    public ResponseEntity<List<Booking>> viewBookings(Principal principal) {
        return ResponseEntity.ok(bookingService.getUserBookings(principal.getName()));
    }

    /////////////////////////////////////////////////////////////////////

    @PostMapping("/book/{eventId}") //
    public ResponseEntity<Booking> bookTicket(@PathVariable Long eventId, Principal principal) {
        Booking booking = bookingService.bookTicket(principal.getName(), eventId);
        return ResponseEntity.ok(booking);
    }

    // @PutMapping("/update/{bookingId}") //unable to update by logic
    // public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking updatedBooking) {
    //     Booking booking = bookingService.updateBooking(bookingId, updatedBooking);
    //     return ResponseEntity.ok(booking);
    // }

    @PutMapping("/cancel/booking/{bookingId}") //
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        Booking booking = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(booking);
    }
}
