package org.example.wms_be.utils;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String text) {

}
