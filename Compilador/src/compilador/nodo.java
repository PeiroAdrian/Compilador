
package compilador;

class nodo {
    String lexema;
    int token;
    int renglon; 
    nodo siguienteNodo = null;
    
    nodo (String lexema, int token, int numRenglon)
    {
        this.lexema = lexema;
        this.token = token;
        this.renglon = numRenglon;
    };
}
