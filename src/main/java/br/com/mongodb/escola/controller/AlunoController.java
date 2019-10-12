package br.com.mongodb.escola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mongodb.escola.model.Aluno;
import br.com.mongodb.escola.repository.AlunoRepository;

@Controller
@RequestMapping(value = "aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "aluno/cadastrar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Aluno aluno) {
		alunoRepository.salvar(aluno);
		return "redirect:/";
	}
}
