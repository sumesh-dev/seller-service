package com.example.sellerService.model

import org.bson.types.ObjectId

data class SellerResponse(
    var _id: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var role: String,
    var gstNo: String,
    var productAdded: MutableList<ObjectId>?
)
