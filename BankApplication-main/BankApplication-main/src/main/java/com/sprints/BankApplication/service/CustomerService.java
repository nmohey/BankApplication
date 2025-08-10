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
        return mapToDto(savedCustomer);
    }

    private CustomerDto mapToDto(Customer customer) {
        if (customer == null) return null;
        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }
    public List<CustomerDto> getAllCustomers(){return customerRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());}

    public CustomerDto getCustomerById(Integer id){return customerRepository.findById(id).map(this::mapToDto).orElse(null);}

    public CustomerDto findByEmail(String email) {return customerRepository.findByEmail(email).map(this::mapToDto).orElse(null);}


    public CustomerDto updateCustomer(Integer id, CustomerDto customerDto){
        return customerRepository.findById(id).map(existingCustomer -> {
            existingCustomer.setName(customerDto.getName());
            existingCustomer.setEmail(customerDto.getEmail());
            existingCustomer.setPhone(customerDto.getPhone());

            Customer updated = customerRepository.save(existingCustomer);
            return mapToDto(updated);
        }).orElse(null);
    }

    public boolean deleteCustomer(Integer id){
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
