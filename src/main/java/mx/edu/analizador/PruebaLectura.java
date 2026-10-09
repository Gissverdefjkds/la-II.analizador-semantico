package mx.edu.analizador;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class PruebaLectura {
    public static void main (String[] args) throws IOException {
        Scanner tomasa = new Scanner(System.in);
        System.out.println("Agrega la ruta del archivo: ");

        String ruta = tomasa.nextLine();
        if (ruta.toLowerCase().endsWith(".c")) {
            String c = LectorC.Lectura(ruta);
            System.out.println("EL codigo es");
            System.out.println(c);
            //System.out.println("La ruta es " + ruta);
            tomasa.close();
        }else {
            System.out.println("No es un archivo c");
        }
    }
}
