package com.waracle.cakemanagerserver.controller;

import com.waracle.cakemanagerserver.controller.aspect.MethodLogging;
import com.waracle.cakemanagerserver.controller.exception.RestNoContentException;
import com.waracle.cakemanagerserver.controller.model.AddCake;
import com.waracle.cakemanagerserver.controller.model.Cake;
import com.waracle.cakemanagerserver.controller.model.Error;
import com.waracle.cakemanagerserver.controller.model.RestResponse;
import com.waracle.cakemanagerserver.service.CakesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Cakes controller.
 */
@RestController
@RequestMapping("/cakes")
public class UiCakeController {

    private final CakesService cakesService;

    /**
     * Cakes controller constructor.
     *
     * @param cakesService cake service
     */
    public UiCakeController(final CakesService cakesService) {
        Assert.notNull(cakesService, "cakesService cannot be null.");
        this.cakesService = cakesService;
    }

    /**
     * Get list of cakes.
     *
     * @return ResponseEntity with {@link RestResponse} containing a list of {@link Cake}
     */
    @Operation(summary = "Get list of cakes", description = "Get list of cakes", tags = {"cakes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cakes found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "204", description = "No content", content = @Content)
    })
    @MethodLogging
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<List<Cake>>> getCakes() {
        final var cakes = cakesService.getCakes();
        final RestResponse<List<Cake>> restResponse = new RestResponse<>();
        if (cakes.isEmpty()) {
            throw new RestNoContentException();
        } else {
            restResponse.setPayload(cakes);
            return ResponseEntity.ok().body(restResponse);
        }
    }

    /**
     * Add cake.
     *
     * @param addCake add cake model
     * @return ResponseEntity with {@link RestResponse} containing a {@link Cake}
     */
    @Operation(summary = "Add cake", description = "Add cake", tags = {"cakes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cake added", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Cake.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content)
    })
    @MethodLogging
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestResponse<Cake>> addCake(@Valid @RequestBody final AddCake addCake) {

        final var optionalCake = cakesService.getByTitle(addCake.getTitle());

        final RestResponse<Cake> restResponse = new RestResponse<>();
        if (optionalCake.isPresent()) {
            restResponse.getErrors().add(new Error("title", "Cake already exists"));
        } else {
            final var cake = cakesService.addCake(addCake);
            restResponse.setPayload(cake);
        }
        return ResponseEntity.ok().body(restResponse);

    }

    /**
     * Validator exception handler.
     *
     * @param methodArgumentNotValidException method argument not valid exception
     * @return ResponseEntity with {@link RestResponse} containing a list of {@link Error}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<Cake>> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {

        final RestResponse<Cake> restResponse = new RestResponse<>();

        final var errors = new ArrayList<Error>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) ->
                errors.add(new Error(((FieldError) error).getField(), error.getDefaultMessage()))
        );
        restResponse.getErrors().addAll(errors);

        return ResponseEntity.badRequest().body(restResponse);
    }

}
