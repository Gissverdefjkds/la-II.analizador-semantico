package mx.edu.analizador.lexico;

public class palabrasReservadas implements ReconocedorToken {

    // arreglo de palabras reservadas que nos tocan
    private String[] reservadas = {
            "and", "asm", "auto",
            "bitand", "bitor", "bool", "break",
            "case", "catch", "char", "class",
            "compl", "const", "constexpr",
            "continue", "decltype", "default", "delete",
            "do", "double", "else",
            "enum", "explicit", "export", "extern",
            "false", "float", "for", "friend",
            "goto", "if", "inline", "int",
            "long", "mutable", "namespace", "new",
            "noexcept", "not", "nullptr",
            "operator", "or", "private",
            "protected", "public", "register",
            "return", "short", "signed", "sizeof",
            "static", "struct", "switch",
            "template", "this", "throw", "true",
            "try", "typedef", "typeid", "typename",
            "union", "unsigned", "using", "virtual",
            "void", "volatile", "while",
            "xor"
    };

    @Override
    public boolean puedeIniciar(Cursor cursor) {
        String palabra = "";
        int avance = 0;

        // que letra sigue
        char letra = cursor.siguiente(avance);

        // junta las letras
        while ((letra >= 'a' && letra <= 'z') || (letra >= 'A' && letra <= 'Z')) {
           palabra = palabra + letra;
           avance = avance + 1;
           letra = cursor.siguiente(avance);
        }

//for para buscar en el arreglo
        for (int i = 0; i < reservadas.length; i++) {
          if (palabra.equals(reservadas[i])) {
             return true;
            }
        }

        // si no esta en la lista entonces es un false
        return false;

    }

    @Override
public Token leer(Cursor cursor) {

    // Guardamos dónde comienza la palabra
    int linea = cursor.linea();
    int inicio = cursor.columna();

    String palabra = "";

    // Leemos la palabra completa
    while (!cursor.fin() &&
           (Character.isLetterOrDigit(cursor.actual())
            || cursor.actual() == '_')) {

        palabra = palabra + cursor.actual();
        cursor.avanzar();
    }

    // Guardamos la columna donde terminó
    int fin = cursor.columna() - 1;

    // Creamos el token
    return new Token(
        palabra,
        TipoToken.PALABRA_RESERVADA,
        linea,
        inicio,
        fin
    );
}
}
