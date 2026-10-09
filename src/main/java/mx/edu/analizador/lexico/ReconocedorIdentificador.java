package mx.edu.analizador.lexico;

public class ReconocedorIdentificador implements ReconocedorToken {

    // palabras reservadas
    private String[] reservadas = {
        "auto", "break", "case", "char", "const", "continue",
          "default", "do", "double", "else", "enum", "extern",
        "float", "for", "goto", "if", "inline", "int", "long",
           "register", "restrict", "return", "short", "signed",
         "sizeof", "static", "struct", "switch", "typedef",
        "union", "unsigned", "void", "volatile", "while",
        "_Alignas", "_Alignof", "_Atomic", "_Bool", "_Complex",
        "_Generic", "_Imaginary", "_Noreturn", "_Static_assert",
          "_Thread_local"
    };

    // el identificador  con letra ASCII o _
    private static boolean esInicioIdentificador(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_';
    }

    // y sigue con letras, dígitos o _
    private static boolean esParteIdentificador(char c) {
        return esInicioIdentificador(c) || (c >= '0' && c <= '9');
    }

    @Override
    public boolean puedeIniciar(Cursor cursor) {
      if (cursor.fin()) {
         return false;
        }
        return esInicioIdentificador(cursor.actual());
    }

    @Override
    public Token leer(Cursor cursor) {

        // donde comienza la palabra
    int linea = cursor.linea();
    int inicio = cursor.columna();

        String palabra = "";

        // leer el identificador completo
       while (!cursor.fin() && esParteIdentificador(cursor.actual())) {
         palabra = palabra + cursor.actual();
         cursor.avanzar();
        }

       // la columna donde termino
     int fin = cursor.columna() - 1;

     // ver si es palabra reservada
      boolean esReservada = false;
      for (int i = 0; i < reservadas.length; i++) {
         if (reservadas[i].equals(palabra)) {
            esReservada = true;
              break;
            }
        }

     TipoToken tipo;
        if (esReservada) {
            tipo = TipoToken.PALABRA_RESERVADA;
       } else {
         tipo = TipoToken.IDENTIFICADOR;
      }

     return new Token(palabra, tipo, linea, inicio, fin);
  }
}