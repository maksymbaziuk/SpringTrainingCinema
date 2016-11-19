package com.baziuk.spring.booking.web.view;

import com.baziuk.spring.booking.bean.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Maks on 11/12/16.
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component("singleTicketPdfView")
// Extending AbstractPdfView sets content type to "application/pdf" by default
public class SingleTicketPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        // We are expecting single Ticket object
        Ticket ticket = (Ticket) model.get("ticket");

        document.add(new Paragraph("Thank you for your order. See your ticket below."));
        document.add(new Paragraph("Ticket id: " + ticket.getId()));
        document.add(new Paragraph("Film: " + ticket.getEvent().getName()));
        document.add(new Paragraph("Show time: " + ticket.getShow().getStart() + " till " + ticket.getShow().getEnd()));
        document.add(new Paragraph("Your sit number: " + ticket.getSitNumber()));
        document.add(new Paragraph("Price after discount: " + ticket.getPrice()));
    }
}
