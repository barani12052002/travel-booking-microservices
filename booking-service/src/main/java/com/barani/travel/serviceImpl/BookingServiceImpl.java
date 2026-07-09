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
        providerRequest.setAttractionName(request.getAttractionName());

        BookingResponse providerResponse = providerClient.createBooking(providerRequest);
        log.info("Provider Booking Id : {}", providerResponse.getProviderBookingId());
        // Step 3 - Save Booking

        Booking booking = new Booking();
        booking.setBookingReference("BK-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        booking.setProviderBookingId(providerResponse.getProviderBookingId());
        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerEmail(request.getCustomerEmail());
        booking.setAttractionCode(request.getAttractionCode());
        booking.setAttractionName(request.getAttractionName());
        booking.setTravelDate(request.getTravelDate());
        booking.setAdultCount(request.getAdultCount());
        booking.setChildCount(request.getChildCount());
        booking.setTotalAmount(request.getTotalAmount());

        booking.setCurrency(request.getCurrency());
        booking.setTotalAmount(priceResponse.getTotalPrice());

        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setProviderName("Dream Destination Tours");

        booking.setCurrency(priceResponse.getCurrency());

        booking.setTimeSlot(request.getTimeSlot());
        bookingRepository.save(booking);
        log.info("Booking saved successfully : {}", booking.getBookingReference());
        String subject = "Booking Confirmation - " + booking.getBookingReference();

        String body = """
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body style="margin:0;padding:0;background-color:#eef1f5;font-family:'Segoe UI',Helvetica,Arial,sans-serif;">

<div style="max-width:600px;margin:40px auto;background:#ffffff;border-radius:16px;overflow:hidden;box-shadow:0 4px 24px rgba(0,0,0,0.08);">

  <!-- Header -->
  <div style="background:linear-gradient(135deg,#667eea 0%%,#764ba2 100%%);padding:40px 32px;text-align:center;">
    <div style="font-size:15px;letter-spacing:2px;color:rgba(255,255,255,0.85);text-transform:uppercase;margin-bottom:8px;">Dream Destination Tours</div>
    <div style="font-size:26px;font-weight:700;color:#ffffff;">Booking Confirmed 🎉</div>
  </div>

  <!-- Body -->
  <div style="padding:36px 32px;">

    <p style="font-size:15px;color:#333333;margin:0 0 8px;">Hello <b>%s</b>,</p>
    <p style="font-size:14px;color:#666666;line-height:1.6;margin:0 0 28px;">
      Great news — your booking is confirmed. Here are your trip details below.
    </p>

    <!-- Reference badge -->
    <div style="background:#f0f4ff;border-radius:10px;padding:16px 20px;margin-bottom:24px;">
      <div style="font-size:11px;color:#667eea;text-transform:uppercase;letter-spacing:0.5px;font-weight:600;margin-bottom:4px;">Booking Reference</div>
      <div style="font-size:20px;color:#1a1a2e;font-weight:700;">%s</div>
    </div>

    <!-- Details table -->
    <table width="100%%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;margin-bottom:28px;">
      <tr>
        <td style="padding:12px 0;border-bottom:1px solid #f0f0f0;font-size:13px;color:#999999;">Travel Date</td>
        <td style="padding:12px 0;border-bottom:1px solid #f0f0f0;font-size:14px;color:#1a1a2e;font-weight:600;text-align:right;">%s</td>
      </tr>
      <tr>
        <td style="padding:12px 0;border-bottom:1px solid #f0f0f0;font-size:13px;color:#999999;">Time Slot</td>
        <td style="padding:12px 0;border-bottom:1px solid #f0f0f0;font-size:14px;color:#1a1a2e;font-weight:600;text-align:right;">%s</td>
      </tr>
      <tr>
        <td style="padding:12px 0;font-size:13px;color:#999999;">Total Amount</td>
        <td style="padding:12px 0;font-size:18px;color:#667eea;font-weight:700;text-align:right;">%s %s</td>
      </tr>
    </table>

    <div style="background:#fafafa;border-radius:10px;padding:16px 20px;display:flex;align-items:center;gap:10px;">
      <span style="font-size:20px;">📎</span>
      <span style="font-size:13px;color:#666666;">Your e-ticket is attached to this email as a PDF.</span>
    </div>

  </div>

  <!-- Footer -->
  <div style="background:#1a1a2e;padding:28px 32px;text-align:center;">
    <div style="font-size:14px;color:#ffffff;font-weight:600;margin-bottom:10px;">Dream Destination Tours</div>
    <div style="font-size:12px;color:rgba(255,255,255,0.6);line-height:1.8;">
      📧 support@travelbooking.com &nbsp;|&nbsp; 🌐 www.travelbooking.com &nbsp;|&nbsp; 📞 +91 8667600676
    </div>
  </div>

</div>

</body>
</html>
""".formatted(booking.getCustomerName(), booking.getBookingReference(), booking.getTravelDate(),
                booking.getTimeSlot(), booking.getCurrency(), booking.getTotalAmount());

        try {
            byte[] pdf = pdfService.generateBookingPdf(booking);

            emailService.sendBookingConfirmation(booking.getCustomerEmail(), subject, body, pdf,
                    "Booking-" + booking.getBookingReference() + ".pdf");

            log.info("Email sent successfully");

        } catch (Exception e) {
            log.error("PDF or Email failed", e);
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
        response.setAttractionName(booking.getAttractionName());
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

        response.setAttractionName(booking.getAttractionName());

        return response;
    }

    @Override
    public List<BookingResponse> getBookingsByCustomerEmail(String customerEmail) {

        return bookingRepository.findByCustomerEmail(customerEmail)
                .stream().map(booking -> {

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
                    response.setAttractionName(booking.getAttractionName());

                    return response;
                }).toList();
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

        response.setAttractionName(booking.getAttractionName());

        response.setMessage("Booking Cancelled Successfully");

        return response;
    }

    @Override
    public Page<BookingResponse> getBookings(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return bookingRepository.findAll(pageable).map(booking -> {
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
                    response.setAttractionName(booking.getAttractionName());

                    return response;
                });
    }

    @Override
    public List<BookingResponse> searchBookings(String keyword) {
        return List.of();
    }

    @Override
    public List<BookingResponse> filterBookings(BookingStatus status) {
        return List.of();
    }

    @Override
    public List<BookingResponse> searchAndFilterBookings(String keyword, BookingStatus status) {
        return List.of();
    }
}