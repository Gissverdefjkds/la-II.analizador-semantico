package mx.edu.analizador.lexico;

public class ReconocedorIdentificador implements ReconocedorToken {
    //El identificador empieza con letra, _ o $

    @Override
    public boolean puedeIniciar(Cursor cursor){
        if (cursor.fin()) return false;
        char c = cursor.actual();
        return Character.isLetter(c) || c == '_' || c == '$';

    }

    @Override
    public Token leer(Cursor cursor) {
        int linea =  cursor.linea();
        int inicio = cursor.columna();
        StringBuilder lexema = new StringBuilder();
        //Se sigue leyendo mientras haya letra, numero,_ o $
        while(!cursor.fin() && (Character.isLetterOrDigit(cursor.actual()) || cursor.actual() == '_' ||  cursor.actual() == '$')){
            lexema.append(cursor.actual());
            cursor.avanzar();
        }
        int fin = cursor.columna() - 1;

        return new Token (lexema.toString(), TipoToken.IDENTIFICADOR, linea, inicio, fin);
    }
}
