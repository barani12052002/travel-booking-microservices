package com.barani.travel.pdf;

import com.barani.travel.entity.Booking;
import com.barani.travel.qrcode.QrCodeService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {

    // Brand palette — matches your Bootstrap gradient (#667eea -> #764ba2)
    private static final DeviceRgb BRAND_PRIMARY = new DeviceRgb(102, 126, 234);
    private static final DeviceRgb BRAND_DARK = new DeviceRgb(26, 26, 46);
    private static final DeviceRgb TEXT_MUTED = new DeviceRgb(120, 120, 120);
    private static final DeviceRgb ROW_ALT = new DeviceRgb(248, 249, 253);
    private static final DeviceRgb SUCCESS = new DeviceRgb(46, 160, 90);
    private static final DeviceRgb DANGER = new DeviceRgb(220, 53, 69);

    private final QrCodeService qrCodeService;

    public PdfServiceImpl(QrCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @Override
    public byte[] generateBookingPdf(Booking booking) {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(0, 36, 36, 36);

            // Fonts created once — reused everywhere instead of re-parsing per cell
            PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            document.setFont(regular);

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
            DecimalFormat amountFormatter = new DecimalFormat("#,##0.00");

            // ---------- HEADER BANNER ----------
            Table headerBanner = new Table(1).useAllAvailableWidth();
            Cell bannerCell = new Cell()
                    .setBackgroundColor(BRAND_PRIMARY)
                    .setBorder(Border.NO_BORDER)
                    .setPadding(28)
                    .setTextAlignment(TextAlignment.CENTER);

            InputStream logoStream = getClass().getResourceAsStream("/images/logo.png");
            if (logoStream != null) {
                byte[] imageBytes = logoStream.readAllBytes();
                Image logo = new Image(ImageDataFactory.create(imageBytes));
                logo.setWidth(110);
                logo.setAutoScale(true);
                logo.setHorizontalAlignment(HorizontalAlignment.CENTER);
                bannerCell.add(logo);
                bannerCell.add(new Paragraph(" ").setFontSize(4));
            }

            bannerCell.add(new Paragraph("E-TICKET")
                    .setFont(bold)
                    .setFontColor(ColorConstants.WHITE)
                    .setFontSize(26)
                    .setMultipliedLeading(1f)
                    .setTextAlignment(TextAlignment.CENTER));

            bannerCell.add(new Paragraph("Booking Confirmation")
                    .setFont(regular)
                    .setFontColor(new DeviceRgb(230, 230, 250))
                    .setFontSize(11)
                    .setTextAlignment(TextAlignment.CENTER));

            headerBanner.addCell(bannerCell);
            document.add(headerBanner);

            // ---------- BODY WRAPPER (side margins) ----------
            document.add(new Paragraph(" ").setFontSize(6));

            // Booking reference + status strip
            Table refStrip = new Table(UnitValue.createPercentArray(new float[]{6, 4}))
                    .useAllAvailableWidth();

            Cell refCell = new Cell()
                    .setBorder(Border.NO_BORDER)
                    .add(new Paragraph("BOOKING REFERENCE")
                            .setFont(regular).setFontSize(8.5f).setFontColor(TEXT_MUTED)
                            .setCharacterSpacing(0.5f))
                    .add(new Paragraph(booking.getBookingReference())
                            .setFont(bold).setFontSize(18).setFontColor(BRAND_DARK));

            boolean confirmed = "CONFIRMED".equals(booking.getBookingStatus().name());
            DeviceRgb statusColor = confirmed ? SUCCESS : DANGER;

            Cell statusCell = new Cell()
                    .setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .add(new Paragraph("STATUS")
                            .setFont(regular).setFontSize(8.5f).setFontColor(TEXT_MUTED)
                            .setCharacterSpacing(0.5f).setTextAlignment(TextAlignment.RIGHT))
                    .add(new Paragraph(booking.getBookingStatus().name())
                            .setFont(bold).setFontSize(14).setFontColor(statusColor)
                            .setTextAlignment(TextAlignment.RIGHT));

            refStrip.addCell(refCell);
            refStrip.addCell(statusCell);
            document.add(refStrip);

            document.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.SolidLine(0.75f))
                    .setStrokeColor(new DeviceRgb(230, 230, 235)));
            document.add(new Paragraph(" ").setFontSize(8));

            // ---------- DETAILS TABLE ----------
            Table table = new Table(UnitValue.createPercentArray(new float[]{4, 6}))
                    .useAllAvailableWidth();

            addDetailRow(table, "Customer", booking.getCustomerName(), bold, regular, true);
            addDetailRow(table, "Provider", booking.getProviderName(), bold, regular, false);
            addDetailRow(table, "Travel Date", booking.getTravelDate().format(dateFormatter), bold, regular, true);
            addDetailRow(table, "Time Slot", booking.getTimeSlot(), bold, regular, false);
            addDetailRow(table, "Passengers",
                    "Adults: " + booking.getAdultCount() + "    Children: " + booking.getChildCount(),
                    bold, regular, true);

            document.add(table);
            document.add(new Paragraph(" ").setFontSize(6));

            // ---------- AMOUNT HIGHLIGHT ----------
            Table amountBox = new Table(1).useAllAvailableWidth();
            amountBox.addCell(new Cell()
                    .setBackgroundColor(ROW_ALT)
                    .setBorder(new SolidBorder(new DeviceRgb(225, 230, 250), 1))
                    .setPadding(16)
                    .add(new Paragraph("TOTAL AMOUNT PAID")
                            .setFont(regular).setFontSize(9).setFontColor(TEXT_MUTED)
                            .setCharacterSpacing(0.5f))
                    .add(new Paragraph(booking.getCurrency() + " " + amountFormatter.format(booking.getTotalAmount()))
                            .setFont(bold).setFontSize(22).setFontColor(BRAND_PRIMARY)));
            document.add(amountBox);

            document.add(new Paragraph(" ").setFontSize(14));

            // ---------- QR VERIFICATION ----------
            byte[] qrBytes = qrCodeService.generateQrCode(booking.getBookingReference());
            Image qrImage = new Image(ImageDataFactory.create(qrBytes));
            qrImage.setWidth(105);
            qrImage.setHorizontalAlignment(HorizontalAlignment.CENTER);

            document.add(new Paragraph("SCAN TO VERIFY")
                    .setFont(bold).setFontSize(10).setFontColor(BRAND_DARK)
                    .setCharacterSpacing(1f)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(qrImage);
            document.add(new Paragraph(booking.getBookingReference())
                    .setFont(regular).setFontSize(9).setFontColor(TEXT_MUTED)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph(" ").setFontSize(10));
            document.add(new Paragraph("Present this ticket (digital or printed) at the entrance.")
                    .setFont(regular).setFontSize(9).setFontColor(TEXT_MUTED)
                    .setTextAlignment(TextAlignment.CENTER));

            // ---------- FOOTER ----------
            document.add(new Paragraph(" ").setFontSize(16));
            document.add(new LineSeparator(new com.itextpdf.kernel.pdf.canvas.draw.SolidLine(0.75f))
                    .setStrokeColor(new DeviceRgb(230, 230, 235)));
            document.add(new Paragraph(" ").setFontSize(8));

            document.add(new Paragraph("Thank you for choosing Dream Destination Tours")
                    .setFont(bold).setFontSize(11).setFontColor(BRAND_DARK)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("support@travelbooking.com   |   www.travelbooking.com")
                    .setFont(regular).setFontSize(9).setFontColor(TEXT_MUTED)
                    .setTextAlignment(TextAlignment.CENTER));

            String generatedDate = java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));

            document.add(new Paragraph("Generated on " + generatedDate)
                    .setFont(regular).setFontSize(8).setFontColor(TEXT_MUTED)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addDetailRow(Table table, String label, String value,
                              PdfFont bold, PdfFont regular, boolean shaded) {

        Cell labelCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(10)
                .setBackgroundColor(shaded ? ROW_ALT : ColorConstants.WHITE)
                .add(new Paragraph(label)
                        .setFont(regular).setFontSize(10).setFontColor(TEXT_MUTED));

        Cell valueCell = new Cell()
                .setBorder(Border.NO_BORDER)
                .setPadding(10)
                .setBackgroundColor(shaded ? ROW_ALT : ColorConstants.WHITE)
                .add(new Paragraph(value)
                        .setFont(bold).setFontSize(11).setFontColor(BRAND_DARK));

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}