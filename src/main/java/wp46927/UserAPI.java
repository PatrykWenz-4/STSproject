package wp46927;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RequestMapping("/api/users")
public interface UserAPI {

    @PostMapping("/add")
    @Tag(name = "user", description = "User API")
    @Operation(
        summary = "Add new user",
        description = "Adds a new user to the database.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User object to be added. Default values: name=4444, email=string@str.com.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(
                    example = "{ \"name\": \"4444\", \"email\": \"string@str.com\" }"
                )
            )
        )
    )
    @ApiResponses(value = {
            @ApiResponse(description = "User added successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            }),
            @ApiResponse(responseCode = "403", description = "Error adding user.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
    })
    ResponseEntity<Object> addUser(@RequestBody User user);



    @DeleteMapping("/delete/{name}")
    @Tag(name = "user", description = "User API")
    @Operation(summary = "Delete a user", description = "Deletes a user from the database by name.")
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            }),
            @ApiResponse(responseCode = "403", description = "Error removing user.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
    })
    ResponseEntity<Object> deleteUser(
            @PathVariable
            @Parameter(description = "Name of the user to be deleted.", required = true)
            String name
    );

    @GetMapping("/get/{name}")
    @Tag(name = "user", description = "User API")
    @Operation(summary = "Get user info", description = "Returns user info from the database.")
    @ApiResponses(value = {
            @ApiResponse(description = "User information.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
            }),
            @ApiResponse(responseCode = "403", description = "Error getting user info.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
    })
    ResponseEntity<Object> getUser(
            @PathVariable
            @Parameter(description = "Name of the user to get info.", required = true)
            String name
    );

    @PatchMapping("/update/{name}")
    @Tag(name = "user", description = "User API")
    @Operation(summary = "Modify user info", description = "Updates user info in the database.")
    @ApiResponses(value = {
            @ApiResponse(description = "User modified successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Result.class))
            }),
            @ApiResponse(responseCode = "403", description = "Error modifying user.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))
            }),
    })
    ResponseEntity<Object> updateUser(
            @PathVariable
            @Parameter(description = "Name of the user to be updated.", required = true)
            String name, @RequestBody User user);
}
