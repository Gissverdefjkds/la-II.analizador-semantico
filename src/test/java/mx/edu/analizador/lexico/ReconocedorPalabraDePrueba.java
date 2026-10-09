package mx.edu.analizador.lexico;


class ReconocedorPalabraDePrueba implements ReconocedorToken {

    @Override
    public boolean puedeIniciar(Cursor cursor) {
        return esParteDePalabra(cursor.actual());
    }

    @Override
    public Token leer(Cursor cursor) {
        int linea = cursor.linea();
        int inicio = cursor.columna();
        StringBuilder lexema = new StringBuilder();
        while (esParteDePalabra(cursor.actual())) {
            lexema.append(cursor.actual());
            cursor.avanzar();
        }
        int fin = cursor.columna() - 1;
        return new Token(lexema.toString(), TipoToken.IDENTIFICADOR, linea, inicio, fin);
    }

    private boolean esParteDePalabra(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}