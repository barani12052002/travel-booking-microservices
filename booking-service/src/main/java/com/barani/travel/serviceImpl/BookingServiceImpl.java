package com.barani.travel.serviceImpl;
import com.barani.travel.dto.provider.PriceRequest;
import com.barani.travel.dto.provider.PriceResponse;
import com.barani.travel.entity.Booking;
import com.barani.travel.enums.BookingStatus;
import com.barani.travel.service.EmailService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.exception.BookingNotFoundException;
import com.barani.travel.repository.BookingRepository;
import com.barani.travel.service.BookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.barani.travel.pdf.PdfService;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PdfService pdfService;
    private final ProviderClient providerClient;
    private final EmailService emailService;
    public BookingServiceImpl(BookingRepository bookingRepository, ProviderClient providerClient,EmailService emailService,PdfService pdfService) {
        this.bookingRepository = bookingRepository;
        this.providerClient = providerClient;
        this.emailService=emailService;
        this.pdfService=pdfService;
    }

    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);

    @CircuitBreaker(name = "providerService", fallbackMethod = "bookingFallback")
    @Override
    public BookingResponse createBooking(BookingRequest request) {

        // Step 1 - Get Price from Provider
        log.info("Booking request received for customer : {}", request.getCustomerName());
        PriceRequest priceRequest = new PriceRequest();
        priceRequest.setAdultCount(request.getAdultCount());
        priceRequest.setChildCount(request.getChildCount());

        PriceResponse priceResponse = providerClient.getPrice(priceRequest);
        log.info("Price calculated : {}", priceResponse.getTotalPrice());
        // Step 2 - Provider Booking

        BookingRequest providerRequest = new BookingRequest();

        providerRequest.setCustomerName(request.getCustomerName());
        providerRequest.setCustomerEmail(request.getCustomerEmail());
        providerRequest.setTravelDate(LocalDate.parse(request.getTravelDate().toString()));
        providerRequest.setTimeSlot(request.getTimeSlot());
        providerRequest.setAdultCount(request.getAdultCount());
        providerRequest.setChildCount(request.getChildCount());

        BookingResponse providerResponse = providerClient.createBooking(providerRequest);
        log.info("Provider Booking Id : {}", providerResponse.getProviderBookingId());
        // Step 3 - Save Booking

        Booking booking = new Booking();
        booking.setBookingReference("BK-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        booking.setProviderBookingId(providerResponse.getProviderBookingId());
        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerEmail(request.getCustomerEmail());
        booking.setAttractionCode(request.getAttractionCode());
        booking.setTravelDate(request.getTravelDate());
        booking.setAdultCount(request.getAdultCount());
        booking.setChildCount(request.getChildCount());
        booking.setTotalAmount(request.getTotalAmount());

        booking.setCurrency(request.getCurrency());
        booking.setTotalAmount(priceResponse.getTotalPrice());

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setProviderName("Adventure Park");

        booking.setCurrency(priceResponse.getCurrency());

        booking.setTimeSlot(request.getTimeSlot());
        bookingRepository.save(booking);
        log.info("Booking saved successfully : {}", booking.getBookingReference());
        String subject = "Booking Confirmation - " + booking.getBookingReference();

        String body = """
<html>

<body style="font-family:Arial;background:#f5f5f5;padding:20px">

<div style="max-width:650px;
background:white;
padding:30px;
border-radius:10px">

<h2 style="color:#1E88E5">
🎉 Booking Confirmed
</h2>

<p>Hello <b>%s</b>,</p>

<p>Your booking has been confirmed successfully.</p>

<table border="0" cellpadding="8">

<tr>
<td><b>Booking Reference</b></td>
<td>%s</td>
</tr>

<tr>
<td><b>Travel Date</b></td>
<td>%s</td>
</tr>

<tr>
<td><b>Time Slot</b></td>
<td>%s</td>
</tr>

<tr>
<td><b>Total Amount</b></td>
<td>%s %s</td>
</tr>

</table>

<br>

<p>📎 Your booking ticket is attached as a PDF.</p>

<hr>

<p style="color:gray">
Travel Booking Team

📧 support@travelbooking.com
🌐 www.travelbooking.com
📞 +91 8667600676
</p>

</div>

</body>

</html>
""".formatted(
                booking.getCustomerName(),
                booking.getBookingReference(),
                booking.getTravelDate(),
                booking.getTimeSlot(),
                booking.getCurrency(),
                booking.getTotalAmount());
        byte[] pdf = pdfService.generateBookingPdf(booking);
        log.info("Sending confirmation email to {}", booking.getCustomerEmail());
        try {
            emailService.sendBookingConfirmation(booking.getCustomerEmail(), subject, body, pdf,
                    "Booking-" + booking.getBookingReference() + ".pdf");
            log.info("Email sent successfully");
        } catch (Exception e) {
            log.error("Mail sending failed", e);
        }
        // Step 4 - Return Response

        BookingResponse response = new BookingResponse();

        response.setBookingReference(booking.getBookingReference());
        response.setProviderBookingId(booking.getProviderBookingId());
        response.setBookingStatus(booking.getBookingStatus().name());
        response.setTotalAmount(booking.getTotalAmount());
        response.setMessage(providerResponse.getMessage());
        response.setProviderName(booking.getProviderName());
        response.setBookingDate(booking.getCreatedDate());
        response.setTravelDate(booking.getTravelDate());
        response.setTimeSlot(booking.getTimeSlot());
        response.setCurrency(booking.getCurrency());
        response.setAdultCount(booking.getAdultCount());
        response.setChildCount(booking.getChildCount());
        return response;
    }

    public BookingResponse bookingFallback(BookingRequest request, Exception ex) {

        BookingResponse response = new BookingResponse();

        response.setBookingStatus("FAILED");
        response.setMessage("Provider Service is temporarily unavailable. Please try again later.");

        return response;
    }

    @Override
    public BookingResponse getBooking(String bookingReference) {

        Booking booking = bookingRepository.findByBookingReference(bookingReference).orElseThrow(() ->
                        new BookingNotFoundException("Booking not found : " + bookingReference));

        BookingResponse response = new BookingResponse();

        response.setBookingReference(booking.getBookingReference());

        response.setProviderBookingId(booking.getProviderBookingId());

        response.setProviderName(booking.getProviderName());

        response.setBookingStatus(booking.getBookingStatus().name());

        response.setBookingDate(booking.getCreatedDate());

        response.setTravelDate(booking.getTravelDate());

        response.setTimeSlot(booking.getTimeSlot());

        response.setCurrency(booking.getCurrency());

        response.setAdultCount(booking.getAdultCount());

        response.setChildCount(booking.getChildCount());

        response.setTotalAmount(booking.getTotalAmount());

        return response;
    }

    @Override
    public List<BookingResponse> getBookingsByCustomerEmail(String customerEmail) {

        return bookingRepository.findByCustomerEmail(customerEmail)
                .stream()
                .map(booking -> {

                    BookingResponse response = new BookingResponse();

                    response.setBookingReference(booking.getBookingReference());
                    response.setProviderBookingId(booking.getProviderBookingId());
                    response.setProviderName(booking.getProviderName());
                    response.setBookingStatus(booking.getBookingStatus().name());
                    response.setBookingDate(booking.getCreatedDate());
                    response.setTravelDate(booking.getTravelDate());
                    response.setTimeSlot(booking.getTimeSlot());
                    response.setCurrency(booking.getCurrency());
                    response.setAdultCount(booking.getAdultCount());
                    response.setChildCount(booking.getChildCount());
                    response.setTotalAmount(booking.getTotalAmount());

                    return response;
                })
                .toList();
    }
    @Override
    public BookingResponse cancelBooking(String bookingReference) {

        Booking booking = bookingRepository.findByBookingReference(bookingReference).orElseThrow(() ->
                        new BookingNotFoundException("Booking not found : " + bookingReference));

        booking.setBookingStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);

        BookingResponse response = new BookingResponse();

        response.setBookingReference(booking.getBookingReference());

        response.setProviderBookingId(booking.getProviderBookingId());

        response.setProviderName(booking.getProviderName());

        response.setBookingStatus(booking.getBookingStatus().name());

        response.setBookingDate(booking.getCreatedDate());

        response.setTravelDate(booking.getTravelDate());

        response.setTimeSlot(booking.getTimeSlot());

        response.setCurrency(booking.getCurrency());

        response.setAdultCount(booking.getAdultCount());

        response.setChildCount(booking.getChildCount());

        response.setTotalAmount(booking.getTotalAmount());

        response.setMessage("Booking Cancelled Successfully");

        return response;
    }
    @Override
    public Page<BookingResponse> getBookings(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return bookingRepository.findAll(pageable)
                .map(booking -> {
                    BookingResponse response = new BookingResponse();

                    response.setBookingReference(booking.getBookingReference());
                    response.setProviderBookingId(booking.getProviderBookingId());
                    response.setProviderName(booking.getProviderName());
                    response.setBookingStatus(booking.getBookingStatus().name());
                    response.setBookingDate(booking.getCreatedDate());
                    response.setTravelDate(booking.getTravelDate());
                    response.setTimeSlot(booking.getTimeSlot());
                    response.setCurrency(booking.getCurrency());
                    response.setAdultCount(booking.getAdultCount());
                    response.setChildCount(booking.getChildCount());
                    response.setTotalAmount(booking.getTotalAmount());

                    return response;
                });
    }
}