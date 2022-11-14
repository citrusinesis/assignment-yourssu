package com.citrus.assignment.controller

import com.citrus.assignment.security.Auth
import com.citrus.assignment.service.UserService
import com.citrus.assignment.transfer.DeleteRequest
import com.citrus.assignment.transfer.auth.AuthInfo
import com.citrus.assignment.transfer.auth.TokenResponse
import com.citrus.assignment.transfer.user.LoginRequest
import com.citrus.assignment.transfer.user.LoginResponse
import com.citrus.assignment.transfer.user.UserRequest
import com.citrus.assignment.transfer.user.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class UserController(
    @Autowired var userService: UserService
) {
    @PostMapping("/create")
    fun create(@RequestBody user: UserRequest): UserResponse = userService.create(user)

    @PostMapping("/login")
    fun login(@RequestBody login: LoginRequest): LoginResponse = userService.login(login)

    @PostMapping("/delete")
    fun delete(@RequestBody userInfo: DeleteRequest): HttpStatus = userService.delete(userInfo)

    @PostMapping("/refresh")
    fun refresh(request: HttpServletRequest, @Auth authInfo: AuthInfo): TokenResponse =
        userService.refresh(request, authInfo)
}