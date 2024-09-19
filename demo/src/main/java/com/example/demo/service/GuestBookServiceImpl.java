package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.domain.GuestBookDTO;
import com.example.demo.domain.PageRequestDTO;
import com.example.demo.domain.PageResultDTO;
import com.example.demo.entity.GuestBook;
import com.example.demo.repository.GuestBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//=> 각필드에 대해 1개의 매개변수가 있는 생성자를 생성함.
//=> 초기화 되지 않은 모든 final 필드에만 적용됨. 
public class GuestBookServiceImpl implements GuestBookService {
    
    private final GuestBookRepository repository;
    // => JPA Sql 처리를 위해정의
    //    생성자 주입 ( @RequiredArgsConstructor 를 통해 주입받음 )
    // => 도중에 값 변경을 막고 안전하게 사용하기 위해 final 을 적용함 
    //     ( 문법적으로 강제조항은 아님 ) 
    
    
	//** JPA Pageable 을 이용항 Paging 기능
    @Override
    public PageResultDTO<GuestBookDTO, GuestBook> pageList(PageRequestDTO requestDTO) {
    	//1) 조건 완성
    	Pageable pageable = requestDTO.getPageable( Sort.by("gno").descending());
    	
    	// 2) repository 실행
    	// => pageable 을 인자로 하는 findAll 메서드 제공(Page<E> Type 결과 return)
    	// => List select 후에 count (전체출력 레코드 갯수)도 실행함.
    	Page<GuestBook> result =  repository.findAll(pageable);
    	
    	// 3) Function<EN,DTO> 정의
    	Function<GuestBook,GuestBookDTO> fn = entity -> entityToDto(entity);
    	
    	//4) 결과 return
    	return new PageResultDTO<>(result,fn);
    	// return new PageResultDTO<>(result,entity -> entityToDto(entity));
    	
    }//pageList
    
    // ** JPA 기본 CRUD 구현 : JpaRepository 활용
    @Override
    public List<GuestBook> selectList() {
        return repository.findAll();
    }
    
    @Override
    public List<GuestBookDTO> selectList2() {

        
        // => ver01.
        //  List<GuestBook> list = repository.findAll(); 
        // Function<GuestBook, GuestBookDTO> fn =  entity -> entityToDto(entity); //entity를 dto로 바꿔주는 작업!
        //=> Function <T,R>
        // - 우일한 추상 메서드 : R apply(T t);
        // -T type 입력받아 R type return
        //entity -> entityToDto(entity) =>entity 로 들어와  entityToDto(entity) 로 return

        //  List<GuestBookDTO> list2 = list.stream().map(fn).collect(Collectors.toList()) ;
    	//  return list2  ;
    	
    	
        // =>ver02: fn 생략
    	//	List<GuestBookDTO> list2 = list.stream().map( entity -> entityToDto(entity)).collect(Collectors.toList()) ;
        // => collect() : Stream 의 결과를 원하는 자료형으로 바꿔 반환해주는 최종 연산자
        // => Collectors.toList() : Stream 을 List 형태로 바꿔줌 (.collect() 앞까지는 Stream )
    	//  return list2  ;
    	
    	
        // => ver03 : list2 생략
        // return list.stream().map( entity -> entityToDto(entity)).collect(Collectors.toList());
 
        // => ver04 : list,fn, list2 생략
        
        return repository.findAll()
        		.stream().map( entity -> entityToDto(entity))
        		.collect(Collectors.toList());
    
    }
    
    @Override
    public GuestBook selectOne(Long gno) {
        
        // ** Optional<T>
        // => Java8부터 Optional<T>클래스를 사용해 NullPointerException(이하 NPE)를 방지할수 있도록 지원.
        //      즉, Optional<T>는 null이 올수 있는 값을 감싸는 Wrapper클래스로, 
        //      참조하더라도 NPE가 발생하지 않도록 도와줌.
        //      제공되는 메소드로 복잡한 조건문 없이 NPE를 회피할 수 있어록 해줌
        // => isPresent() : Optional객체에 저장된 값이 null인지 확인 ( false 면 null )
        // => get() : Optional객체에 저장된 값 제공
        
        Optional<GuestBook> result = repository.findById(gno);
        if ( result.isPresent() ) return result.get();
        else return null;
    }
    
    @Override
    public Long register(GuestBook entity) {
        entity = repository.save(entity);  
        //=> 없으면 insert, 있으면 update
        //=> 처리후 입력완료된 entity 를 return
        return entity.getGno();
    }
    @Override
    public void delete(Long gno){
    	/* => gno 가 존재하는지 확인 후 Exception 생성
    	if(!repository.existsById(gno)) {    		
    		throw new Exception("Data not Found : gno = "+ gno);//직접 커스텀 예외를 던짐
    	} */
		repository.deleteById(gno);
    }
    


}