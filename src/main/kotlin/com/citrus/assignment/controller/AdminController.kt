package com.citrus.assignment.controller

import com.citrus.assignment.service.AdminService
import com.citrus.assignment.transfer.admin.ShowRequest
import com.citrus.assignment.transfer.admin.ShowResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/show")
class AdminController(
    @Autowired val adminService: AdminService
) {
    @GetMapping
    fun show(showRequest: ShowRequest): List<ShowResponse> = adminService.showUser(showRequest)
}
