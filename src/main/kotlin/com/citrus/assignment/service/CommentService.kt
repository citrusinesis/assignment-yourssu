package com.citrus.assignment.service

import com.citrus.assignment.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService(
    @Autowired var commentRepository: CommentRepository
) {
    //TODO: Implement Create Service
    fun create() {}

    //TODO: Implement Modify Service
    fun modify() {}

    //TODO: Implement Delete Service
    fun delete() {}
}