package com.example.sellerService.service

import com.example.sellerService.model.Seller
import org.bson.types.ObjectId

interface ISellerService {
    fun addSeller(seller: Seller):Any?
    fun deleteSeller(email:String):Any?
    fun updateSeller(email: String,seller: Seller):Any?
    fun getSeller(email: String):Any?
}