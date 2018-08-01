package com.microservices.accounts.services;

import com.microservices.accounts.models.Address;
import com.microservices.accounts.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Iterable<Address> getAllAddresses(){
        return addressRepository.findAll();
    }

    public Optional<Address> findAddressById(Long id){
        return addressRepository.findById(id);
    }

    public Address createNewAddress(Address address){
        return addressRepository.save(address);
    }

    public Address updateAddress(Address address){
        Optional<Address> addressById = addressRepository.findById(address.getId());
        Address foundAddress = addressById.get();
        foundAddress.setAddressOne(address.getAddressOne());
        foundAddress.setAddressTwo(address.getAddressTwo());
        foundAddress.setCity(address.getCity());
        foundAddress.setZipCode(address.getZipCode());
        foundAddress.setCountry(address.getCountry());
        foundAddress.setState(address.getState());

        return addressRepository.save(foundAddress);
    }

    public void deleteAddress(Long id){
        addressRepository.deleteById(id);
    }
}
