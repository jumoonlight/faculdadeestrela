package com.ifsp.anajuliaferreira.util;

public class Util{
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            int peso = 10;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            int resto = 11 - (soma % 11);
            char digito1 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * peso--;
            }
            resto = 11 - (soma % 11);
            char digito2 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

            return digito1 == cpf.charAt(9) && digito2 == cpf.charAt(10);

        } catch (Exception e) {
            return false;
        }
    }
    public static boolean validarNome(String nome){
        if (nome.length() < 3){
            return false;
        }
        return true;
    }
    public static boolean validarTelefone(String telefone){
        String telefoneVal = telefone.replaceAll("[()\\s-]", "");
        try{
            Long.parseLong(telefoneVal);
            if(telefoneVal.length() != 10 && telefoneVal.length() != 11){
                return false;
            }
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    } 
    public static boolean validarEmail(String email){
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    public static boolean validarIdade(String dataNasc){
        String[] partes = dataNasc.split("-");
        int anoNascimento = Integer.parseInt(partes[0]);
        int mesNascimento = Integer.parseInt(partes[1]);
        int diaNascimento = Integer.parseInt(partes[2]);

        java.time.LocalDate dataAtual = java.time.LocalDate.now();
        int anoAtual = dataAtual.getYear();
        int mesAtual = dataAtual.getMonthValue();
        int diaAtual = dataAtual.getDayOfMonth();

        int idade = anoAtual - anoNascimento;

        if (mesAtual < mesNascimento || (mesAtual == mesNascimento && diaAtual < diaNascimento)) {
            idade--;
        }

        return idade < 18;
    }
}