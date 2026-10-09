package mx.edu.analizador.lexico;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReconocedorCaracterTest {

    private final ReconocedorCaracter reconocedor = new ReconocedorCaracter();

    @Test
    void testCaracterSimple() {
        Cursor cursor = new Cursor("'a'");
        assertTrue(reconocedor.puedeIniciar(cursor));
        Token token = reconocedor.leer(cursor);
        assertNotNull(token);
        assertEquals("'a'", token.lexema());
        assertEquals(TipoToken.CONSTANTE_CARACTER, token.tipo());
        assertEquals(1, token.linea());
    }

    @Test
    void testEscapesValidos() {
        Cursor cursorSalto = new Cursor("'\\n'");
        assertEquals("'\\n'", reconocedor.leer(cursorSalto).lexema());

        Cursor cursorComilla = new Cursor("'\\''");
        assertEquals("'\\''", reconocedor.leer(cursorComilla).lexema());

        Cursor cursorDiagonal = new Cursor("'\\\\'");
        assertEquals("'\\\\'", reconocedor.leer(cursorDiagonal).lexema());
    }

    @Test
    void testCaracterVacioLanzaExcepcion() {
        Cursor cursor = new Cursor("''");
        assertThrows(IllegalStateException.class, () -> reconocedor.leer(cursor));
    }

    @Test
    void testMultipleCaracterLanzaExcepcion() {
        Cursor cursor = new Cursor("'ab'");
        assertThrows(IllegalStateException.class, () -> reconocedor.leer(cursor));
    }

    @Test
    void testNoCerradoAlFinalDelArchivoLanzaExcepcion() {
        Cursor cursor = new Cursor("'");
        assertThrows(IllegalStateException.class, () -> reconocedor.leer(cursor));
    }
}