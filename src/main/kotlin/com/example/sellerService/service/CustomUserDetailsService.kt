package com.example.sellerService.service

import com.example.sellerService.dao.ISellerRepository
import com.example.sellerService.model.Seller
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService : UserDetailsService {

    @Autowired
    private lateinit var iSellerRepository: ISellerRepository

    override fun loadUserByUsername(email: String): UserDetails {
        val user: Seller = iSellerRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("seller not found : $email")
        val list = mutableListOf(SimpleGrantedAuthority(user.role))
        return User(user.email, user.password,list)
    }
}