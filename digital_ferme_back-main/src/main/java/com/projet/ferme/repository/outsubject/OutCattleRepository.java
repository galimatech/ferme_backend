package com.projet.ferme.repository.outsubject;

import java.util.List;

import com.projet.ferme.entity.outsubject.OutCattle;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutCattleRepository extends JpaRepository<OutCattle,Long>{

	List<OutCattle> getByCattle_id(Long id);

}
