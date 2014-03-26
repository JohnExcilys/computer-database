package com.excilys.computerdb.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.excilys.computerdb.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

}
