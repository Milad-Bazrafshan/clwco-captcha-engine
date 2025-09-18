package org.clwco.captcha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Managed by clwco-captcha-engine
 * Create by Milad on 9/17/2025 11:58 PM
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyResponseDto {
    private boolean result;
}
