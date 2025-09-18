package org.clwco.captcha.resource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.clwco.captcha.dto.CaptchaResponse;
import org.clwco.captcha.dto.VerifyResponseDto;
import org.clwco.captcha.services.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Managed by clwco-captcha-engine
 * Create by Milad on 9/17/2025 11:32 PM
 */
@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
@Tag(name = "Captcha API", description = "Captcha endpoints")
@Slf4j
public class CaptchaResource {

    @GetMapping("/get")
    @Operation(summary = "Get captcha")
    public ResponseEntity<CaptchaResponse> get() {
        return ResponseEntity.ok(CaptchaService.getCaptcha());
    }

    @GetMapping("/verify")
    @Operation(summary = "Verify captcha")
    public ResponseEntity<VerifyResponseDto> verify(@RequestParam("key") String key, @RequestParam("code") String code) {
        return ResponseEntity.ok(VerifyResponseDto.builder()
                .result(CaptchaService.verifyCaptcha(code, key))
                .build());
    }
}