package com.barani.travel.repository;

import com.barani.travel.entity.Booking;
import com.barani.travel.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    List<Booking> findByCustomerEmail(String customerEmail);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);

}