package com.dteodoro.javari.domain.bettor;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dteodoro.javari.dto.BettorDTO;
import com.dteodoro.javari.repository.BettorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BettorService {

	private ModelMapper modelMapper;
	private BettorRepository bettorRepo;

	public BettorDTO findBettorDetails(UUID bettorId) {
		return modelMapper.map(bettorRepo.findById(bettorId).orElse(null), BettorDTO.class);

	}
	
}
