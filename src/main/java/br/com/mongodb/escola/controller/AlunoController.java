package br.com.mongodb.escola.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.maps.errors.ApiException;

import br.com.mongodb.escola.model.Aluno;
import br.com.mongodb.escola.repository.AlunoRepository;
import br.com.mongodb.escola.service.GeolocalizacaoService;

@Controller
@RequestMapping(value = "aluno")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private GeolocalizacaoService geolocalizacaoService;

	@GetMapping("/listar")
	public String listar(Model model) {
		List<Aluno> alunos = alunoRepository.findAll();
		model.addAttribute("alunos", alunos);
		return "aluno/listar";
	}

	@GetMapping("visualizar/{id}")
	public String visualizar(@PathVariable String id, Model model) {
		Aluno aluno = alunoRepository.findById(id);
		model.addAttribute("aluno", aluno);
		return "aluno/visualizar";
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("aluno", new Aluno());
		return "aluno/cadastrar";
	}

	@PostMapping("/salvar")
	public String salvar(@ModelAttribute Aluno aluno) {

		try {
			List<Double> lateLong = geolocalizacaoService.getLateLongBy(aluno.getContato());
			aluno.getContato().setCoordinates(lateLong);
			alunoRepository.salvar(aluno);
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@GetMapping("/pesquisarnome")
	public String pesquisarNome() {
		return "aluno/pesquisarnome";
	}

	@GetMapping("/pesquisar")
	public String pesquisar(@RequestParam("nome") String nome, Model model) {
		List<Aluno> alunos = alunoRepository.findByName(nome);
		model.addAttribute("alunos", alunos);
		return "aluno/pesquisarnome";
	}
}
