package com.example.demo.service;

import com.example.demo.entity.Employee;
import com.example.demo.repository.MyRepository;
import org.redisson.api.MapOptions;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {

  @Autowired
  private MyRepository myRepository;

  @Autowired
  private RedissonClient redissonClient;

  @Autowired
  private RMapCache<Long, Employee> employeeRMapCache;


  public Employee save(final Employee employee) {
    this.employeeRMapCache.put(employee.getId(), employee);
    return employee;
  }

  public List<Employee> findAll() {
    List<Employee> employeeList = new ArrayList<>();
    this.myRepository.findAll().forEach(employeeList::add);
    return employeeList;
  }

  public Employee findById(final long id) {
    return this.employeeRMapCache.get(id);
  }

  public List<Employee> findByName(final String name) {
    return this.myRepository.findByName(name);
  }

  public void updateNameForId(final long id, final String name) {
    final Employee employee = this.myRepository.findById(id);
    employee.setName(name);
    this.myRepository.save(employee);
  }

}
