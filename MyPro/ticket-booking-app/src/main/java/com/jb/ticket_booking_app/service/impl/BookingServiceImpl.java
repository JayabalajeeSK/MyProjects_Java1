package com.jb.ticket_booking_app.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jb.ticket_booking_app.entity.Booking;
import com.jb.ticket_booking_app.entity.Event;
import com.jb.ticket_booking_app.repo.BookingRepository;
import com.jb.ticket_booking_app.repo.EventRepository;
import com.jb.ticket_booking_app.service.BookingService;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private EventRepository eventRepo;

    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    public List<Booking> getUserBookings(String username) {
        return bookingRepo.findByUsername(username);
    }

    ///////////////////////////////////////////////////////////

    public Booking bookTicket(String username, Long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow();

        if (event.getAvailableSeats() <= 0) {
            throw new RuntimeException("No seats available");
        }
        event.setAvailableSeats(event.getAvailableSeats() - 1);
        eventRepo.save(event);

        Booking booking = new Booking();
        booking.setUsername(username);
        booking.setEvent(event);
        booking.setBookingTime(LocalDateTime.now());
        return bookingRepo.save(booking);
    }

    public Booking updateBooking(Long bookingId, Booking updatedBooking) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new RuntimeException("Id Not found"));

        booking.setBookingTime(updatedBooking.getBookingTime());
        booking.setCanceled(updatedBooking.isCanceled());
        return bookingRepo.save(booking);
    }


    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow();
        booking.setCanceled(true);
        
        Event event = booking.getEvent();
        event.setAvailableSeats(event.getAvailableSeats() + 1);
        eventRepo.save(event);
        return bookingRepo.save(booking);
    }
}
