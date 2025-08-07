package com.ecommerce.sbEcommerce.service;

import com.ecommerce.sbEcommerce.model.Address;
import com.ecommerce.sbEcommerce.model.User;
import com.ecommerce.sbEcommerce.payload.AddressDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO , User user);

    List<AddressDTO> getAllAddresses();

    AddressDTO getAddressbyId(Long addressId);

    List<AddressDTO> getUserAddresses(User loggedInUser);

    AddressDTO updateAddress( Long addressId, @Valid AddressDTO addressDTO);

    String delteAddressById(Long addressId);
}
