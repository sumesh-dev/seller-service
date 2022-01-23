package com.example.sellerService.controller

import com.example.sellerService.config.JwtRequestFilter
import com.example.sellerService.dao.ISellerRepository
import com.example.sellerService.helper.JwtTokenUtil
import com.example.sellerService.model.JwtRequest
import com.example.sellerService.model.Seller
import com.example.sellerService.service.CustomUserDetailsService
import com.example.sellerService.service.ISellerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/seller")
class SellerController {
    @Autowired
    private lateinit var iSellerService: ISellerService

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var  jwtTokenUtil: JwtTokenUtil

    @Autowired
    private lateinit var customUserDetailsService: CustomUserDetailsService

    @Autowired
    private lateinit var jwtRequestFilter: JwtRequestFilter

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody seller: Seller):ResponseEntity<Any>{
//        println("seller signup")
      return ResponseEntity<Any>(iSellerService.addSeller(seller), HttpStatus.OK)
    }

    @PostMapping("/signin")
    @ResponseBody
    fun signIn(@Valid @RequestBody jwtRequest: JwtRequest, response: HttpServletResponse):ResponseEntity<MutableMap<String,String>>{
        val data: MutableMap<String, String> = mutableMapOf()
        try{
            this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(jwtRequest.email,jwtRequest.password))
        }
        catch(e: BadCredentialsException){
            data["msg"] = "Invalid Crendentials"
            return ResponseEntity<MutableMap<String,String>>(data,HttpStatus.BAD_REQUEST)
        }
        val userDetails: UserDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.email)
        val token: String = this.jwtTokenUtil.generateToken(userDetails)!!
        val cookie = Cookie("JwtToken",token)
        cookie.maxAge = 60*1000
        response.addCookie(cookie)
        data["msg"] = "Login successful"
        data["JwtToken"] = token
        return ResponseEntity<MutableMap<String,String>>(data,HttpStatus.OK)
    }

    @GetMapping("/me")
    fun getSellerByEmail():ResponseEntity<Any>{
        return ResponseEntity<Any>(iSellerService.getSeller(jwtRequestFilter.email),HttpStatus.OK)
    }

    @PatchMapping("/")
    fun updateSeller(@Valid @RequestBody seller: Seller):ResponseEntity<Any>{
        return ResponseEntity<Any>(iSellerService.updateSeller(jwtRequestFilter.email,seller),HttpStatus.OK)
    }

    @DeleteMapping("/")
    fun deleteSellerByEmail():ResponseEntity<Any>{
        return ResponseEntity<Any>(iSellerService.deleteSeller(jwtRequestFilter.email),HttpStatus.OK)
    }
}