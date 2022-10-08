package com.citrus.assignment.transfer

class DeleteRequest(
    override var email: String,
    override var password: String
) : Request(email, password)