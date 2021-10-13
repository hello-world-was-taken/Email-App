package com.nodamin.emailapp.model;

import java.util.Date;

public class EmailContent {

    private final String from;
    private final String subject;
    private final Date dateSent;

    public EmailContent(String from, String subject, Date dateSent) {
        this.from = from;
        this.subject = subject;
        this.dateSent = dateSent;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public Date getDateSent() {
        return dateSent;
    }
}
