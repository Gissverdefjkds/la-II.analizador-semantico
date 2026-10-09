package mx.edu.analizador.lexico;

/** Reconoce si el caracter actual es un número entero */
public class ReconocedorNumero implements ReconocedorToken {
    @Override
    public boolean puedeIniciar(Cursor cursor) {
        return !cursor.fin() && (Character.isDigit(cursor.actual()));
    }

    @Override
    public Token leer(Cursor cursor) {
        int linea = cursor.linea();
        int inicio = cursor.columna();
        StringBuilder lexemaSB = new StringBuilder();
        int esDecimal = 0;

        while (!cursor.fin() && Character.isDigit(cursor.actual()) || cursor.actual() == '.') {
            if (cursor.actual() == '.') {
                esDecimal++;
                if (esDecimal > 1) {
                    break;
                }
            }

            lexemaSB.append(cursor.actual());
            cursor.avanzar();
        }


        int fin = cursor.columna() - 1;
        String lexema = lexemaSB.toString();

        if (esDecimal == 1) {
            return new Token(lexema, TipoToken.CONSTANTE_REAL, linea, inicio, fin);
        } else {
            return new Token(lexema, TipoToken.CONSTANTE_ENTERA, linea, inicio, fin);

        }
    }

}
