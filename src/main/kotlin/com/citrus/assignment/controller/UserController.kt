package com.citrus.assignment.controller

import com.citrus.assignment.service.UserService
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired var userService: UserService
) {
    @PostMapping("/create")
    fun create(@RequestBody user: UserRequest): UserResponse = userService.create(user)

    @PostMapping("/delete")
    fun delete(@RequestBody userInfo: DeleteRequest): HttpStatus = userService.delete(userInfo)
}