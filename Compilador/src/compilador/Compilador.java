package compilador;

import java.io.IOException;

public class Compilador {
    nodo p;
    
    public static void main(String[] args) throws IOException  {
        lexico lexico = new lexico (); 
        if(!lexico.errorEncontrado)
        {
             System.out.println("Analisis lexico terminado");
         
        }
    
    }
}
