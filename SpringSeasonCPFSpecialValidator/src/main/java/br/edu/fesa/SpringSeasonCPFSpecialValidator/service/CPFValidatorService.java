package br.edu.fesa.SpringSeasonCPFSpecialValidator.service;

import org.springframework.stereotype.Service;

@Service
public class CPFValidatorService {

    enum Estado {
        Q0, Q1, Q2, Q3, Q4, Q5, Q6, Q7, Q8, Q9, Q10, Q11, Q12, Q13, FINAL, ERRO
    }

    public boolean validaFormatoCPF(String input) {
        Estado estadoCorrente = Estado.Q0;
        StringBuilder cpfBuilder = new StringBuilder();
        int dotCount = 0;
        int dashCount = 0;

        for (char c : input.toCharArray()) {
            switch (estadoCorrente) {
                case Q0:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q1;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q1:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q2;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q2:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q3;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q3:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q5;
                        cpfBuilder.append(c);
                    } else if (c == '.') {
                        estadoCorrente = Estado.Q4;
                        dotCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q4:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q5;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q5:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q6;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q6:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q7;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q7:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q9;
                        cpfBuilder.append(c);
                    } else if (c == '.') {
                        estadoCorrente = Estado.Q8;
                        dotCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q8:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q9;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q9:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q10;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q10:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q11;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q11:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q13;
                        cpfBuilder.append(c);
                    } else if (c == '-') {
                        estadoCorrente = Estado.Q12;
                        dashCount++;
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q12:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.Q13;
                        cpfBuilder.append(c);
                    } else {
                        estadoCorrente = Estado.ERRO;
                    }
                    break;
                case Q13:
                    if (Character.isDigit(c)) {
                        estadoCorrente = Estado.FINAL;
                        cpfBuilder.append(c);
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

            if (estadoCorrente == Estado.ERRO) {
                break;
            }
        }

        String cpf = cpfBuilder.toString();
        boolean formatoValido = estadoCorrente == Estado.FINAL && cpf.length() == 11;

        // Valida se os separadores estão corretos (2 pontos e 1 traço, se houver)
        if (formatoValido && (dotCount > 0 || dashCount > 0)) {
            formatoValido = (dotCount == 2 && dashCount == 1);
        }

        return formatoValido && validarDigitosCPF(cpf);
    }

    private boolean validarDigitosCPF(String cpf) {
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = Character.getNumericValue(cpf.charAt(i));
        }

        // Cálculo do primeiro dígito
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += digitos[i] * (10 - i);
        }
        int digito1 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        // Cálculo do segundo dígito
        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += digitos[i] * (11 - i);
        }
        int digito2 = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

        return (digitos[9] == digito1) && (digitos[10] == digito2);
    }
}   