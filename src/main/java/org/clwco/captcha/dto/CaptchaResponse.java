package org.clwco.captcha.dto;

import lombok.Data;

@Data
public class CaptchaResponse {
    private String base64;
    private int captchaCode;
}
