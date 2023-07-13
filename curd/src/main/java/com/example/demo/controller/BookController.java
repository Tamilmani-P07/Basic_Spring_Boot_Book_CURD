package com.example.demo.controller;



import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.CustomException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/")
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping("/getAllBooks")
	public ResponseEntity<List<Book>> getAllBooks(){
		try {
			List<Book> bookList = bookRepository.findAll();
			return new ResponseEntity<>(bookList,HttpStatus.OK);
		}
		catch (Exception e) {
			return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@GetMapping("/getBook/{id}")
	public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
		Book book =bookRepository.findById(id).orElseThrow(()->new CustomException("Book not Exist with Id: "+id));
			return ResponseEntity.ok(book);
	}
	@PostMapping("/saveBook")
	public ResponseEntity<String> saveBook(@RequestBody Book book){
		if(Objects.nonNull(book.getName()) && !"".equalsIgnoreCase(book.getName())) {
			  if(bookRepository.existByName(book.getName(),book.getId())>0)
			         throw new CustomException("Book Name is Already Exists");
		}
		 bookRepository.save(book);
		 return ResponseEntity.ok("Saved SuceessFully");
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book,@PathVariable("id") Long id)
	{
		Book oldBook = bookRepository.findById(id).orElseThrow(()->new CustomException("Book not Exist with Id: "+id));
		if(Objects.nonNull(book.getName()) && !"".equalsIgnoreCase(book.getName())) {
			
			  if(bookRepository.existByName(book.getName(),book.getId())>0) throw new
			      CustomException("Book Name is Already Exists");
			 
			oldBook.setName(book.getName());
		}
		
		if(Objects.nonNull(book.getAuthor()) && !"".equalsIgnoreCase(
				book.getAuthor()))
			oldBook.setAuthor(book.getAuthor());
		
		if(Objects.nonNull(book.getCategory()) && !"".equalsIgnoreCase(
				book.getCategory()))
			oldBook.setCategory(book.getCategory());
		
		 Book b=bookRepository.save(oldBook);
		 return ResponseEntity.ok(b);
	}
	@DeleteMapping("/book/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable("id") Long id) {
		bookRepository.deleteById(id);
		
		return ResponseEntity.ok("Deleted Sucessfully");
	}

}
