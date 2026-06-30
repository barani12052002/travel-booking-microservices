package com.barani.travel.serviceImpl;
import com.barani.travel.dto.PriceRequest;
import com.barani.travel.dto.PriceResponse;
import com.barani.travel.entity.Booking;
import com.barani.travel.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.repository.BookingRepository;
import com.barani.travel.service.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final ProviderClient providerClient;

    public BookingServiceImpl(
            BookingRepository bookingRepository,
            ProviderClient providerClient) {

        this.bookingRepository = bookingRepository;
        this.providerClient = providerClient;
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        // Step 1 - Get Price from Provider

        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setAdultCount(request.getAdultCount());
        priceRequest.setChildCount(request.getChildCount());

        PriceResponse priceResponse = providerClient.getPrice(priceRequest);

        // Step 2 - Provider Booking

        BookingRequest providerRequest =
                new BookingRequest();

        providerRequest.setCustomerName(request.getCustomerName());
        providerRequest.setCustomerEmail(request.getCustomerEmail());
        providerRequest.setTravelDate(LocalDate.parse(request.getTravelDate().toString()));
        providerRequest.setTimeSlot(request.getTimeSlot());
        providerRequest.setAdultCount(request.getAdultCount());
        providerRequest.setChildCount(request.getChildCount());

        BookingResponse providerResponse =
                providerClient.createBooking(providerRequest);

        // Step 3 - Save Booking

        Booking booking = new Booking();

        booking.setBookingReference(
                "BK-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());

        booking.setProviderBookingId(
                providerResponse.getProviderBookingId());

        booking.setCustomerName(request.getCustomerName());

        booking.setCustomerEmail(request.getCustomerEmail());

        booking.setCustomerPhone(request.getCustomerPhone());

        booking.setAttractionCode(request.getAttractionCode());

        booking.setTravelDate(request.getTravelDate());

        booking.setAdultCount(request.getAdultCount());

        booking.setChildCount(request.getChildCount());

        booking.setTotalAmount(
                BigDecimal.valueOf(priceResponse.getTotalPrice()));

        booking.setBookingStatus(
                BookingStatus.CONFIRMED);

        bookingRepository.save(booking);

        // Step 4 - Return Response

        BookingResponse response =
                new BookingResponse();

        response.setBookingReference(
                booking.getBookingReference());

        response.setBookingStatus(
                booking.getBookingStatus().name());

        response.setTotalAmount(
                booking.getTotalAmount());

        response.setMessage(
                providerResponse.getMessage());

        return response;
    }
}