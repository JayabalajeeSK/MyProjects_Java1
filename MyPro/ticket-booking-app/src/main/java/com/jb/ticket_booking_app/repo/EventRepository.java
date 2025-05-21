package com.jb.ticket_booking_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jb.ticket_booking_app.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {}
