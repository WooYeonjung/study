package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.JoDTO;
import com.example.demo.entity.Jo;
import com.example.demo.repository.JoRepository;

@Service
public class JoServiceImpl implements JoService {

	@Autowired
	JoRepository repository;
	
	@Override
	public List<Jo> selectAll(){
		return repository.findAll();
	}
	@Override
	public Jo selectOne(int jno) {
		Optional<Jo> result = repository.findById(jno);
		if(result.isPresent()) return result.get();
		else return null;
	}
	
	@Override
	public Jo save(Jo entity) {
		return repository.save(entity);
	}
	

	@Override
	public void deleteById(int jno) {
		 repository.deleteById(jno);
	}
	
	//조장이름을 위한 join
	// joJoinMember
	public List<JoDTO> joinList(){
		return repository.findAllJoin();
	};

}
