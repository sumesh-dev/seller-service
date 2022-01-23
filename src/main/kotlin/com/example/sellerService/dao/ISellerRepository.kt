package com.example.sellerService.dao

import com.example.sellerService.model.Seller
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface ISellerRepository: MongoRepository<Seller,ObjectId>{

    @Query("{email:?0}")
    fun findByEmail(email: String?): Seller?
}