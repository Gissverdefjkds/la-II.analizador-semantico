package mx.edu.analizador.lexico;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {
    private Lexer lexer() {
        return new Lexer(List.of(new ReconocedorNumero(), new ReconocedorSimbolo()));
    }

    @Test
    void reconoceSimbolosConPosicion() {
        List<Token> tokens = lexer().analizar("(){\n  ;");
        assertEquals(4, tokens.size());
        assertEquals(new Token("(", TipoToken.SIMBOLO, 1, 1, 1), tokens.get(0));
        assertEquals(new Token("{", TipoToken.SIMBOLO, 1, 3, 3), tokens.get(2));
        assertEquals(new Token(";", TipoToken.SIMBOLO, 2, 3, 3), tokens.get(3));
    }

    @Test
    void caracterDesconocidoLanzaExcepcion() {
        assertThrows(IllegalStateException.class, () -> lexer().analizar("(@)"));
    }

    @Test
    void reconoceNumeros() {
        List<Token> tokens = lexer().analizar("42 3.14");
        assertEquals(2, tokens.size());

        assertEquals(new Token("42", TipoToken.CONSTANTE_ENTERA, 1, 1, 2), tokens.getFirst());
        assertEquals(new Token("3.14", TipoToken.CONSTANTE_REAL, 1, 4, 7), tokens.get(1));
    }

    @Test
    void detieneLecturaEnPuntoInvalido() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> lexer().analizar("1.5.3"));
        assertTrue(exception.getMessage().contains("Carácter inesperado '.'"));
    }
}
