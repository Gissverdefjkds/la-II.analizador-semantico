package mx.edu.analizador.semantico;

import mx.edu.analizador.lexico.TipoToken;
import mx.edu.analizador.lexico.Token;

import java.util.ArrayList;
import java.util.List;

public class ErrorContinue {

    public List<ErrorSemantico> analizar(List<Token> tokens) {
        List<ErrorSemantico> errores = new ArrayList<>();


        // Guarda true si la llave es de un ciclo, false si es de un if normal
   List<Boolean> llaves = new ArrayList<>();
   boolean cicloPendiente = false;

   for (int i = 0; i < tokens.size(); i++) {
       Token token = tokens.get(i);

       if (esPalabra(token, "while") || esPalabra(token, "for") || esPalabra(token, "do")) {
           cicloPendiente = true;
           // aqui, Si el while no tiene llaves esto marca error falso
           // intente avanzar i pero no se como saltarme la condicion del parentesis

       }
       else if (esSimbolo(token, "{")) {
           llaves.add(cicloPendiente);
           cicloPendiente = false;
       }
       else if (esSimbolo(token, "}")) {
           if (llaves.size() > 0) {
               llaves.remove(llaves.size() - 1);
           }
       }
       else if (esPalabra(token, "continue")) {
           // Revisamos la lista para ver si estamos protegidos por un ciclo
           if (!hayCicloAbierto(llaves)) {
               errores.add(new ErrorSemantico(
                   "Uso incorrecto de continue",
                   "Solo se puede usar dentro de un ciclo",
                   token.linea(), token.inicio(), token.fin()));
           }
       }
   }
   return errores;
    }

    // pase esto a un metodo aparte
    private boolean hayCicloAbierto(List<Boolean> llaves) {
        for (int i = 0; i < llaves.size(); i++) {
            if (llaves.get(i)) {
                return true;
            }
        }
        return false;
    }

    // para no escribir token.tipo() ==  en cada if
    private boolean esPalabra(Token token, String lexema) {
        return token.tipo() == TipoToken.PALABRA_RESERVADA && token.lexema().equals(lexema);
    }

    private boolean esSimbolo(Token token, String lexema) {
        return token.tipo() == TipoToken.SIMBOLO && token.lexema().equals(lexema);
    }
}