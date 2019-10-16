package br.com.mongodb.escola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mongodb.escola.model.Aluno;
import br.com.mongodb.escola.repository.AlunoRepository;

@Controller
public class GeolocalizacaoController {

	@Autowired
	private AlunoRepository alunoRepository;

	@GetMapping("/geolocalizacao/iniciarpesquisa")
	public String inicializarPesquisa(Model model) {
		List<Aluno> alunosList = alunoRepository.findAll();

		model.addAttribute("alunos", alunosList);

		return "geolocalizacao/pesquisar";
	}

	@GetMapping("/geolocalizacao/pesquisar")
	public String pesquisar(@RequestParam("alunoId") String alunoId, Model model) {
		Aluno aluno = alunoRepository.findById(alunoId);
		List<Aluno> alunosProximos = alunoRepository.pesquisaPorGeolocalizao(aluno);

		model.addAttribute("alunosProximos", alunosProximos);
		return "geolocalizacao/pesquisar";
	}

}
