package com.barani.travel.pdf;

import com.barani.travel.entity.Booking;
import com.barani.travel.qrcode.QrCodeService;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayOutputStream;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.element.Cell;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.kernel.colors.ColorConstants;
import org.springframework.stereotype.Service;

@Service
public class PdfServiceImpl implements PdfService {

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

            Document document = new Document(pdf);

            document.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy");

            DecimalFormat amountFormatter = new DecimalFormat("#,##0.00");

            InputStream inputStream = getClass().getResourceAsStream("/images/logo.png");

            if (inputStream != null) {
                byte[] imageBytes = inputStream.readAllBytes();

                Image logo = new Image(ImageDataFactory.create(imageBytes));

                logo.setWidth(140);
                logo.setAutoScale(true);
                logo.setHorizontalAlignment(
                        com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

                document.add(logo);
            } else {
                System.out.println("Logo not found!");
            }

            Table header = new Table(1).useAllAvailableWidth();

            header.addCell(
                    new Cell()
                            .setBackgroundColor(ColorConstants.BLUE)
                            .setTextAlignment(TextAlignment.CENTER)
                            .add(new Paragraph("TRAVEL BOOKING TICKET")
                                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                                    .setFontColor(ColorConstants.WHITE)
                                    .setFontSize(22)));

            document.add(header);

            document.add(new Paragraph(" "));
            document.add(new LineSeparator(new SolidLine()));
            Table table = new Table(UnitValue.createPercentArray(new float[]{3,5}))
                    .useAllAvailableWidth();

            Cell labelCell = new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Booking Reference")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)));

            table.addCell(labelCell);

            table.addCell(new Cell().add(new Paragraph(booking.getBookingReference())));

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Customer")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(booking.getCustomerName());

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Provider")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(booking.getProviderName());

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Travel Date")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(booking.getTravelDate().format(dateFormatter));

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Time Slot")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(booking.getTimeSlot());

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Status")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            Paragraph status = new Paragraph(booking.getBookingStatus().name())
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD));

            if ("CONFIRMED".equals(booking.getBookingStatus().name())) {
                status.setFontColor(ColorConstants.GREEN);
            } else if ("CANCELLED".equals(booking.getBookingStatus().name())) {
                status.setFontColor(ColorConstants.RED);
            }

            table.addCell(new Cell().add(status));
            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Passengers")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(
                    "Adults : " + booking.getAdultCount() +
                            "   Children : " + booking.getChildCount());

            table.addCell(new Cell()
                    .setBackgroundColor(ColorConstants.LIGHT_GRAY)
                    .add(new Paragraph("Amount")
                            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))));
            table.addCell(booking.getCurrency() + " " +
                            amountFormatter.format(booking.getTotalAmount()));

            document.add(table);
            byte[] qrBytes = qrCodeService.generateQrCode(booking.getBookingReference());

            Image qrImage = new Image(ImageDataFactory.create(qrBytes));

            qrImage.setWidth(120);
            qrImage.setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.CENTER);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("SCAN TO VERIFY")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(qrImage);

            document.add(new Paragraph(booking.getBookingReference())
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Present this ticket at the entrance.")
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph(" "));
            document.add(new LineSeparator(new SolidLine()));
            document.add(new Paragraph("\n"));

            document.add(new LineSeparator(new SolidLine()));

            document.add(new Paragraph("Thank you for choosing Travel Booking")
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("Please carry this ticket during your visit.")
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("support@travelbooking.com")
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("www.travelbooking.com")
                    .setFontSize(10)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER));

            String generatedDate = java.time.LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a"));

            document.add(new Paragraph("Generated On : " + generatedDate)
                    .setFontSize(9)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER));



            document.close();

            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}