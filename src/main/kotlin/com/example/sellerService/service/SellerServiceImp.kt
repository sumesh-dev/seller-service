package com.example.sellerService.service

import com.example.sellerService.dao.ISellerRepository
import com.example.sellerService.model.Seller
import com.example.sellerService.model.SellerResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SellerServiceImp:ISellerService {

    @Autowired
    private lateinit var iSellerRepository: ISellerRepository

    override fun addSeller(seller: Seller): Any {

        return if (iSellerRepository.findByEmail(seller.email)==null) {
            iSellerRepository.save(Seller(seller.firstName,seller.lastName,seller.email,seller.password,"seller",seller.gstNo,null))
            "user created successfully"
        } else
            "user already have account with this mail id "
    }

    override fun deleteSeller(email: String): Any {
        val seller: Seller? = iSellerRepository.findByEmail(email)
        return if (seller!==null) {
            iSellerRepository.deleteById(seller._id)
            "user deleted successfully"
        } else{
            "user does not exist"
        }
    }

    override fun updateSeller(email: String,seller: Seller): Any? {
        val findseller:Seller? = iSellerRepository.findByEmail(email)
        return if (findseller!==null) {
            return if(findseller.email!=seller.email){
                "you cannot change email"
            }
            else{
                iSellerRepository.save(Seller(findseller._id,seller.firstName,seller.lastName,seller.email,seller.password,"seller",seller.gstNo,findseller.productAdded))
                "user updated successfully"
            }
        }
        else {
            "user does not exist"
        }
    }

    override fun getSeller(email: String): Any? {
        val seller:Seller? = iSellerRepository.findByEmail(email)
        return if(seller!=null){
            return SellerResponse(seller._id.toString(),seller.firstName,seller.lastName,seller.email,seller.role.substringAfter("_"),seller.gstNo,
                seller.productAdded)
        }
        else{
            "user does not exist"
        }
    }

}