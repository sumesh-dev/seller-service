package com.example.sellerService.model

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Document("Sellers")
class Seller {
    @Autowired
    private var bCryptPasswordEncoder = BCryptPasswordEncoder(10)

    @Id
    lateinit var _id: ObjectId

    @field:NotBlank(message = "first name is mandatory")
    lateinit var firstName:String

    @field:NotBlank(message = "last name is mandatory")
    lateinit var lastName:String

    @field:Email(message  = "Email id is mandatory")
    lateinit var email:String

    @field:NotBlank(message = "password is mandatory")
    lateinit var password:String

//    @field:NotBlank(message = "Role is mandatory")
    lateinit var role:String

    @field:NotBlank(message = "Gst No. is required")
    @field:Size(min = 15, message = "gst no is not valid")
    @field:Size(max = 15, message = "gst no is not valid")
    lateinit var gstNo:String

    var productAdded: MutableList<ObjectId>?= mutableListOf()

    constructor()

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }


    constructor(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        role: String,
        gstNo: String,
        productAdded: MutableList<ObjectId>?
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        if (password.length > 30){
            this.password = password
        }
        else {
            this.password = this.bCryptPasswordEncoder.encode(password)
        }
        if(!role.startsWith("ROLE")){
            this.role = "ROLE_"+role.lowercase()
        }
        else{
            this.role = role.lowercase()
        }
        this.gstNo = gstNo
        this.productAdded = productAdded
    }

    constructor(
        _id: ObjectId,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        role: String,
        gstNo: String,
        productAdded: MutableList<ObjectId>?
    ) {
        this._id = _id
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        if (password.length > 30){
            this.password = password
        }
        else {
            this.password = this.bCryptPasswordEncoder.encode(password)
        }
        if(!role.startsWith("ROLE")){
            this.role = "ROLE_"+role.lowercase()
        }
        else{
            this.role = role.lowercase()
        }
        this.gstNo = gstNo
        this.productAdded = productAdded
    }

}