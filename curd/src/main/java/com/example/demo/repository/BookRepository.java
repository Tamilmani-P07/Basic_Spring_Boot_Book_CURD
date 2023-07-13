package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{
	
	 @Query(value = "SELECT count(Book_Name) FROM Books WHERE Book_Name = :name and id NOT IN(:id)", nativeQuery = true)
	  int existByName(String name,long id);
	 @Query(value = "SELECT count(Book_Name) FROM Books WHERE Book_Name = :name", nativeQuery = true)
	  int existByName(String name);
}
