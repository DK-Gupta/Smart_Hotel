package com.smart.hotel.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.smart.hotel.model.Bill;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;

public class BillPdfExporter {

    private final Bill bill;
    private final String customerName;

    public BillPdfExporter(Bill bill, String customerName) {
        this.bill = bill;
        this.customerName = customerName;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Title
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
        Paragraph title = new Paragraph("SmartHotel - Billing Summary", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);

        // Customer Info
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        document.add(new Paragraph("Customer Name: " + customerName, infoFont)); // ✅ name added
        document.add(new Paragraph("Customer Email: " + bill.getEmail(), infoFont));
        document.add(new Paragraph("Billing Date: " + bill.getBillDate(), infoFont));
        document.add(Chunk.NEWLINE);

        // Charges Table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        PdfPCell h1 = new PdfPCell(new Phrase("Charge Type"));
        h1.setBackgroundColor(Color.LIGHT_GRAY);
        h1.setPadding(10);
        table.addCell(h1);

        PdfPCell h2 = new PdfPCell(new Phrase("Amount (₹)"));
        h2.setBackgroundColor(Color.LIGHT_GRAY);
        h2.setPadding(10);
        table.addCell(h2);

        table.addCell("Room Charges");
        table.addCell(String.format("%.2f", bill.getRoomCost()));

        table.addCell("Food Charges");
        table.addCell(String.format("%.2f", bill.getFoodCost()));

        double serviceCost = bill.getTotalAmount() - bill.getRoomCost() - bill.getFoodCost();
        table.addCell("Other Services");
        table.addCell(String.format("%.2f", serviceCost));

        PdfPCell totalCell = new PdfPCell(new Phrase("Total"));
        totalCell.setBackgroundColor(new Color(230, 230, 250));
        totalCell.setPadding(10);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);

        PdfPCell totalValueCell = new PdfPCell(new Phrase("₹" + String.format("%.2f", bill.getTotalAmount())));
        totalValueCell.setBackgroundColor(new Color(230, 230, 250));
        totalValueCell.setPadding(10);
        totalValueCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(totalValueCell);

        document.add(table);

        document.add(Chunk.NEWLINE);
        Paragraph thankYou = new Paragraph("Thank you for choosing SmartHotel!", infoFont);
        thankYou.setAlignment(Element.ALIGN_CENTER);
        thankYou.setSpacingBefore(30f);
        document.add(thankYou);

        document.close();
    }
}
