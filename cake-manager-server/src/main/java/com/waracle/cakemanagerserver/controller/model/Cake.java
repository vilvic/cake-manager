package com.waracle.cakemanagerserver.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cake model.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public final class Cake {

    @Schema(description = "id", example = "592255af-feba-4cf2-b22c-4bb3abdbfac9")
    private String id;

    @Schema(description = "Title", example = "Madeira")
    private String title;

    @Schema(description = "Description", example = "The cake has a firm yet light texture. It is eaten with tea or (occasionally) for breakfast and is traditionally flavoured with lemon")
    private String description;

    @Schema(description = "Image url", example = "http://")
    private String imageUrl;

}
