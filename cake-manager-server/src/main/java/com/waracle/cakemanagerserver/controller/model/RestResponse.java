package com.waracle.cakemanagerserver.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest response.
 *
 * @param <T>
 */
@NoArgsConstructor
@Data
public final class RestResponse<T> {

    @Schema(description = "Payload")
    private T payload;

    @Schema(description = "Error code/messages", implementation = Error.class)
    private final List<Error> errors = new ArrayList<>();

}
