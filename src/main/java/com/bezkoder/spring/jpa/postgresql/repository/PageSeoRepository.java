package com.bezkoder.spring.jpa.postgresql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bezkoder.spring.jpa.postgresql.entity.PageSeo;

@Repository
public interface PageSeoRepository extends JpaRepository<PageSeo, Long> {

	List<PageSeo> findAllByOrderByIdDesc();

	java.util.Optional<PageSeo> findByPageKey(String pageKey);
}
