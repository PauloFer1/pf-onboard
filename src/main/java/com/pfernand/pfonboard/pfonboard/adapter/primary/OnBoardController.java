package com.pfernand.pfonboard.pfonboard.adapter.primary;

import com.pfernand.pfonboard.pfonboard.core.OnBoardService;
import com.pfernand.pfonboard.pfonboard.core.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@AllArgsConstructor
public class OnBoardController {

    private final OnBoardService onBoardService;

    @ApiOperation("OnBoard a new User")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Exception"),
    })
    @RequestMapping(value = "/onboard", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody final User user) {
        log.info("POST /onboard with params {}", user.toString());
        return ResponseEntity.ok(onBoardService.onboardUser(user));

    }
}
