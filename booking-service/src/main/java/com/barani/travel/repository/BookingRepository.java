package com.barani.travel.repository;

import com.barani.travel.entity.Booking;
import com.barani.travel.enums.BookingStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByBookingReference(String bookingReference);

    List<Booking> findByCustomerEmail(String customerEmail);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);

    long countByBookingStatus(BookingStatus bookingStatus);

    // Total revenue from confirmed bookings
    @Query("""
           SELECT COALESCE(SUM(b.totalAmount), 0)
           FROM Booking b
           WHERE b.bookingStatus = com.barani.travel.enums.BookingStatus.CONFIRMED
           """)
    BigDecimal getTotalRevenue();

}