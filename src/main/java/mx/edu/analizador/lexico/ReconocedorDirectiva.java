package mx.edu.analizador.lexico;

import java.util.Set;


public class ReconocedorDirectiva implements ReconocedorToken {

    private static final String[] DIRECTIVAS = {
            "include", "define", "undef",
            "if", "ifdef", "ifndef", "elif", "elifdef", "elifndef", "else", "endif",
            "line", "error", "warning", "pragma", "embed"
    };
    private static final int VENTANA = 64;

    @Override
    public boolean puedeIniciar(Cursor cursor) {
        if (cursor.actual() != '#') return false;
        return longitudDirectiva(mirarAdelante(cursor), 0) > 0;
    }
// s
    @Override
    public Token leer(Cursor cursor) {
        String adelante = mirarAdelante(cursor);
        int longitud = longitudDirectiva(adelante, 0);

        int linea = cursor.linea();
        int inicio = cursor.columna();
        for (int i = 0; i < longitud; i++) {
            cursor.avanzar();
        }
        int fin = cursor.columna() - 1;

        return new Token(adelante.substring(0, longitud), TipoToken.DIRECTIVA, linea, inicio, fin);
    }
    private String mirarAdelante(Cursor cursor) {
        StringBuilder sb = new StringBuilder();
        sb.append(cursor.actual());
        for (int n = 1; n < VENTANA; n++) {
            char c = cursor.siguiente(n);
            if (c == '\n' || c == '\r' || c == '\0') break;
            sb.append(c);
        }
        return sb.toString();
    }

    static int longitudDirectiva(String texto, int inicio) {
        int i = inicio;
        if (i >= texto.length() || texto.charAt(i) != '#') return 0;
        i++;

        while (i < texto.length() && (texto.charAt(i) == ' ' || texto.charAt(i) == '\t')) i++;

        int inicioNombre = i;
        while (i < texto.length() && Character.isLetter(texto.charAt(i))) i++;

        if (!java.util.Arrays.asList(DIRECTIVAS).contains(texto.substring(inicioNombre, i))) return 0;        if (i < texto.length()
                && (Character.isLetterOrDigit(texto.charAt(i)) || texto.charAt(i) == '_')) {
            return 0;
        }
        return i - inicio;
    }
}