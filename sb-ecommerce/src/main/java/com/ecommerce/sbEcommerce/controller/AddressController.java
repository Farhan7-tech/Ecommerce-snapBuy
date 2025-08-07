package com.ecommerce.sbEcommerce.controller;

import com.ecommerce.sbEcommerce.model.User;
import com.ecommerce.sbEcommerce.payload.AddressDTO;
import com.ecommerce.sbEcommerce.service.AddressService;
import com.ecommerce.sbEcommerce.utils.AuthUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AuthUtil authUtil;

    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user = authUtil.loggedInUser();
        AddressDTO addressDTOs = addressService.createAddress(addressDTO, user);
        return new ResponseEntity<>(addressDTOs, HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAllAddresses() {
        List<AddressDTO> addressDTOList = addressService.getAllAddresses();
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    @GetMapping("/addresses/{AddressId}")
    public ResponseEntity<AddressDTO> getAddressbyId(@PathVariable Long AddressId) {
        AddressDTO addressDTO = addressService.getAddressbyId(AddressId);
        return new ResponseEntity<>(addressDTO, HttpStatus.OK);
    }

    @GetMapping("/users/addresses")
    public ResponseEntity<List<AddressDTO>> getUserAddresses() {
        User loggedInUser = authUtil.loggedInUser();
        List<AddressDTO> addressDTOList = addressService.getUserAddresses(loggedInUser);
        return new ResponseEntity<>(addressDTOList, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddressById(@PathVariable Long addressId, @Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO  updatedAddressDTO = addressService.updateAddress(addressId ,addressDTO);
        return new ResponseEntity<>(updatedAddressDTO, HttpStatus.OK);
    }

    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long addressId) {
        String  Addresstobedeleted = addressService.delteAddressById(addressId);
        return new ResponseEntity<>(Addresstobedeleted, HttpStatus.OK);
    }
}
