package mx.edu.analizador.lexico;

public class ReconocedorCaracter implements ReconocedorToken {

    @Override
    public boolean puedeIniciar(Cursor cursor) {
        return cursor.siguiente() == '\'';
    }

    @Override
    public Token leer(Cursor cursor) {
        if (!puedeIniciar(cursor)) {
            return null;
        }

        int linea = cursor.linea();
        int inicio = cursor.columna();

        // Consumir la comilla inicial '
        cursor.avanzar();

        // Carácter nulo o fin de archivo prematuro
        if (cursor.siguiente() == '\0') {
            return null;
        }

        // Caso secuencia de escape: '\n', '\t', '\\', '\'', etc.
        if (cursor.siguiente() == '\\') {
            cursor.avanzar(); // consumir la diagonal
            char escape = cursor.siguiente();
            if (escape == 'n' || escape == 't' || escape == 'r' || escape == '0' || escape == '\\' || escape == '\'') {
                cursor.avanzar(); // consumir el carácter escapado
            } else {
                return null; // escape no válido
            }
        } else {
            // Carácter normal (no puede ser otra comilla simple ni diagonal sin escapar)
            if (cursor.siguiente() == '\'' || cursor.siguiente() == '\\') {
                return null;
            }
            cursor.avanzar(); // consumir el carácter
        }

        // Debe cerrar con comilla simple '
        if (cursor.siguiente() != '\'') {
            return null;
        }

        cursor.avanzar(); // consumir la comilla de cierre '

        int fin = cursor.columna() - 1;
        String lexema = cursor.extraer(linea, inicio, fin);

        return new Token(lexema, TipoToken.CONSTANTE_CARACTER, linea, inicio, fin);
    }
}