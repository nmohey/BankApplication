package com.sprints.BankApplication.service;

import com.sprints.BankApplication.dto.CustomerDto;
import com.sprints.BankApplication.model.Customer;
import com.sprints.BankApplication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerDto createCustomer(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setPhone(customerDto.getPhone());
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDto result = new CustomerDto(savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getPhone());
        return result;
    }

    public List<CustomerDto> getAllCustomers(){
        return customerRepository.findAll().stream().map(customer -> {
            CustomerDto dto = new CustomerDto();
            dto.setName(customer.getName());
            dto.setEmail(customer.getEmail());
            dto.setPhone(customer.getPhone());
            return dto;
        }).collect(Collectors.toList());
    }

    public CustomerDto getCustomerById(Long id){
        return customerRepository.findById(id).map(customer -> {
            CustomerDto dto = new CustomerDto();
            dto.setName(customer.getName());
            dto.setEmail(customer.getEmail());
            dto.setPhone(customer.getPhone());
            return dto;
        }).orElse(null);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto){
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setPhone(customerDto.getPhone());

            Customer updated = customerRepository.save(existingCustomer);

            CustomerDto dto = new CustomerDto();
            dto.setName(updated.getName());
            dto.setEmail(updated.getEmail());
            dto.setPhone(updated.getPhone());
            return dto;
        }).orElse(null);
    }

    public boolean deleteCustomer(Long id){
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
