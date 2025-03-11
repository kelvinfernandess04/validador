/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.SpringSeasonCPFSpecialValidator.controller;

import br.edu.fesa.SpringSeasonCPFSpecialValidator.service.CPFValidatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kelvi
 */


@RestController
@RequestMapping("/api")
public class CPFValidatorController {

    private final CPFValidatorService cpfValidatorService;

    public CPFValidatorController(CPFValidatorService cpfValidatorService) {
        this.cpfValidatorService = cpfValidatorService;
    }

    @GetMapping("/validar-cpf")
    public String validarCPF(@RequestParam String cpf) {
        return cpfValidatorService.validaFormatoCPF(cpf) ? "CPF válido!" : "CPF inválido!";
    }
}
