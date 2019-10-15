package br.com.mongodb.escola.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mongodb.escola.model.Aluno;
import br.com.mongodb.escola.model.Nota;
import br.com.mongodb.escola.repository.AlunoRepository;

@Controller
@RequestMapping("nota")
public class NotaController {

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/cadastrar/{id}")
	public String cadastrar(@PathVariable String id, Model model) {
		Aluno aluno = alunoRepository.findById(id);
		model.addAttribute("aluno", aluno);
		model.addAttribute("nota", new Nota());

		return "nota/cadastrar";
	}

	@PostMapping("/salvar/{id}")
	public String salvar(@PathVariable String id, @ModelAttribute Nota nota) throws IllegalAccessException {
		Aluno aluno = alunoRepository.findById(id);
		aluno.addNota(nota);
		alunoRepository.salvar(aluno);
		return "redirect:/aluno/listar";
	}
}
