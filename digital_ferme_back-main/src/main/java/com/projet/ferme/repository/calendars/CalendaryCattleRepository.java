package com.projet.ferme.repository.calendars;

import java.util.List;

import com.projet.ferme.entity.calendars.CalendaryCattle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
@CrossOrigin("*")
public interface CalendaryCattleRepository extends JpaRepository<CalendaryCattle,Long>{

	List<CalendaryCattle> findByCattle_id(Long id);

}
