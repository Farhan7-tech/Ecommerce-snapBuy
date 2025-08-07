package com.ecommerce.sbEcommerce.service;

import com.ecommerce.sbEcommerce.Exceptions.ResourseNotFoundException;
import com.ecommerce.sbEcommerce.Repository.AddressRepository;
import com.ecommerce.sbEcommerce.Repository.UserRepository;
import com.ecommerce.sbEcommerce.model.Address;
import com.ecommerce.sbEcommerce.model.User;
import com.ecommerce.sbEcommerce.payload.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public AddressDTO createAddress(AddressDTO addressDTO , User user) {
        Address address = modelMapper.map(addressDTO, Address.class);
        List<Address> addressList = user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        return  addressRepository.findAll().stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO getAddressbyId(Long addressId) {
        return addressRepository.findById(addressId).map(address -> modelMapper.map(address, AddressDTO.class)).orElseThrow(() -> new ResourseNotFoundException("Address" , "AddressId" , addressId));
    }

    @Override
    public List<AddressDTO> getUserAddresses(User loggedInUser) {
        return loggedInUser.getAddresses().stream().map(address -> modelMapper.map(address, AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Address addressFromDB = addressRepository.findById(addressId).orElseThrow(() -> new ResourseNotFoundException("Address" , "AddressId" , addressId));
        addressFromDB.setStreet(addressDTO.getStreet());
        addressFromDB.setCity(addressDTO.getCity());
        addressFromDB.setState(addressDTO.getState());
        addressFromDB.setBuildingName(addressDTO.getBuildingName());
        addressFromDB.setCountry(addressDTO.getCountry());
        addressFromDB.setPincode(addressDTO.getPincode());

        Address updatedAddress = addressRepository.save(addressFromDB);

        User user = addressFromDB.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(updatedAddress);

        userRepository.save(user);

        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String delteAddressById(Long addressId) {
        Address addressFromDB = addressRepository.findById(addressId).orElseThrow(() -> new ResourseNotFoundException("Address" , "AddressId" , addressId));
        User user = addressFromDB.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDB);

        return "address deleted successfully with id " + addressId;
    }
}
