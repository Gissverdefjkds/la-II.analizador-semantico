package mx.edu.analizador.lexico;

public class ReconocedorComentario implements ReconocedorToken{

    @Override
    public boolean puedeIniciar(Cursor cursor) {
        // TODO Auto-generated method stub

        if (
        cursor.actual() == '/' &&  
        cursor.siguiente(1) == '/' ||
        cursor.actual() == '/' && 
        cursor.siguiente(1) == '*'){
            return true;

        }else{
            return false;
        }
        //throw new UnsupportedOperationException("Unimplemented method 'puedeIniciar'");
    }

    @Override
    public Token leer(Cursor cursor) {
        // TODO Auto-generated method stub

        int lineaInicial = cursor.linea();
        int columnaInicial = cursor.columna();
        int columnaFin = cursor.columna();
        StringBuilder armadorDeToken = new StringBuilder();


        if(cursor.siguiente(1) == '/'){
            while (!cursor.fin() && cursor.actual() != '\n'){
                armadorDeToken.append(cursor.actual());
                cursor.avanzar();
                columnaFin++;
            }

            return new Token(
                armadorDeToken.toString(),
                TipoToken.COMENTARIO_LINEA,
                lineaInicial,
                columnaInicial,
                columnaFin
            );

        } else if(cursor.siguiente(1) == '*'){
            while (!cursor.fin() && !(cursor.actual() == '*' && cursor.siguiente(1) == '/')){
                armadorDeToken.append(cursor.actual());
                cursor.avanzar();
  
            }

            // Si sí encontramos el cierre */, se agrega y se consume.
            if (!cursor.fin()) {
            armadorDeToken.append(cursor.actual()); // *
            cursor.avanzar();


            armadorDeToken.append(cursor.actual()); // /
            cursor.avanzar();

        }

            return new Token(
                armadorDeToken.toString(),
                TipoToken.COMENTARIO_BLOQUE,
                lineaInicial,
                columnaInicial,
                cursor.columna()
            );

        }

        throw new UnsupportedOperationException("Unimplemented method 'leer'");
    }
    
}
