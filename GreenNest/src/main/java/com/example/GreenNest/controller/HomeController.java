package com.example.GreenNest.controller;

import com.example.GreenNest.exception.ResourceNotFoundException;
import com.example.GreenNest.model.*;
import com.example.GreenNest.repository.CustomerRepository;
import com.example.GreenNest.repository.EmployeeRepository;
import com.example.GreenNest.repository.UserProfileRepository;
import com.example.GreenNest.security.Encryption;
import com.example.GreenNest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
//@CrossOrigin(origins = "https://localhost:3000")
@CrossOrigin("*")
@RequestMapping("/api/v1")
public class HomeController {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    @Autowired
    Encryption encryption;

    IvParameterSpec ivParameterSpec = encryption.generateIv();
    SecretKey key = encryption.generateKey(128);
    String algorithm = "AES/CBC/PKCS5Padding";

    public HomeController() throws NoSuchAlgorithmException {
    }


    @GetMapping("/user")
    public String home(){
        return ("<h1> hello </h1>");
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

    //add customer
    @PostMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertCustomer(@RequestBody Customer customer){
        if(customer == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }
        else{
            String hashPassword = userService.doHash(customer.getProfile().getPassword());

            customer.getProfile().setPassword(hashPassword);

            System.out.println(customer.getProfile().getPassword());

            List<String> username = userProfileRepository.getProfile(customer.getProfile().getEmail());

            if(username.isEmpty()){
                //customer.getProfile().setPassword(customer.getProfile().getPassword().hashCode());
                customerRepository.save(customer);
                return true;
            }else{
                System.out.println("already have an account");
                return false;
            }
        }

    }
    //add employee
    @PostMapping(value = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean insertEmployee(@RequestBody Employee employee){
        if (employee == null){
            throw new ResourceNotFoundException("Missing Data Exception");
        }else{
            String hashPassword = userService.doHash(employee.getUserProfile().getPassword());

            employee.getUserProfile().setPassword(hashPassword);

            System.out.println(employee.getUserProfile().getPassword());

            List<String> username = userProfileRepository.getProfile(employee.getUserProfile().getEmail());
            System.out.println(username);

            if(username.isEmpty()){
                //customer.getProfile().setPassword(bcryptEncoder.encode(customer.getProfile().getPassword()));
                employeeRepository.save(employee);
                return true;
            }else{
                System.out.println("already have an account");
                return false;
            }
        }
    }

    //login user to the system
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<String> loginCustomer(@RequestBody LoginModel loginModel){
        List<String> message = new ArrayList<String>();
        System.out.println(loginModel.getPassword());
        String hashPassword = userService.doHash(loginModel.getPassword());
        loginModel.setPassword(hashPassword);
        System.out.println(loginModel.getPassword());

        UserProfile userProfile = userProfileRepository.findByEmail(loginModel.getUsername());

        if(userProfile == null){
            message.add("username not found");
            return message;
        }else{
            if(loginModel.getPassword().equals(userProfile.getPassword())){
                userProfileRepository.updateLoginDetailsWithUsername(0, loginModel.getUsername());
                String role = userProfile.getRole();
                message.add(role);
                String id = String.valueOf(userProfile.getUser_id());
                message.add(id);
                return message;
            }
            else{
                message.add("incorrect password");
                return message;
            }
        }

    }

    //get session key
    @PostMapping("/sessionKey")
    public String getSessionKey (@RequestBody SessionDetails sessionDetails) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String cipherText = encryption.encrypt(algorithm, sessionDetails.getUserId(), key, ivParameterSpec);
        System.out.println(cipherText);
        return cipherText;
    }

    public String decryptUserIdFunc(String encryptedUserId, SecretKey key) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        try {
            String plainText = encryption.decrypt(algorithm, encryptedUserId, key, ivParameterSpec);
            System.out.println(plainText);
            return plainText;
        } catch (NoSuchPaddingException e) {
            return e.getMessage();
        } catch (NoSuchAlgorithmException e) {
            return e.getMessage();
        } catch (InvalidAlgorithmParameterException e) {
            return e.getMessage();
        } catch (InvalidKeyException e) {
            return e.getMessage();
        } catch (BadPaddingException e) {
            return e.getMessage();
        } catch (IllegalBlockSizeException e) {
            return e.getMessage();
        }

    }

    // check user id
    @PostMapping("/checkLoginState")
    public LoginState encryptUserId(@RequestBody CipherText cipherText){
        try{
            System.out.println(cipherText.getCipher());
            int userid = Integer.parseInt(decryptUserIdFunc(cipherText.getCipher(), key));
            System.out.println(userid);

            List<String> userState = userProfileRepository.checkLoginStatusOnUser(userid);
            LoginState loginState = new LoginState(Integer.parseInt(userState.get(0)), userid);
            return  loginState;

        } catch (Exception e){
            return new LoginState();
        }
    }

    //logout
    @PostMapping("/logout")
    public int logout(@RequestBody CipherText cipherText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        int userId = Integer.parseInt(decryptUserIdFunc(cipherText.getCipher(), key));
        int responset = userProfileRepository.updateLoginDetails(1, userId);
        return responset;

    }

}
