package mx.edu.analizador.lexico;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ReconocedorComentarioTest {

    private final ReconocedorComentario reconocedor =
            new ReconocedorComentario();

    @Test
    void reconoceComentarioDeLinea() {
        Cursor cursor = new Cursor("// hola mundo\nint x = 1;");

        assertTrue(reconocedor.puedeIniciar(cursor));

        Token token = reconocedor.leer(cursor);

        assertEquals("// hola mundo", token.lexema());
        assertEquals(TipoToken.COMENTARIO_LINEA, token.tipo());
        assertEquals(1, token.linea());
        assertEquals(1, token.inicio());

        // El cursor debe quedar antes del salto de línea.
        assertEquals('\n', cursor.actual());
    }

    @Test
    void reconoceComentarioDeBloque() {
        Cursor cursor = new Cursor("/* comentario */ int x = 1;");

        assertTrue(reconocedor.puedeIniciar(cursor));

        Token token = reconocedor.leer(cursor);

        assertEquals("/* comentario */", token.lexema());
        assertEquals(TipoToken.COMENTARIO_BLOQUE, token.tipo());
        assertEquals(1, token.linea());
        assertEquals(1, token.inicio());

        // Después de leer */, debe quedar sobre el espacio posterior.
        assertEquals(' ', cursor.actual());
    }

    @Test
    void reconoceComentarioDeBloqueMultilinea() {
        Cursor cursor = new Cursor("/* primera linea\nsegunda linea */");

        assertTrue(reconocedor.puedeIniciar(cursor));

        Token token = reconocedor.leer(cursor);

        assertEquals(
                "/* primera linea\nsegunda linea */",
                token.lexema()
        );
        assertEquals(TipoToken.COMENTARIO_BLOQUE, token.tipo());
        assertEquals(1, token.linea());
        assertEquals(1, token.inicio());
    }

    @Test
    void noConfundeDivisionConComentario() {
        Cursor cursor = new Cursor("/");

        assertFalse(reconocedor.puedeIniciar(cursor));
    }

    @Test
    void noConfundeAsignacionDivisionConComentario() {
        Cursor cursor = new Cursor("/=");

        assertFalse(reconocedor.puedeIniciar(cursor));
    }
}
