package com.projet.ferme.repository.calendars;

import java.util.Date;
import java.util.List;

import com.projet.ferme.entity.calendars.CalendaryPoultry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendaryPoultryRepository extends JpaRepository<CalendaryPoultry,Long>{

	List<CalendaryPoultry> findByPoultry_id(Long id);
	List<CalendaryPoultry> getPoultryByDate(Date nowDate);

}
