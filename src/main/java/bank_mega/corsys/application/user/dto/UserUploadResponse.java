package bank_mega.corsys.application.user.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UserUploadResponse(
        int total,
        int success,
        int failed,
        List<String> errors
) {
}
