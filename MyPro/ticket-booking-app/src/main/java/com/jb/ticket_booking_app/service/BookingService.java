package com.jb.ticket_booking_app.service;


import com.jb.ticket_booking_app.entity.Booking;

import java.util.List;

public interface BookingService {

    List<Booking> getAllBookings();

    List<Booking> getUserBookings(String username);

    

    Booking bookTicket(String username, Long eventId);
    Booking updateBooking(Long bookingId, Booking updatedBooking);
    Booking cancelBooking(Long bookingId);

}
