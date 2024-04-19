package com.example.DMS.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.DMS.Models.Dog;
import com.example.DMS.Models.Trainer;
import com.example.DMS.repository.DogRepository;
import com.example.DMS.repository.TrainerRepository;

@Controller
public class DogController {
	@Autowired
	DogRepository dogRepo;
	@Autowired
	TrainerRepository trainerRepo;
	ModelAndView mv = new ModelAndView();
//	@RequestMapping("dogHome")
//	public String home() {
//		return "home";
//	}
	@RequestMapping("dogHome")
	public ModelAndView home() {
		mv.setViewName("home");
		return mv;
	}
	@RequestMapping("add")
	public ModelAndView add() {
		mv.setViewName("addNewDog.html");
		mv.addObject("trainers", trainerRepo.findAll());
		return mv;
	}
	@RequestMapping("addNewDog")
	public ModelAndView addNewDog(Dog dog, @RequestParam("trainerId")int id) {
		Trainer trainer = trainerRepo.findById(id).orElse(new Trainer());
		dog.setTrainer(trainer);
		dogRepo.save(dog);
		mv.setViewName("home");
		return mv;
	}

	@RequestMapping("viewModifyDelete")
	public ModelAndView viewDogs() {
		
		mv.setViewName("viewDogs");
		Iterable<Dog> dogList = dogRepo.findAll();
		mv.addObject("dogs", dogList);
		return mv;
	}
	
	@RequestMapping("editDog")
	public ModelAndView editDog(Dog dog) {
		dogRepo.save(dog);
		mv.setViewName("editDog");
		return mv;
	}

	@RequestMapping("deleteDog")
	public ModelAndView deleteDog(Dog dog) {
//		 Optional<Dog> d = dogRepo.findById(dog.getId());
//		 if(d.isPresent()) {
//			 dogRepo.delete(dog);
//		 }
//		 return home();
//		
		Dog d = dogRepo.findById(dog.getId()).orElse(new Dog());
		dogRepo.delete(d);
		return home();
	}
	
	@RequestMapping("search")
	public ModelAndView searchById(int id) {
		Dog d = dogRepo.findById(id).orElse(new Dog());
		mv.addObject(d);
		mv.setViewName("searchResults");
		return mv;
	}
	
	@RequestMapping("addTrainer")
	public ModelAndView addTrainer() {
		mv.setViewName("addNewTrainer.html");
		return mv;
	}
	
	@RequestMapping("trainerAdded")
	public ModelAndView addNewTrainer(Trainer trainer) {
		trainerRepo.save(trainer);
		mv.setViewName("home");
		return mv;
	}
}
