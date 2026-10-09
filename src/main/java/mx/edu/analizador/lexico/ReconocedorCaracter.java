package mx.edu.analizador.lexico;

public class ReconocedorCaracter {

    public static boolean esCaracterValido(String lexema) {
        if (lexema == null || lexema.length() < 3) {
            return false;
        }

        // Verifica que empiece y termine con comillas simples
        if (lexema.charAt(0) != '\'' || lexema.charAt(lexema.length() - 1) != '\'') {
            return false;
        }

        // Caracter simple: 'a', '1', etc.
        if (lexema.length() == 3) {
            char c = lexema.charAt(1);
            return c != '\'' && c != '\\';
        }

        // Secuencias de escape: '\n', '\t', '\\', '\'', etc.
        if (lexema.length() == 4) {
            if (lexema.charAt(1) == '\\') {
                char escape = lexema.charAt(2);
                return escape == 'n' || escape == 't' || escape == 'r' 
                    || escape == '0' || escape == '\\' || escape == '\'';
            }
        }

        return false;
    }
}