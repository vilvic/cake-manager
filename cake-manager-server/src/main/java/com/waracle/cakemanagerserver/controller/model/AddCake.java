package com.waracle.cakemanagerserver.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Add cake model.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class AddCake {

    @Schema(description = "Title", example = "Madeira")
    @NotBlank(message = "Title is required")
    @Size(message = "Maximum length of title is 100", max = 100, min = 1)
    private String title;

    @Schema(description = "Description", example = "The cake has a firm yet light texture. It is eaten with tea or (occasionally) for breakfast and is traditionally flavoured with lemon")
    @NotBlank(message = "Description is required")
    @Size(message = "Maximum length of description is 500", max = 500, min = 1)
    private String description;

    @Schema(description = "Image url", example = "http://")
    @NotBlank(message = "Image url is required")
    @Size(message = "Maximum length of image url is 300", max = 300, min = 1)
    private String imageUrl;

}
