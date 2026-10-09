package mx.edu.analizador.lexico;

public class palabrasReservadas implements ReconocedorToken {

    // arreglo de palabras reservadas que nos tocan
    private String[] reservadas = {
       "int", "float", "char", "double", "void",
            "if", "else", "while", "for", "do",
            "return", "switch", "case", "default", "break",
            "and"
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

        // aqui me falta el for para comparar con el arreglo
        // le pongo false para que no me marque error en el main en lo que termino
        return false;
    }

    @Override
    public Token leer(Cursor cursor) {
        // todavia no hago esta parte
        return null;
    }
}