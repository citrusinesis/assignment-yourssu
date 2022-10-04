package com.citrus.assignment.controller

import com.citrus.assignment.service.UserService
import com.citrus.assignment.transfer.user.CreateRequest
import com.citrus.assignment.transfer.user.CreateResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired var userService: UserService
) {
    @PostMapping("/create")
    fun create(@RequestBody user: CreateRequest): CreateResponse {
        return userService.create(user)
    }

    //TODO: Implement User Delete
    @PostMapping("/delete")
    fun delete(): String {
        return "200"
    }
}