package pt.ulisboa.tecnico.rnl.dei.deiwed.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Dish;

@Repository
@Transactional
public interface DishRepository extends JpaRepository<Dish, Long> {

}