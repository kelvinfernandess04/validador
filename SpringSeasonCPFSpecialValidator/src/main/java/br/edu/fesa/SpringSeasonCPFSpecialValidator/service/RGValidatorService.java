package br.edu.fesa.SpringSeasonCPFSpecialValidator.service;

import org.springframework.stereotype.Service;

@Service
public class RGValidatorService {

    enum Estado {
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, FINAL, ERRO
    }

    public boolean validaFormatoRG(String input) {
        Estado estadoCorrente = Estado.Q0;
        StringBuilder rgBuilder = new StringBuilder();
        int dotCount = 0;
        int dashCount = 0;

        for (char c : input.toCharArray()) {
            switch (estadoCorrente) {
                case Q0:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q1;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q1:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q2;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q2:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q4;
                        rgBuilder.append(c);
                    } else if (c == '.') {
                        estadoCorrente = Estado.Q3;
                        dotCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q3:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q4;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q4:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q5;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q5:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q6;
                        rgBuilder.append(c);
                    } else if (c == '.') {
                        estadoCorrente = Estado.Q7;
                        dotCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q6:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q9;
                        rgBuilder.append(c);
                    } else if (c == '.') {
                        estadoCorrente = Estado.Q7;
                        dotCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q7:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q8;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q8:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q9;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q9:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q10;
                        rgBuilder.append(c);
                    } else if (c == '-') {
                        estadoCorrente = Estado.Q11;
                        dashCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q10:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q12;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q11:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q12;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q12:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.FINAL;
                        rgBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case FINAL:
                    estadoCorrente = Estado.ERRO;
                    break;
                default:
                    estadoCorrente = Estado.ERRO;
                    break;
            }

            if (estadoCorrente == Estado.ERRO) break;
        }

        String rg = rgBuilder.toString();
        boolean formatoValido = estadoCorrente == Estado.FINAL && rg.length() == 9;

        if (formatoValido && (dotCount > 0 || dashCount > 0)) {
            formatoValido = (dotCount == 2 && dashCount == 1);
        }

        return formatoValido && validarDigitoRG(rg);
    }

    private boolean validarDigitoRG(String rg) {
        int[] digitos = new int[9];
        for (int i = 0; i < 9; i++) {
            digitos[i] = Character.getNumericValue(rg.charAt(i));
        }

        int[] pesos = {2, 3, 4, 5, 6, 7, 8, 9};
        int soma = 0;
        
        for (int i = 0; i < 8; i++) {
            soma += digitos[i] * pesos[i];
        }

        int digitoVerificador = 11 - (soma % 11);
        digitoVerificador = digitoVerificador == 10 ? 0 : digitoVerificador;
        digitoVerificador = digitoVerificador == 11 ? 0 : digitoVerificador;

        return digitos[8] == digitoVerificador;
    }
}