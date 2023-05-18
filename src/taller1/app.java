package taller1;

import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class app {
	
	/*MAIN*/
	/*Este es el método main que lee tres archivos de texto y los almacena en matrices. Si alguno de los archivos está vacío, se imprime un
	 *  mensaje de advertencia. Luego se realiza un informe inicial sobre los datos, se verifica si hay errores y se inicia sesión en el sistema
	 *   si no hay errores.*/
	public static void main(String[] args) throws IOException {
		File archivo1 = new File("datos_ia.txt");
		File archivo2 = new File("datos_creadores.txt");
		File archivo3 = new File("datos_usuarios.txt");

		boolean proceder;
		proceder=estaVacio(archivo1); proceder=estaVacio(archivo2); proceder=estaVacio(archivo3);

		if(proceder==false){
			System.out.println("No se puede proceder con un archivo(s) vacio");
		}else {
			//Datos_ia.txt
	        String archivoIa = "datos_ia.txt";
	        String[][] datosIa = leerDatos(archivoIa);
	        //Datos_creadores.txt
	        String archivoCreadores = "datos_creadores.txt";
	        String[][] datosCreadores = leerDatos(archivoCreadores);
	        //Datos_usuarios.txt
	        String archivoUsuarios = "datos_usuarios.txt";
	        String[][] datosUsuarios = leerDatos(archivoUsuarios);
	        
	        //Reporte inicial
	        int errores1 = reporteInicial(datosIa);
	        int errores2 = reporteInicial(datosCreadores);
	        int errores3 = reporteInicial(datosUsuarios);

	        if(errores1 == 0 && errores2 == 0 && errores3 == 0){
	        	//Inicio de sesion
		        login(datosIa,datosCreadores,datosUsuarios);
	        }else if (errores1 > 0 || errores2 > 0 || errores3 > 0) {
	        	System.out.println("Datos afectados que se debieron borrar fueron: ");
	        	System.out.println("datos_ia.txt: "+errores1);
	        	System.out.println("datos_creadores.txt: "+errores2);
	        	System.out.println("datos_usuarios.txt: "+errores3);
	        	//Inicio de sesion
		        login(datosIa,datosCreadores,datosUsuarios);
	        }
	        
	        
		}
	
    }//Main



	public static boolean estaVacio(File archivo){
		boolean proceed=true;

		if(archivo.length()==0){
			System.out.println("El archivo " + archivo.getName()+ " esta vacio");
			return proceed = false;
		}else{
			return proceed;
		}
	}

	/*LEER DATOS ARCHIVOS TXT*/
	/*Esta función recibe como parámetro el nombre de un archivo y retorna una matriz de Strings que contiene los datos del archivo separados por comas.
	 *  La función utiliza un BufferedReader para leer el archivo línea por línea y un split para separar los datos de cada línea en las diferentes 
	 *  columnas de la matriz.*/
    public static String[][] leerDatos(String archivo) {
        String[][] datos = null;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            int numFilas = contarFilas(archivo);
            int numColumnas = contarColumnas(archivo);
            datos = new String[numFilas][numColumnas];
            String linea;
            int i = 0;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                for (int j = 0; j < partes.length; j++) {
                    datos[i][j] = partes[j].toLowerCase();
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datos;
    }
    
    /* RF4 */
    /*La función `corromperArchivo` recibe una matriz y un archivo como parámetros. La función genera un número aleatorio de veces para reemplazar el
     *  valor en una posición aleatoria de la matriz por la cadena de caracteres "¡@IA¿WIN$#". Finalmente, la función actualiza el 
     *  archivo con la matriz modificada.*/
    public static void corromperArchivo(String[][]matriz,File archivo) throws IOException{
    	Random random = new Random();
    	int n = 1; //Remplazar dos veces el String ¡@IA¿WIN$#
    	for(int i=0;i<n;i++) {
    		int fila = random.nextInt(matriz.length);
    		int columna = random.nextInt(matriz[0].length);
    		matriz[fila][columna] = "¡@IA¿WIN$#";
    	}
    	actualizarArchivo(archivo,matriz);
    }
    
    /*Esta función recibe una matriz de String y busca cuántas veces se encuentra el dato "¡@IA¿WIN$#" dentro de ella.
     *  Retorna el número de veces que se encontró dicho dato.*/
    public static int reporteInicial(String[][] matriz) {
    	String dato = "¡@ia¿win$#";
    	int cont = 0;
    	
    	for(int i=0;i<matriz.length;i++) {
    		for(int j=0;j<matriz[0].length;j++) {
    			if(matriz[i][j].equals(dato)){
    				cont++;
    			}
    		}
    	}
    	
    	
    	return cont;
    }
    
    /*ACTUALIZAR ARCHIVO*/
    /*La función "actualizarArchivo" recibe como parámetros un objeto File que representa el archivo que se desea actualizar y una matriz de 
     * Strings que contiene los nuevos datos a escribir en el archivo. La función abre un escritor de archivos, escribe los datos de la matriz en el 
     * archivo de texto, y cierra el escritor. Si ocurre un error de entrada o salida durante la operación, se imprime un mensaje de error.*/
    public static void actualizarArchivo(File archivo,String[][] matriz) {
    	try{
    		//Abrir escritor
    		BufferedWriter escribir = new BufferedWriter(new FileWriter(archivo));
    		//Escribir datos de la matriz en el archivo de texto
    		for(int i=0;i<matriz.length;i++) {
    			for(int j=0;j<matriz[i].length;j++) {
    				escribir.write(matriz[i][j].trim());
    				if(j != matriz[i].length -1){
    					escribir.write(",");
    				}
    			}	
    			escribir.write("\n");
    		}
    		//cerrar escrtor
    		escribir.close();
    	}catch(IOException e) {
    		System.out.println("Archivo no encontrado");
    	}
    	
    }
    
    
    /*CONTAR CANTIDAD DE N FILAS Y COLUMNAS E IMPRIMIR ARREGLOS*/
    
    //Cuenta la cantidad de filas que contiene una matriz
    public static int contarFilas(String archivo) {
        int numFilas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            while (br.readLine() != null) {
                numFilas++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numFilas ;
    }
    
    /*IMPRIMIR DATOS*/
    
    /*Variable solo utilizada para que imprima los datos */
    public static void imprimirMatriz(String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length ; j++) {
                System.out.print(datos[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    //Imprimir el vector selecionado
    public static void imprimirVector(String[] vector) {
    	for(int i=0;i<vector.length;i++) {
    		System.out.println(vector[i]);
    	}
    }
    
    //Funcion encargada de contar el numero de columnas que tiene un archivo de texto para luego ser retornada y utilizada en la lectura de archivos
    public static int contarColumnas(String archivo) {
        int numColumnas = 0;
        try {
            // Abrir archivo y leer matriz
            Scanner sc = new Scanner(new File(archivo));
            String linea = sc.nextLine();
            String[] partes = linea.split(",");
            numColumnas= partes.length;
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + archivo);
        }
        return numColumnas;
    }
    
    /*MENU USUARIO NORMAL*/
    
    /*MENU ESPECIALIDAD MEJORA IA*/
    /*Esta función recibe como parámetros una matriz, un número de columna1 y un número de columna2. Crea una nueva matriz de 2x2 y 
     * copia los datos de la columna1 y columna2 de la matriz original en la nueva matriz. Finalmente, retorna la nueva matriz.*/
    public static String[][] obtenerDatosIa(String[][] matriz, int columna1,int columna2){
    	//Obtener cantidad de filas de la matriz
    	int filas = matriz.length;
    	
    	//Crear matriz 2x2 para almacenar la matriz resultante
    	String[][] matrizR = new String[filas][2];
    	
    	//recores la matriz para copiar valores
    	for(int i=0;i<filas;i++) {
    		matrizR[i][0] = matriz[i][columna1];
    		matrizR[i][1] = matriz[i][columna2];
    	}
    	return matrizR;
    }
    
    /*Esta función recibe como parámetros un archivo, una matriz, un número de fila y un nuevo dato. Obtiene información de la matriz en la fila dada y 
     * la usa para actualizar el archivo. Si el tipo de IA es "Simple", la función imprime un mensaje. Si el tipo de IA es "Mediana" o "Avanzada" y l
     * a cantidad de mejora ingresada es mayor que el límite, la función imprime un mensaje. De lo contrario, actualiza la cantidad de mejoras de IA 
     * en la matriz y llama a las funciones actualizarArchivo y corromperArchivo para actualizar el archivo.*/
    public static void remplazarDatoMejoraIa(File archivo,String[][] matriz,int fila,String nuevoDato) throws IOException {
    	//Variables
    	String dato1 = nuevoDato; //Cantidad de mejores a añadir
    	String dato2 = matriz[fila][5];//Contiene la cantidad de mejoras de una ia
    	String tipoIa = matriz[fila][3];//El tipo de ia (Simple-Mediana-Avanzada)
    	int dato3 = 0;
    	
    	
    	if(tipoIa.equals("simple")) {
    		System.out.println("No se puede mejorar esta ia");
    	}else if (tipoIa.equals("mediana")) {
    		if(Integer.parseInt(dato1) > 5) {
    			System.out.println("La cantidad de mejora que ingreso supera el limite que es de 5");
    		}else {
    			dato3 = Integer.parseInt(dato2) + Integer.parseInt(dato1);
            	matriz[fila][5] = String.valueOf(dato3);
            	//Funcion actualizar texto
            	actualizarArchivo(archivo,matriz);
            	corromperArchivo(matriz,archivo);
            	
    		}
    	}else if (tipoIa.equals("avanzada")) {
    		if(Integer.parseInt(dato1) > 30) {
    			System.out.println("La cantidad de mejora que ingreso supera el limite que es de 5");
    		}else {
    			dato3 = Integer.parseInt(dato2) + Integer.parseInt(dato1);
            	matriz[fila][5] = String.valueOf(dato3);
            	//Funcion actualizar texto
            	actualizarArchivo(archivo,matriz);
            	corromperArchivo(matriz,archivo);
    		}
    	}
    }

    /*MENU ESPECIALIDAD PROGAMADOR*/
    
    /* Esta función recibe como parámetros un archivo, una matriz, un número de fila y un nuevo dato. Actualiza la velocidad de aprendizaje
     *  de IA en la matriz y llama a las funciones actualizarArchivo y corromperArchivo para actualizar el archivo.*/
    public static void remplazarDatoVelocidad(File archivo,String[][] matriz,int fila,String nuevoDato) throws IOException {
    	String dato1 = nuevoDato;
    	matriz[fila][2] = dato1;
    	actualizarArchivo(archivo,matriz);
    	corromperArchivo(matriz,archivo);
    }
    
    /*Esta función recibe como parámetros un archivo, una matriz, un número de fila y un nuevo dato. Actualiza el tipo de IA 
     * en la matriz y llama a las funciones actualizarArchivo y corromperArchivo para actualizar el archivo.*/
    public static void remplazarDatoTipoIa(File archivo,String[][] matriz,int fila,String nuevoDato) throws IOException {
    	String dato1 = nuevoDato;
    	matriz[fila][3] = dato1;
    	actualizarArchivo(archivo,matriz);
    	corromperArchivo(matriz,archivo);
    }
    
    
    /*MENU ESPECIALIDAD IA MASTER*/
    
    /* Esta función recibe como parámetros dos matrices y un número de fila. Obtiene la especialidad del usuario de la segunda matriz y 
     * muestra un mensaje de bienvenida con la opción de ingresar a uno de los dos menús disponibles. Luego, usa un escáner para obtener la 
     * opción del usuario y llama a la función correspondiente (loginNormalIa o loginNormalProga) según la opción ingresada por el usuario.*/
    public static void loginNormalMaster(String[][] matriz1,String[][] matriz2, int numFila) throws IOException {
    	//Variables
    	String opcion = "",especialidad = "";

    	//Logica para usuario con la especialidad
    	especialidad = matriz2[numFila][2]; //Guarda al especialidad que tiene el usuario almacenada en datos_creadores.txt
    	
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Usted tiene la especialidad IA Master,Tiene acceso a menu Progamador y menu Mejora IA");
    	System.out.println("¿A que menu desea ingresar? (1) Mejora IA ; (2) Progamador");
    	opcion = sc.nextLine();
    	while(!opcion.equals("1") && !opcion.equals("2")){
    	    System.out.println("Error, ingrese nuevamente dato, ¿A cuál menú desea ingresar? (1) Mejora IA; (2) Programador");
    	    opcion = sc.nextLine();    
    	}
    	if(opcion.equals("1")) {
        	loginNormalIa(matriz1,matriz2,numFila);
    	}
    	if(opcion.equals("2")) {
            loginNormalProga(matriz1,matriz2,numFila);
    	}
    	
    	sc.close();
    }
    
    
    /*SUBMENU IA*/
    
    /* Esta función recibe como parámetros dos matrices. Usa un escáner para obtener la opción del usuario de cómo quiere ordenar las IAs 
     * y muestra la matriz ordenada según la opción seleccionada. La función sigue ejecutándose hasta que el usuario seleccione la opción de salir.*/
    public static void submenuIaOrdenar(String[][] matriz1,String[][] matriz2){
    	//Variables
    	String opcion;
    	
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	
    	do {
    		//Logica Menu ordenar
        	System.out.println("¿Con que orden quieres ver las ias?");
        	System.out.println("1- nombre IA ; 2- año creacion ; 3- velocidad aprendizaje ; 4- tipo ");
        	System.out.println("5- Creador ; 6- Cantidad de mejoras ; 7-Salir");
        	opcion = sc.nextLine();
        	
    		switch(opcion) {
    		case "1":
    			ordenarMatriz(matriz1,0);
    			imprimirMatriz(matriz1);
    			break;
    		case "2":
    			ordenarMatriz(matriz1,1);
    			imprimirMatriz(matriz1);
    			break;
    		case "3":
    			ordenarMatriz(matriz1,2);
    			imprimirMatriz(matriz1);
    			break;
    		case "4":
    			ordenarMatriz(matriz1,3);
    			imprimirMatriz(matriz1);
    			break;
    		case "5":
    			ordenarMatriz(matriz1,4);
    			imprimirMatriz(matriz1);
    			break;
    		case "6":
    			ordenarMatriz(matriz1,5);
    			imprimirMatriz(matriz1);
    			break;
    		case "7":
    			System.out.println("Saliendo...");
    			break;
    		default:
    			System.out.println("Opcion invalida");
    			break;
    		}
    	}while(!opcion.equals("7"));
    	
		
    	sc.close();
    }

    /* Ordena la matriz según una columna especificada, utilizando el algoritmo de ordenamiento burbuja.*/
    public static void ordenarMatriz(String[][] matriz, int columna) {
        int numFilas = matriz.length;
        for (int i = 0; i < numFilas - 1; i++) {
            for (int j = 0; j < numFilas - i - 1; j++) {
                if (matriz[j][columna].compareTo(matriz[j + 1][columna]) > 0) {
                    String[] temp = matriz[j];
                    matriz[j] = matriz[j + 1];
                    matriz[j + 1] = temp;
                }
            }
        }
    }
    
    /*busca un elemento en la matriz y lo reemplaza por un nuevo valor especificado por el usuario. Luego, actualiza el archivo en disco con los nuevos datos y
     *  corrompe intencionalmente el archivo para demostrar que se puede recuperar la información a partir de los datos almacenados en la matriz.*/
    public static void submenuIaEditar(String[][] matriz,String elemento,String nuevoValor,File archivo) throws IOException{
    	boolean encontrado = false;
    	for(int i=0; i < matriz.length;i++) {
    		for(int j=0; j<matriz[0].length;j++) {
    			if(matriz[i][j].equals(elemento)) {
    				matriz[i][j] = nuevoValor;
    				encontrado = true;
    				actualizarArchivo(archivo,matriz);
    				corromperArchivo(matriz,archivo);
    				break;
    			}
    		}
    		if(encontrado) break;
    	}
    	if(!encontrado) {
    		System.out.println("El elemento especificado no existe en matriz");
    	}
    }

    
    /*SUBMENU USUARIOS Y CREADORES*/
    
    /* muestra un menú al usuario para que seleccione qué tipo de usuario desea ver y, en función de la selección, se llama a la función 
     * "contarDatosTipoUsuario" que cuenta cuántos usuarios del tipo seleccionado hay en la matriz y muestra el porcentaje correspondiente.*/
    public static void submenuUsuariosCantidadTipo(String[][] matriz) {
    	//Escaner
    	Scanner sc = new Scanner(System.in);
    	String opcion, dato = "";
    	
    	do {
    		System.out.println("¿Que tipo de usuario quieres ver?");
        	System.out.println("1- Admnistrador");
        	System.out.println("2- Normal");
        	System.out.println("3- Salir");
        	opcion = sc.nextLine();
        	switch(opcion) {
        	case "1":
        		dato = "administrador";
        		contarDatosTipoUsuario(matriz,dato);
        		break;
        	case "2":
        		dato = "normal";
        		contarDatosTipoUsuario(matriz,dato);
        		break;
        	case "3":
        		System.out.println("Saliendo...");
        		break;
        	default:
        		break;
        	}
    	}while(!opcion.equals("3"));
    	
    	sc.close();
    }
    
    /*cuenta cuántos usuarios de un tipo específico hay en una matriz de cadenas y muestra el porcentaje correspondiente.*/
    public static void contarDatosTipoUsuario(String[][] matriz, String cadenaBuscada) {
    	//Variables
    	int contNormal = 0,contAdmin = 0,total = matriz.length;
    	double porNormal = 0,porAdmin = 0;
    	
    	//Comtar usuarios en la matriz
    	for(int i=0;i<matriz.length;i++) {
    		//En la columna dos estan almacenados los tipos de usuario en el datos_usuarios.txt
    		String tipoUsuario = matriz[i][2];
    		switch(tipoUsuario) {
    		case "normal":
    			contNormal++;
    			break;
    		case "administrador":
    			contAdmin++;
    			break;
    		default:
    			break;
    		}
    	}
    	
    	//Calcular y desplegar tipo de usuario
    	switch(cadenaBuscada) {
    		case "normal":
    			porNormal = (double) contNormal / total * 100;
    			System.out.println("El porcentaje de los usuarios normales son: " + porNormal);
    			break;
    		case "administrador":
    			porAdmin = (double) contAdmin / total * 100;
    			System.out.println("El porcentaje de los usuarios administrador son: " + porAdmin);
    		default:
    			break;
    	}
    }
    
    /*agrega una nueva fila a dos matrices diferentes (datos_creadores.txt y datos_usuarios.txt) y s
     * olicita al usuario que ingrese los datos correspondientes para cada columna. Finalmente, muestra la matriz actualizada.*/
    public static void añadirUsuario(String[][] matriz1,String[][] matriz2,String[][] matriz3) {
    	//Matriz 1 = datos_creadores.txt ; matriz3 = datos_usuarios.txt
    	
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	
    	String opcion;
    	
    	System.out.println("Quiere ingresar usuaio o creador");
    	System.out.println("1- Usuario ; 2- Creador");
    	opcion = sc.nextLine();
    	while(!opcion.equals("1") && !opcion.equals("2")){
    		System.out.println("Error, ingrese dato nuevamente:");
        	System.out.println("1- Usuario ; 2- Creador");
        	opcion = sc.nextLine();
    	}
    	if(opcion.equals("1")) {
			agregarFilaNueva(matriz3);
    		System.out.println("Ingrese usuario");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz3,opcion,0);
			System.out.println("Ingrese contraseña");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz3,opcion,1);
			System.out.println("Ingrese tipo usuario");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz3,opcion,2);
			System.out.println("Ingrese Creador");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz3,opcion,3);
			imprimirMatriz(matriz3);
    	}else if(opcion.equals("2")) {
			agregarFilaNueva(matriz2);
    		System.out.println("Ingrese creador");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz2,opcion,0);
			System.out.println("Ingrese experiencia");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz2,opcion,1);
			System.out.println("Ingrese espcialidad");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz2,opcion,2);
			System.out.println("Ingrese edad");
			opcion = sc.nextLine();
			agregarDatoEnColumna(matriz2,opcion,3);	
			imprimirMatriz(matriz2);
    	}
    	
   		
    	
    	sc.close();
    }

    /*Este método toma una matriz de cadenas matriz como entrada y agrega una nueva fila al final de la matriz. 
     * La nueva fila consta de elementos nulos y tiene la misma longitud que la primera fila de la matriz. 
     * El método no devuelve nada, sino que modifica directamente la matriz de entrada.*/
    public static void agregarFilaNueva(String[][] matriz) {
    	String[] nuevaFila = new String[matriz[0].length];
        for (int i = 0; i < nuevaFila.length; i++) {
            nuevaFila[i] = null;
        }
        String[][] nuevaMatriz = new String[matriz.length + 1][matriz[0].length];
        for (int i = 0; i < matriz.length; i++) {
            nuevaMatriz[i] = matriz[i];
        }
        nuevaMatriz[matriz.length] = nuevaFila;
    }
    
    /* Este método toma una matriz de cadenas matriz, un valor de cadena dato y un índice de columna columna como entrada.
     *  Agrega el valor de cadena dato en la última fila de la columna especificada en la matriz. El método no devuelve nada, 
     *  sino que modifica directamente la matriz de entrada.*/
    public static void agregarDatoEnColumna(String[][] matriz,String dato,int columna) {
        matriz[matriz.length - 1][columna] = dato;
    }
    
    /*Este método toma dos matrices de cadenas matriz1 y matriz2 como entrada y muestra un menú para que el usuario 
     * seleccione si desea eliminar usuarios, creadores o ambos de las matrices. El método llama al método eliminarUsuarioCreador 
     * según la opción seleccionada por el usuario. El bucle do-while se ejecuta hasta que el usuario selecciona la opción de salida.*/
    public static void 	menuEliminarUsuarioCreador(String[][] matriz1,String[][] matriz2) {
    	//Matriz 1 = datos_usuarios.txt ; matriz2 = datos_creadores.txt
    	
    	//Escaner
    	Scanner sc = new Scanner(System.in);
    	
    	String opcion;
    	
    	do {
    		System.out.println("¿Desea eliminar Usuario y/o creador?");
        	System.out.println("1- Usuario");
        	System.out.println("2- Creador");
        	System.out.println("3- Ambas");
        	System.out.println("4- Salir");
        	opcion = sc.nextLine();
        	
        	switch(opcion) {
        	case "1":
        		eliminarUsuarioCreador(matriz1,3);
        		break;
        	case "2":
        		eliminarUsuarioCreador(matriz2,0);
        		break;
        	case "4":
        		System.out.println("Saliendo");
        		break;
        	default:
        		System.out.println("Opcion invalida");
        		break;
        	}
    	}while(!opcion.equals("4"));

    }
    
    
    /*Este método toma una matriz de cadenas matriz y un índice de columna columna como entrada. Busca en la matriz la cadena de texto 
     * ¡@IA¿WIN$# en la columna especificada y si la encuentra, la reemplaza por null. En caso contrario, muestra un mensaje de que el dato
     *  no es corrupto. Este método no devuelve nada, sino que modifica directamente la matriz de entrada.*/
    public static void eliminarUsuarioCreador(String[][] matriz,int columna) {	
    	for(int i=0;i<matriz.length;i++) {
    		if(matriz[i][columna].equals("¡@IA¿WIN$#")) {
    			System.out.println("El dato es corrupto");
				matriz[i][columna] = null;
				System.out.println("El dato quedo eliminado");
				imprimirMatriz(matriz);
				return;
    		}
    	}
    	System.out.println("El dato no es corupto");
    }
    
    /* REPORTES*/
    
    /*Este método calcula la edad media de los creadores en la matriz, utilizando la columna 3 de la matriz
     *  (que supuestamente contiene las edades de los creadores). El método itera sobre cada fila de la matriz, 
     *  suma las edades de cada creador y cuenta el número total de creadores. Finalmente, calcula la media dividiendo 
     *  la suma total de edades por el número de creadores y la imprime en la consola.*/
    public static void edadMediaCreadores(String[][] matriz) {
    	int suma = 0,cont=0;
    	
    	for(int i=0;i<matriz.length;i++) {
    		suma += Integer.parseInt(matriz[i][3]);
    		cont++;
    	}
    	double media = (double) suma / cont;
    	System.out.println("La edad media de los creadores son");
    	System.out.println(media);
    }
    
    /* Este método calcula la edad media de las IA en la matriz, utilizando la columna 1 de la matriz (que supuestamente contiene 
     * los años de nacimiento de las IA). El método itera sobre cada fila de la matriz, calcula la edad de cada 
     * IA restando su año de nacimiento del año actual (2023) y sumando el resultado. A continuación, cuenta el número total
     *  de IA y calcula la media dividiendo la suma total de edades por el número de IA y la imprime en la consola.*/
    public static void edadMediaIa(String[][] matriz) {
    	int cont=0,suma=0;
    	
    	for(int i=0;i<matriz.length;i++) {
    		suma += (2023 - Integer.parseInt(matriz[i][1]));
    		cont++;
    	}
    	
    	double media = (double) suma / cont;
    	System.out.println("La edad media de las ia son");
    	System.out.println(media);
    }
    
    /*Este método ordena la matriz según la experiencia de los creadores de forma decreciente. El método utiliza el 
     * algoritmo de selección para ordenar la matriz en función de la columna 1 (que supuestamente contiene la experiencia de los creadores).
     *  El método también crea un arreglo de nombres de creadores ordenados por experiencia y lo imprime en la consola.*/
    public static void ordenarCreadoresPorExperiencia(String[][] matriz) {
        // Ordenar matriz según años de experiencia de forma decreciente
        for (int i = 0; i < matriz.length - 1; i++) {
            int indiceMayor = i;
            for (int j = i + 1; j < matriz.length; j++) {
                if (Integer.parseInt(matriz[j][1]) > Integer.parseInt(matriz[indiceMayor][1])) {
                    indiceMayor = j;
                }
            }
            String[] temp = matriz[indiceMayor];
            matriz[indiceMayor] = matriz[i];
            matriz[i] = temp;
        }

        // Crear arreglo con nombres de creadores ordenados por experiencia
        String[] nombresCreadores = new String[matriz.length];
        for (int i = 0; i < matriz.length; i++) {
            nombresCreadores[i] = matriz[i][0];
        }

        // Devolver arreglo de nombres de creadores ordenados por experiencia
        System.out.println("Creadores segun años de experiencia de manera decreciente");
        imprimirVector(nombresCreadores);
    }
    
    
    /*Este método calcula el promedio de la velocidad de aprendizaje de las IA en la matriz. El método itera sobre cada fila de la matriz,
     *  calcula la velocidad de aprendizaje de cada IA en días multiplicando la columna 2 (que supuestamente contiene la velocidad de 
     *  aprendizaje en horas) por 24 y restando la cantidad de mejoras realizadas por la IA multiplicando la columna 5 (que supuestamente 
     *  contiene la cantidad de mejoras) por 6. A continuación, suma el resultado de cada IA y cuenta el número total de IA. Finalmente,
     *   calcula el promedio dividiendo la suma total por el número total de IA y la imprime en la consola.*/
    public static void promVelocidadesIas(String[][] matriz) {
    	int filas=0,velocidad = 0,mejoras = 0,calculo=0,suma=0,total=0,prom=0;
    	
    	filas = matriz.length;
    	
    	for(int i=0;i<filas;i++) {
    		velocidad = Integer.parseInt(matriz[i][2]) * 24; //Velocidad en dias
    		mejoras =Integer.parseInt(matriz[i][5]) * 6;  //Cantidad de mejoras, reduce 6 horas
    		calculo = (velocidad - mejoras) / 24;
    		
    		suma += calculo;
    		total++;
    	}
    	
    	prom = suma / total;
    	System.out.println("Promedio Velocidad aprendizaje IA");
    	System.out.println(prom);
    }
    
    /*Este método cuenta la cantidad de IA en la matriz de cada tipo (simple, media y avanzada). El método itera sobre cada fila de la matriz,
     *  verifica el tipo de cada IA en la columna 3 y cuenta la cantidad de IA de cada tipo. Finalmente, imprime la cantidad de IA de c
     *  ada tipo en la consola.*/
    public static void cantidadIaTipo(String[][] matriz) {
    	int simple=0,media=0,avanzada=0;
    	String tipo ="";
    	
    	for(int i=0;i<matriz.length;i++){
    		tipo = matriz[i][3];
    		if(tipo.equals("simple")){
    			simple++;
    		}else if(tipo.equals("mediana")){
    			media++;
    		} else if(tipo.equals("avanzada")){
    			avanzada++;
    		}
    	}
    	
    	System.out.println("Cantidad de ias simples: "+simple);
    	System.out.println("Cantidad de ias medianas: "+media);
    	System.out.println("Cantidad de ias avanzadas: "+avanzada);
    }

    /*Este método cuenta la cantidad de creadores en la matriz con cada especialidad (mejora IA, programador e IA master).
     *  El método itera sobre cada fila de la matriz, verifica la especialidad de cada creador en la columna 2 y cuenta la 
     *  cantidad de creadores con cada especialidad. Finalmente, imprime la cantidad de creadores con cada especialidad en la consola.*/
    public static void cantidadCreadoresEspecialidad(String[][] matriz) {
    	String especialidad = "";
    	int mejora=0,progamador=0,iaMaster=0;
    	
    	for(int i=0;i<matriz.length;i++){
    		especialidad = matriz[i][2];
    		if(especialidad.equals("mejora ia")){
    			mejora++;
    		}else if(especialidad.equals("progamador")){
    			progamador++;
    		} else if(especialidad.equals("ia master")){
    			iaMaster++;
    		}
    	}
    	System.out.println("Cantidad de creadores con especialidad mejoras ias: "+mejora);
    	System.out.println("Cantidad de creadores con especialidad progamador: "+progamador);
    	System.out.println("Cantidad de creadores con especialidad ia master"+iaMaster);
    }
    
   /* la cual toma dos matrices de cadenas de texto como entrada y no tiene valor de retorno. 
    La función busca en la primera matriz de entrada (matriz1) por filas que contengan diferentes
    tipos de inteligencia artificial (IA): "Simple", "Mediana" y "Avanzada". Para cada tipo de IA,
    la función realiza una serie de cálculos utilizando información de la misma fila en la matriz1
    y en la matriz2. Finalmente, la función imprime el nombre de la IA y el resultado de los
    cálculos para cada tipo de IA en la consola. */
    
    public static void iaRelevarse(String[][] matriz1,String[][] matriz2) {
    	int añoCreacion=0,experiencia=0;
    	int velocidad = 0,calculo=0;
    	String tipo ="";
    	System.out.println("IA más probables para revelarse: ");
    	for(int i=0;i<matriz1.length;i++) {
    		tipo = matriz1[i][3];
    		if(tipo.equals("simple")){
    			System.out.println("Ia Simple: "+matriz1[i][0]);
    			añoCreacion = Integer.parseInt(matriz1[i][1]);
    			velocidad = Integer.parseInt(matriz1[i][2]);
    			experiencia = Integer.parseInt(matriz2[i][1]) / 30;
    			calculo = (añoCreacion * 5 * experiencia) / velocidad;
    			System.out.println(calculo);
    		}else if(tipo.equals("mediana")){
    			System.out.println("Ia Media: "+matriz1[i][0]);
    			añoCreacion = Integer.parseInt(matriz1[i][1]);
    			velocidad = Integer.parseInt(matriz1[i][2]);
    			experiencia = Integer.parseInt(matriz2[i][1]) / 30;
    			calculo = (añoCreacion * 10 * experiencia) / velocidad;
    			System.out.println(calculo);
    		} else if(tipo.equals("avanzada")){
    			System.out.println("Ia Avanzada: "+matriz1[i][0]);
    			añoCreacion = Integer.parseInt(matriz1[i][1]);
    			velocidad = Integer.parseInt(matriz1[i][2]);
    			experiencia = Integer.parseInt(matriz2[i][1]) / 30;
    			calculo = (añoCreacion * 15 * experiencia) / velocidad;
    			System.out.println(calculo);
    		}
    	}
    
    }
    
    /* LOGIN*/
    
    public static void login(String[][] matriz1,String[][] matriz2, String[][] matriz3) throws IOException{

    	//Abrir escaner
    	Scanner sc = new Scanner(System.in);
    	
    	//Variables
    	String opcion = "",especialidad = "";
    	
    	//Abrir archivos 
    	 File archivoIa = new File("datos_ia.txt");
    	 File archivoCreadores = new File("datos_creadores.txt");
    	 File archivoUsuarios = new File("datos_usuarios.txt");
     
     	 //Variables
         String nombreUsuario = "", contraseñaUsuario = "",tipoUsuario = "";
         int numFila = 0;
         boolean verificar = false; //Mientras verificar sea false ejecuta el inicio de sesion con un control de errores de los datos
         
     	//Ingreso de datos de usuario
         while(verificar == false) {
         	System.out.println("Ingrese nombre usuario: ");
             nombreUsuario = sc.nextLine();
             boolean existeUsuario = buscarMatriz(matriz3,nombreUsuario);
             while(existeUsuario == false) {
             	System.out.println("Error, ingrese usuario nuevamente:");
             	nombreUsuario = sc.nextLine();
                 existeUsuario = buscarMatriz(matriz3,nombreUsuario);
             }
         	System.out.println("Ingrese contraseña usuario: ");
             contraseñaUsuario = sc.nextLine();
             boolean existeContraseña = buscarMatriz(matriz3,contraseñaUsuario);
             while(existeContraseña == false) {
             	System.out.println("Error, ingrese contraseña nuevamente:");
             	   contraseñaUsuario = sc.nextLine();
                    existeContraseña = buscarMatriz(matriz3,contraseñaUsuario);
             }
           
         	numFila = buscarFila(matriz3,nombreUsuario); //Guarda el numero de fila del usuario
         	tipoUsuario = matriz3[numFila][2]; //Guarda el tipo de usuario de datos_usuarios.txt
        	//Logica para especialidad
        	especialidad = matriz2[numFila][2]; //Guarda al especialidad que tiene el usuario almacenada en datos_creadores.txt
             verificar = verificar(matriz3,nombreUsuario,contraseñaUsuario,numFila); //Verifica que el usuario y contraseña esten correctos
             if(verificar == false) {	
             	System.out.println("Datos ingresados de manera erronea,ingrese datos nuevamente");
             }
             
        System.out.println("Inicio de sesion correcto");
        if (tipoUsuario.equals("administrador") ) {
            // Lógica para usuario administrador
        	System.out.println("Usted ingreso al menu Administrador");
        	loginAdmin(matriz1,matriz2,matriz3,archivoIa);

        } else if (tipoUsuario.equals("normal") ) {
        	//Logica para usuario Normal
        	System.out.println("Usted ingreso al menu Normal");
        	if(especialidad.equals("progamador")){
            	System.out.println("Usted tiene la especialidad de Progamador");
            	loginNormalProga(matriz1,matriz2,numFila);	
        	}
        	else if(especialidad.equals("mejora ia")) {
            	System.out.println("Usted tiene la especialidad de mejora de ia.");
        		loginNormalIa(matriz1,matriz2,numFila);
        	}
        	else if(especialidad.equals("ia master")) {
        		loginNormalMaster(matriz1,matriz2,numFila);
        	}
        }
        //Cerrar escaner
        sc.close();
    }
    }//login
    
    /*El usuario debe ingresar su nombre de usuario y contraseña. Si los datos son
     *  incorrectos, se le pedirá que los ingrese nuevamente. Una vez que el usuario 
     *  ingresa correctamente, se verifica su tipo de usuario y especialidad. Si es un 
     *  administrador, se llama a un método "loginAdmin" para el menú de administrador. 
     *  Si es un usuario normal, se muestra el menú correspondiente según su especialidad.*/
    
    //Busca en la matriz dada junto al valor a buscar si existe en la matriz sacada del archivo de texto seleccionado.
    public static boolean buscarMatriz(String[][] matriz, String cadenaBuscada) {
    	
        try {
            boolean existe = false;
            for(int i = 0; i < matriz.length; i++) {
                for(int j = 0; j < matriz[i].length; j++) {
                    if(matriz[i][j].equals(cadenaBuscada)) {
                        existe = true;
                        break;
                    }
                }
                if(existe) {
                    break;
                }
            }
            return existe;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    
    //Busca la fila en la cual existe el dato ingresado no ah vuelto
    public static int buscarFila(String[][] matriz,String cadenaBuscada) {
   	 int fila = 0;
        boolean existe = false;
        for(int i = 0; i < matriz.length; i++) {
            for(int j = 0; j < matriz[i].length; j++) {
                if(matriz[i][j].equals(cadenaBuscada)) {
                    fila = i;
                    break;
                }
            }
            if(existe) {
                break;
            }
        }
        return fila;
   }
    
  //Verifica que los datos ingresados por consola corresponden tanto al usuario, contraseña y tipo de usuario especificos en la misma fila de usuario.
    public static boolean verificar(String[][] matriz, String usuario, String contraseña,int fila) {
    	String[] filaUsuario = matriz[fila];
        if(filaUsuario[0].equals(usuario) && filaUsuario[1].equals(contraseña)) {
            return true;
        }
        return false;
    }
    
    /*La función "loginAdmin" es un método público y estático que recibe tres matrices y un archivo y lanza una excepción 
     * IOException. Esta función permite al administrador acceder a diferentes submenús para 
     * realizar tareas. Se utilizan diferentes variables y bloques de código para manejar las 
     * opciones seleccionadas por el usuario.*/
    public static void loginAdmin(String[][] matriz1,String[][] matriz2,String[][] matriz3,
    		File archivoIa) throws IOException{
    	//MAtriz 1 = datos_ia.txt ; matriz2 = datos_creadores.txt ; matriz3 = datos_usuarios.txt
    	
    	//Variables
    	String opcion = "",dato = "",elemento ="";
    	
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	
    	do {
    		System.out.println("¿A que submenú desea ingresar?");
    		System.out.println("1- Submenú IA");
    		System.out.println("2- Submenú Usuarios y Creadores");
    		System.out.println("3- Reportes");
    		System.out.println("4- Salir");
    		opcion = sc.nextLine();
    	
    		switch(opcion) {
    		case "1":
    			System.out.println("Ingreso al Submenú IA");
    			System.out.println("A que menu Submenu IA desea ingresar");
    			System.out.println("1- Ordenar por Cualidad");
    			System.out.println("2- Editar datos de IA");
    			System.out.println("3- Salir");
        		opcion = sc.nextLine();
        		switch(opcion) {
        		case "1":
        			submenuIaOrdenar(matriz1,matriz2);
        			break;
        		case "2":
        			System.out.println("¿Que dato desea remplazar?");
        			imprimirMatriz(matriz1);
        			elemento = sc.nextLine();
        			System.out.println("Ingrese dato nuevo a remplazar");
        			dato = sc.nextLine();
        			submenuIaEditar(matriz1,elemento,dato,archivoIa);
        			System.out.println("El dato a sido remplazado con exito");
        			imprimirMatriz(matriz1);
        			break;
        		case "3":
        			System.out.println("Saliendo");
        			break;
        		default:
        			System.out.println("Opcion invalida");
        			break;
        		}
    			break;
    		case "2":
    			System.out.println("Ingreso al Submenú Usuarios y Creadores");
    			System.out.println("¿A que menu submenu Usuarios y Creadores desea ingresar?");
    			System.out.println("1- Cantidad de tipo de Usuario");
    			System.out.println("2- Añadir Usuario y/o Creador");
    			System.out.println("3- Eliminar Usuario y/o Creador");
    			System.out.println("4- Reportes");
    			System.out.println("5- Salir");
    			opcion = sc.nextLine();
    			switch(opcion) {
    			case "1":
    				submenuUsuariosCantidadTipo(matriz3);
    				break;
    			case "2":
    				añadirUsuario(matriz1,matriz2,matriz3);
    				break;
    			case "3":
    				menuEliminarUsuarioCreador(matriz3,matriz2);
    			default:
    				System.out.println("Opcionr invalida");
    				break;
    			}
    			break;	
    		case "3":
    			System.out.println("Ingreso en el menu de reportes");
    			//Edad media Creadores
    			edadMediaCreadores(matriz2);
    			//Edad media Ia
    			edadMediaIa(matriz1);
    			//Promedio velocidad aprendizaje de las IA
    			promVelocidadesIas(matriz1);
    			//Creadores segun años de experiencia de forma decreciente
    			ordenarCreadoresPorExperiencia(matriz2);
    			//Cantidad de IA de tipo
    			cantidadIaTipo(matriz1);
    			//Cantidad creadores por especialidad
    			cantidadCreadoresEspecialidad(matriz2);
    			//Ias mas problabes en revelare
    			iaRelevarse(matriz1,matriz2);
    			break;
    		case "4":
    			System.out.println("Saliendo");
    			break;
    		default:
    			System.out.println("Opcion invalida");
    			break;
    		}
    		if(!sc.hasNextLine()) {
    			break;
    		}
    		
    	}while (!opcion.equals("4")); 
    	sc.close();
    }
    
    /*El código es una función de inicio de sesión para mejorar una inteligencia artificial. 
     * Primero muestra una lista de todas las IAs disponibles y su cantidad de mejoras. Luego, 
     * permite al usuario seleccionar una IA para mejorar y agregar la cantidad deseada de mejoras.*/
    public static void loginNormalIa(String[][] matriz1,String[][]matriz2, int numFila) throws IOException{
    	
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	//Variables
    	String opcion = "",especialidad = "";
    	//Abrir archivo
    	File archivoIa = new File("datos_ia.txt");

    	//Inicio de sesion Mejora de ia
    	String[][] datosIa = obtenerDatosIa(matriz1,0,5);
    	System.out.println("A continuacion se muestran todas las Ia y su cantidad de mejores correspondientes:");
    	imprimirMatriz(datosIa);
    	System.out.println("¿Desea añadir mejoras? (S = si / N = no)");
    	opcion = sc.nextLine();
    	if(opcion.equals("s") || opcion.equals("S")){
    		System.out.println("Seleccione la ia a la cual mejorar");
        	String ia = sc.nextLine();
        	boolean existeIa = buscarMatriz(matriz1,ia);
        		while(existeIa == false) {
                   System.out.println("Error, ingrese ia a la cual mejorar nuevamente:");
                   ia = sc.nextLine();
                   existeIa = buscarMatriz(matriz1,ia);
                   }
         	numFila = buscarFila(matriz1,String.valueOf(ia));
        	System.out.println("Ingrese la cantidad de mejoras a agregar");
        	int añadirMejorasIa = sc.nextInt();
        	remplazarDatoMejoraIa(archivoIa,matriz1,numFila,String.valueOf(añadirMejorasIa));
        	imprimirMatriz(datosIa);	
    	}else if(opcion.equals("n") || opcion.equals("N")){
    		System.out.println("Elegiste la opcion No, fin del progama");
    	}
    	sc.close();
    }

    /*La función loginNormalProga recibe dos matrices y un número de fila como parámetros. 
     * Permite al programador cambiar la velocidad o el tipo de una inteligencia artificial
     *  existente en el archivo "datos_ia.txt". Para ello, muestra una lista de todas las IAs
     *   y sus velocidades actuales, y pide al usuario que seleccione la IA a modificar y el 
     *   aspecto a cambiar. Finalmente, actualiza el archivo con los nuevos datos.*/
    public static void loginNormalProga(String[][] matriz1,String[][] matriz2, int numFila) throws IOException {
    	//Iniciar escaner
    	Scanner sc = new Scanner(System.in);
    	//Variables
    	String opcion = "",especialidad = "";
    	//Archivo de texto a cambiar.
    	File archivoIa = new File("datos_ia.txt");
    	
    	//Inicio de sesion menu Progamador
    	String[][] datosIa = obtenerDatosIa(matriz1,0,2);
    	System.out.println("A continuacion se muestran todas las Ia y su cantidad de velocidades:");
    	imprimirMatriz(datosIa);
    	System.out.println("Ingrese nombre de la Ia a cambiar:");
    	opcion = sc.nextLine();
    	boolean existeIa = buscarMatriz(matriz1,opcion);
		while(existeIa == false) {
           System.out.println("Error, ingrese ia a la cual cambiar nuevamente:");
           opcion = sc.nextLine();
           existeIa = buscarMatriz(matriz1,opcion);
           }
		System.out.println("Desea cambiar la velocidad de ia o el tipo de ia(1 o 2)");
		opcion = sc.nextLine();
		while(opcion.equals("1") && opcion.equals("2")){
			System.out.println("Error, ingrese si desea cambiar velocidad de ia o el tipo de ia(1 o 2)");
			opcion = sc.nextLine();
		}
		if(opcion.equals("1")){
			System.out.println("Ingrese el valor a cambiar ");
			opcion = sc.nextLine();
    		numFila = buscarFila(matriz1,String.valueOf(opcion));
			remplazarDatoVelocidad(archivoIa,matriz1,numFila,String.valueOf(opcion));
		}
		if(opcion.equals("2")) {
			System.out.println("Ingrese tipo de ia a cambiar (Simple - Mediana - Avanzada)");
			opcion = sc.nextLine();
    		numFila = buscarFila(matriz1,String.valueOf(opcion));
			remplazarDatoTipoIa(archivoIa,matriz1,numFila,String.valueOf(opcion));
		}
		
		sc.close();
    }
    
   
    
}//Public class	


	


