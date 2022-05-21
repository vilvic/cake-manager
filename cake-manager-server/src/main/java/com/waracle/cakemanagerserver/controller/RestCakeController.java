package com.waracle.cakemanagerserver.controller;

import com.waracle.cakemanagerserver.controller.aspect.MethodLogging;
import com.waracle.cakemanagerserver.controller.exception.RestNoContentException;
import com.waracle.cakemanagerserver.controller.model.Cake;
import com.waracle.cakemanagerserver.service.CakesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Cakes controller.
 */
@RestController
@RequestMapping("/rest/cakes")
public class RestCakeController {

    private final CakesService cakesService;

    /**
     * Cakes rest controller constructor.
     *
     * @param cakesService cake service
     */
    public RestCakeController(final CakesService cakesService) {
        Assert.notNull(cakesService, "cakesService cannot be null.");
        this.cakesService = cakesService;
    }

    /**
     * Get list of cakes.
     *
     * @return ResponseEntity with list of {@link Cake}
     */
    @Operation(summary = "Get list of cakes", description = "Get list of cakes", tags = {"cakes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cakes found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content)
    })
    @MethodLogging
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cake>> getCakes() {
        final var cakes = cakesService.getCakes();
        if (cakes.isEmpty()) {
            throw new RestNoContentException();
        } else {
            final List<Cake> restResponse = new ArrayList<>(cakes);
            return ResponseEntity.ok().body(restResponse);
        }
    }

}
