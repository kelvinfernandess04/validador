package br.edu.fesa.SpringSeasonCPFSpecialValidator.controller;

import br.edu.fesa.SpringSeasonCPFSpecialValidator.service.RGValidatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RGValidatorController {

    private final RGValidatorService rgValidatorService;

    public RGValidatorController(RGValidatorService rgValidatorService) {
        this.rgValidatorService = rgValidatorService;
    }

    @GetMapping("/validar-rg")
    public String validarRG(@RequestParam String rg) {
        return rgValidatorService.validaFormatoRG(rg) ? "RG válido!" : "RG inválido!";
    }
}