package compilador;

import java.io.RandomAccessFile;

class lexico {
    nodo cabeza = null;
    nodo p;
    int estado = 0;
    int columna;
    int valorMT;
    int numRenglon = 1;

    int caracter = 0;
    String lexema = "";
    boolean errorEncontrado = false;
    boolean errorEncontradoSintactico = false;

    String archivo = "C:\\Users\\MARIO\\Desktop\\codigo.txt";

    int matriz[][] = {

            // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27
            // l @ _ d + - * / ^ < > = ! & | ( ) { } , ; " eb tab nl . eof oc

            /* 0 */ { 1, 1, 1, 2, 103, 104, 105, 5, 107, 8, 9, 10, 11, 12, 13, 117, 118, 119, 120, 124, 125, 14, 0, 0,
                    0, 505, 0, 505 },
            /* 1 */ { 1, 1, 1, 1, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,
                    100, 100, 100, 100, 100, 100, 100 },
            /* 2 */ { 101, 101, 101, 2, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101, 101,
                    101, 101, 101, 101, 101, 3, 101, 101 },
            /* 3 */ { 500, 500, 500, 4, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
                    500, 500, 500, 500, 500, 500, 500, 500 },
            /* 4 */ { 102, 102, 102, 4, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102, 102,
                    102, 102, 102, 102, 102, 102, 102, 102 },
            /* 5 */ { 106, 106, 106, 106, 106, 106, 6, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106, 106,
                    106, 106, 106, 106, 106, 106, 106, 106 },
            /* 6 */ { 6, 6, 6, 6, 6, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 501, 6 },
            /* 7 */ { 6, 6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 501, 6 },
            /* 8 */ { 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 108, 110, 108, 108, 108, 108, 108, 108, 108,
                    108, 108, 108, 108, 108, 108, 108, 108, 108 },
            /* 9 */ { 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 109, 111, 109, 109, 109, 109, 109, 109, 109,
                    109, 109, 109, 109, 109, 109, 109, 109, 109 },
            /* 10 */ { 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 123, 112, 123, 123, 123, 123, 123, 123, 123,
                    123, 123, 123, 123, 123, 123, 123, 123, 123 },
            /* 11 */ { 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 116, 113, 116, 116, 116, 116, 116, 116, 116,
                    116, 116, 116, 116, 116, 116, 116, 116, 116 },
            /* 12 */ { 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 502, 114, 502, 502, 502, 502, 502,
                    502, 502, 502, 502, 502, 502, 502, 502, 502 },
            /* 13 */ { 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 503, 115, 503, 503, 503, 503,
                    503, 503, 503, 503, 503, 503, 503, 503, 503 },
            /* 14 */ { 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 122, 14, 14,
                    504, 14, 504, 14 }

    };
    String palReservadas[][] = {
            // 0 , 1 <----- numero de columna del arreglo
            /* 0 */ { "break", "200" },
            /* 1 */ { "if", "201" },
            /* 2 */ { "else", "202" },
            /* 3 */ { "main", "203" },
            /* 13 */ { "while", "204" },
            /* 5 */ { "go to", "205" },
            /* 6 */ { "print", "206" },
            /* 7 */ { "new", "207" },
            /* 8 */ { "float", "208" },
            /* 9 */ { "int", "209" },
            /* 10 */ { "false", "210" },
            /* 11 */ { "true", "211" },
            /* 12 */ { "String", "212" },
            /* 13 */ { "getvalue", "213" }
    };
    String errores[][] = {

            // 0 1 <----- numero de columna del arreglo
            /* 0 */{ "se espera digito", "500" },
            /* 1 */{ "se espera cierre de comentario", "501" },
            /* 2 */{ "se espera & 'and' ", "502" },
            /* 3 */{ "se espera | 'or' ", "503" },
            /* 4 */{ "se espera cierre de cadena ", "504" },
            /* 5 */{ "carácter no valido", "505" }
    };
    RandomAccessFile file = null;

    public lexico() {
        try {
            file = new RandomAccessFile(archivo, "r");
            while (caracter != -1) {
                caracter = file.read();

                if (Character.isLetter(((char) caracter))) {
                    columna = 0;
                } else if (Character.isDigit((char) caracter)) {
                    columna = 3;
                } else {
                    switch ((char) caracter) {
                        case '@':
                            columna = 1;
                            break;
                        case '_':
                            columna = 2;
                            break;
                        case '+':
                            columna = 4;
                            break;
                        case '-':
                            columna = 5;
                            break;
                        case '*':
                            columna = 6;
                            break;
                        case '/':
                            columna = 7;
                            break;
                        case '^':
                            columna = 8;
                            break;
                        case '<':
                            columna = 9;
                            break;
                        case '>':
                            columna = 10;
                            break;
                        case '=':
                            columna = 11;
                            break;
                        case '!':
                            columna = 12;
                            break;
                        case '&':
                            columna = 13;
                            break;
                        case '|':
                            columna = 14;
                            break;
                        case '(':
                            columna = 15;
                            break;
                        case ')':
                            columna = 16;
                            break;
                        case '{':
                            columna = 17;
                            break;
                        case '}':
                            columna = 18;
                            break;
                        case ',':
                            columna = 19;
                            break;
                        case ';':
                            columna = 20;
                            break;
                        case '"':
                            columna = 21;
                            break;
                        case ' ':// eb
                            columna = 22;
                            break;
                        case 9:// tab
                            columna = 23;
                            break;
                        case 10:// nl
                            columna = 24;
                            numRenglon++;
                            break;
                        case 13:// nl
                            columna = 24;
                            break;
                        case '.':
                            columna = 25;
                            break;
                        default:
                            if (caracter == -1) {
                                columna = 26;
                            } else {
                                columna = 27;
                            }
                    }
                }
                valorMT = matriz[estado][columna];

                if (valorMT < 100) { // cambiar de estado
                    estado = valorMT;

                    if (estado == 0) {
                        lexema = "";
                    } else {
                        lexema = lexema + (char) caracter;
                    }
                } else if (valorMT >= 100 && valorMT < 500) {// estado final
                    if (valorMT == 100) {
                        validarSiEsPalabraReservada();
                    }
                    if (valorMT == 100 || valorMT == 101 || valorMT == 102 || valorMT == 106 || valorMT == 123
                            || valorMT == 108 || valorMT == 109 || valorMT == 116 || valorMT >= 200) {
                        file.seek(file.getFilePointer() - 1);
                    } else {
                        lexema = lexema + (char) caracter;
                    }
                    insertarNodo();
                    estado = 0;
                    lexema = "";

                } else { // estado de error
                    imprimirMensajeError();
                    break;
                }

            } // while
            imprimirNodos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        ////////////////////////// Analizador Sintactico
        ////////////////////////// ////////////////////////////////
        p = cabeza;
        while (p != null) {
            if (p.token == 203) { // main
                p = p.siguienteNodo;
                if (p.token == 117) { // (
                    p = p.siguienteNodo;
                    if (p.token == 118) { // )
                        p = p.siguienteNodo;
                        if (p.token == 119) { // {
                            p = p.siguienteNodo;
                            variables();
                            if (errorEncontradoSintactico) {
                                break;
                            }
                            while (p.token != 120) {
                                statement();
                                if (errorEncontradoSintactico) {
                                    return;
                                }
                            }
                            if (p.token == 120) { // }
                                p = p.siguienteNodo;

                            }

                        } else {
                            System.out.println("Error, se espera: {");
                            errorEncontradoSintactico = true;
                            return;
                        }
                    } else {
                        System.out.println("Error, se espera: )");
                        errorEncontradoSintactico = true;
                        return;
                    }
                } else {
                    System.out.println("Error, se espera: (");
                    errorEncontradoSintactico = true;
                    return;
                }
            } else {
                System.out.println("Error, se espera: main");
                errorEncontradoSintactico = true;
                return;
            }
        }
    }

    private void variables() {
        if (p.token == 207) {// new
            p = p.siguienteNodo;
            if (tipos()) {
                p = p.siguienteNodo;
            } else {
                errorEncontradoSintactico = true;
                return;
            }

            if (p.token == 100 || p.token == 101) {// id
                p = p.siguienteNodo;
            } else {
                System.out.println("Error, se espera: id");
                errorEncontradoSintactico = true;
                return;
            }
            while (p.token == 124) {// ,
                p = p.siguienteNodo;
                if (p.token == 100 || p.token == 101) {// id
                    p = p.siguienteNodo;
                } else {
                    System.out.println("Error, se espera: id");
                    errorEncontradoSintactico = true;
                    return;
                }
            }
            if (p.token == 125) {// ;
                p = p.siguienteNodo;
            } else {
                System.out.println("Error, se espera: ;");
                errorEncontradoSintactico = true;
                return;
            }
        } else {
            System.out.println("Error, se espera: new");
            errorEncontradoSintactico = true;
            return;
        }
    }

    private void statement() {
        switch ((p.token)) {
            case 201:// if
                p = p.siguienteNodo;
                if (p.token == 117) {// (
                    p = p.siguienteNodo;
                    while (p.token != 118) {
                        exp_cond();
                        if (errorEncontradoSintactico) {
                            System.out.println("Error en sentencia condicional");
                            return;    
                        }
                    }
                    if (p.token == 118) { // )
                        p = p.siguienteNodo;
                        if (p.token == 119) {// {
                            p = p.siguienteNodo;
                            statement();
                            if (p.token == 120) {// }
                                p = p.siguienteNodo;
                                if (p.token != 202) {
                                    break;
                                }
                                if (p.token == 202) {// else
                                    p = p.siguienteNodo;
                                    if (p.token == 119) {// {
                                        p = p.siguienteNodo;
                                        statement();
                                        if (p.token == 120) {// }
                                            p = p.siguienteNodo;
                                            break;
                                        } else {
                                            System.out.println("Error, se espera: }");
                                            return;
                                        }
                                    } else {
                                        System.out.println("Error, se espera: {");
                                        return;
                                    }
                                } else {
                                    System.out.println("Error, se espera: else");
                                    return;
                                }
                            } else {
                                System.out.println("Error, se espera: }");
                                return;
                            }
                        } else {
                            System.out.println("Error, se espera: {");
                            return;
                        }
                    } else {
                        System.out.println("Error, se espera: )");
                        return;
                    }
                } else {
                    System.out.println("Error, se espera: (");
                    return;
                }

            case 204:// while
                p = p.siguienteNodo;
                if (p.token == 117) {// (
                    p = p.siguienteNodo;
                    exp_cond();
                    if (errorEncontradoSintactico) {
                        System.out.println("Error en sentencia iterativa");
                        return;    
                    }
                    if (p.token == 118) {// )
                        p = p.siguienteNodo;
                        if (p.token == 119) {// {
                            p = p.siguienteNodo;
                            while (p.token != 120) {
                                statement();
                            }
                            if (p.token == 120) {// }
                                p = p.siguienteNodo;
                                break;
                            } else {
                                System.out.println("Error, se espera: }");
                                return;
                            }
                        } else {
                            System.out.println("Error, se espera: {");
                            return;
                        }
                    } else {
                        System.out.println("Error, se espera: )");
                        return;
                    }
                } else {
                    System.out.println("Error, se espera: (");
                    return;
                }

            case 206:// print
                p = p.siguienteNodo;
                if (p.token == 117) {// (
                    p = p.siguienteNodo;
                    while (p.token == 100 || p.token == 124) {// id O ,
                        if (p.token == 100 || p.token == 124) {// id O ,
                            p = p.siguienteNodo;
                        } else {
                            System.out.println("Error, se espera: id o ,");
                            return;
                        }
                    }
                    if (p.token == 118) {// )
                        p = p.siguienteNodo;
                        if (p.token == 125) {// ;
                            p = p.siguienteNodo;
                            break;
                        } else {
                            System.out.println("Error, se espera: ;");
                            return;
                        }
                    } else {
                        System.out.println("Error, se espera: )");
                        return;
                    }
                }

            case 213:// getValue
                p = p.siguienteNodo;
                if (p.token == 117) {// (
                    p = p.siguienteNodo;
                    if (p.token == 118) {// )
                        p = p.siguienteNodo;
                        if (p.token == 125) {// ;
                            p = p.siguienteNodo;
                            break;
                        } else {
                            System.out.println("Error, se espera: ;");
                            return;
                        }
                    } else {
                        System.out.println("Error, se espera: )");
                        return;
                    }
                } else {
                    System.out.println("Error, se espera: (");
                    return;
                }

            case 100:// id
                p = p.siguienteNodo;
                if (p.token == 123) {// =
                    p = p.siguienteNodo;
                    if (p.token == 100 || p.token == 101) {// id o numero
                        while (p.token != 125) {
                            exp_simple();
                        }
                    } else {
                        System.out.println("Error, se espera: id o numero");
                        errorEncontradoSintactico = true;
                        return;
                    }

                    if (p.token == 125) {// ;
                        p = p.siguienteNodo;
                        break;
                    } else {
                        System.out.println("Error, se espera: ;");
                        return;
                    }
                } else {
                    System.out.println("Error, se espera: =");
                    errorEncontradoSintactico = true;
                    return;
                }
            default:
                System.out.println("Se espera: statement");
                errorEncontradoSintactico = true;
                p = p.siguienteNodo;
                break;
        }
    }

    private boolean tipos() {
        switch (p.token) {
            case 209:// int
                return true;
            case 208:// float
                return true;
            case 212:// string
                return true;
            default:
                System.out.println("Error, se espera definir tipo de variable");
                return false;
        }
    }

    private void exp_cond() {
        
        if (exp_simple()) {
            if (op_rel()) {
                if (exp_simple()) {//
                } else {
                    //System.out.println("Error de expresión simple");
                }
            } else {
                System.out.println("Error, se espera: operador relacional");
                errorEncontradoSintactico = true;
                return;
            }
        }
    }

    private boolean exp_simple() {
        if (errorEncontradoSintactico) {
            return false;
        }
        if (signo()) {// manda a llamar signo
            if (termino()) {// manda a llamar termino
                return true;
            }

        } else if (termino()) {// manda a llamar termino
            return true;
        } else if (exp_simple()) {// si no es termino, manda a llamar expresion simple
            if (op_aditivo()) {// manda a llamar operador aditivo
                if (termino()) { // manda a llamar termino
                    return true;
                }
            }
        } else {
            System.out.println("Error de expresión simple, se espera: signo, termino, expresión simple o operador aditivo");
            errorEncontradoSintactico = true;
            
        }
        return false;
    }

    private boolean termino() {
        if (factor()) {// manda a llamar factor
            return true;
        } else // if(termino()){//manda a llamar termino
        if (op_mult()) { // manda a llamar a operador multiplicativo
            if (factor()) {// manda a llamar a factor
                return true;
            }
        }
        // }
        return false;
    }

    private boolean factor() {
        switch (p.token) {
            case 100: // id
                p = p.siguienteNodo;
                return true;
            case 101: // num
                p = p.siguienteNodo;
                return true;
            case 117: // (
                p = p.siguienteNodo;
                if (exp_simple()) {
                    if (p.token == 118) { // )
                        p = p.siguienteNodo;
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            case 116: // !
                p = p.siguienteNodo;
                if (factor()) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    private boolean op_mult() {
        switch (p.token) {
            case 105: // *
                p = p.siguienteNodo;
                return true;
            case 106: // /
                p = p.siguienteNodo;
                return true;
            case 114: // &&
                p = p.siguienteNodo;
                return true;
            default:
            errorEncontradoSintactico = true;
                return false;
        }
    }

    private boolean op_rel() {
        switch (p.token) {
            case 109:// >
                p = p.siguienteNodo;
                return true;
            case 108:// <
                p = p.siguienteNodo;
                return true;
            case 111:// >=
                p = p.siguienteNodo;
                return true;
            case 110:// <=
                p = p.siguienteNodo;
                return true;
            case 113:// !=
                p = p.siguienteNodo;
                return true;
            case 112:// ==
                p = p.siguienteNodo;
                return true;
            default:
                return false;
        }
    }

    private boolean op_aditivo() {
        switch (p.token) {
            case 103: // +
                p = p.siguienteNodo;
                return true;
            case 104: // -
                p = p.siguienteNodo;
                return true;
            case 115: // ||
                p = p.siguienteNodo;
                return true;
            default:
                return false;
        }
    }

    private boolean signo() {
        if (p.token == 103 || p.token == 104) {
            p = p.siguienteNodo;
            return true;
        } else {
            return false;
        }
    }

    private void imprimirNodos() {
        p = cabeza;
        while (p != null) {
            System.out.println(p.lexema + " " + p.token + " " + p.renglon);
            p = p.siguienteNodo;
        }

    }

    private void validarSiEsPalabraReservada() {
        for (String[] palReservada : palReservadas) {
            if (lexema.equals(palReservada[0])) {
                valorMT = Integer.valueOf(palReservada[1]);
            }
        }
    }

    private void imprimirMensajeError() {
        if (caracter != -1 && valorMT >= 500) {
            for (String[] errore : errores) {
                if (valorMT == Integer.valueOf(errore[1])) {
                    System.out.println("El error encontrado es: " + errore[0] + " error " + valorMT + " caracter "
                            + caracter + " en el renglon " + numRenglon);
                }
            }
            errorEncontrado = true;
        } else if (caracter == -1 && valorMT >= 500) {
            for (String[] errore : errores) {
                if (valorMT == Integer.valueOf(errore[1])) {
                    System.out.println("El error encontrado es: " + errore[0] + " error " + valorMT + " caracter "
                            + caracter + " en el renglon " + numRenglon);
                }
            }
            errorEncontrado = true;
        }
    }

    private void insertarNodo() {
        nodo nodo = new nodo(lexema, valorMT, numRenglon);

        if (cabeza == null) {
            cabeza = nodo;
            p = cabeza;
        } else {
            p.siguienteNodo = nodo;
            p = nodo;
        }
    }
}
