package com.example.sellerService

import com.example.sellerService.dao.ISellerRepository
import com.example.sellerService.model.Seller
import com.example.sellerService.model.SellerResponse
import com.example.sellerService.service.ISellerService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class SellerServiceApplicationTests {

    @Autowired
    private lateinit var iSellerService:ISellerService

    @MockBean
    private lateinit var iSellerRepository: ISellerRepository

    @Test
    fun addUserTest(){
        val user:Seller = Seller(ObjectId("61ee597e731a3b6965939a02"),"seller","account","test@gmail.com","test123","seller","23AEGHDJKL23DFS",null)
        Mockito.`when`(iSellerRepository.save(user)).thenReturn(user)
        Mockito.`when`(iSellerRepository.findByEmail(user.email)).thenReturn(user)
        Assertions.assertEquals("user already have account with this mail id ",iSellerService.addSeller(user))
    }

    @Test
    fun getUserByEmailTest(){
        val user:Seller = Seller(ObjectId("61ee597e731a3b6965939a02"),"seller","account","test@gmail.com","test123","seller","23AEGHDJKL23DFS",null)
        Mockito.`when`(iSellerRepository.save(user)).thenReturn(user)
        Mockito.`when`(iSellerRepository.findByEmail(user.email)).thenReturn(user)
        Assertions.assertEquals(SellerResponse(user._id.toString(),user.firstName,user.lastName,user.email,user.role.substringAfter("_"),user.gstNo,
            user.productAdded),iSellerService.getSeller(user.email))
    }

    @Test
    fun updateUserTest(){
        val user:Seller = Seller(ObjectId("61ee597e731a3b6965939a02"),"seller","account","test@gmail.com","test123","seller","23AEGHDJKL23DFS",null)
        val userUpdate:Seller = Seller(ObjectId("61ee597e731a3b6965939a02"),"seller1","account","test@gmail.com","test123","seller","23AEGHDJKL23DFS",null)
        Mockito.`when`(iSellerRepository.save(user)).thenReturn(user)
        Mockito.`when`(iSellerRepository.findByEmail(user.email)).thenReturn(user)
        Assertions.assertEquals("user updated successfully",iSellerService.updateSeller(user.email,userUpdate))
    }

    @Test
    fun deleteUserTest(){
        val user:Seller = Seller(ObjectId("61ee597e731a3b6965939a02"),"seller","account","test@gmail.com","test123","seller","23AEGHDJKL23DFS",null)
        Mockito.`when`(iSellerRepository.save(user)).thenReturn(user)
        Mockito.`when`(iSellerRepository.findByEmail(user.email)).thenReturn(user)
        Assertions.assertEquals("user deleted successfully",iSellerService.deleteSeller(user.email))
    }

}
