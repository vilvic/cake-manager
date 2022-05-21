package com.waracle.cakemanagerserver.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error model.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class Error {

    @Schema(description = "Field name", example = "title")
    private String field;

    @Schema(description = "Error message", example = "Title is required")
    private String message;

}
