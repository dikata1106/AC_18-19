package MemoriaCache;

//import java.util.Arrays;

/**
 * 
 * @author Daniel Ruskov 
 * 09/10/2018, Arquitectura de Computadores
 * Sumilacion de Memoria Cache Directa con 8 conjuntos, palabras de 4 bytes y bloques de 4 palabras. Politica de escritura WB. Direccionamiento al byte,
 * espacio de MC: 128 Bytes. Act(0=no, 1=si); Wr(0=no, 1=si)
 * 
 * Esquema:			<--------------------dirByte n bits-------------------->    (Direcciones MP de n bits, direccionada al byte)
 * 					________________________________________________________
 * 					|        n-7      |		 3		|     2	    |     2    |
 * 		MC:			|		 Tag	  |	   Cjto 	|   pal/bl  | byte/pal |
 *					|_________________|_____________|___________|__________|
 *					<------------------dirPal------------------>
 *					<------------bl(MP)------------>
 *
 *		Tcm = 2c.; Tmp = 21c.; Tbuf = 1c
 *
 */

public class Cache {

	private int[][] contenido = new int[8][4];//8 bloques x 4 palabras
	private int[][] directorio = new int[8][3];//directorio: bitActivo, bitWrite, Tag
	
	public void iniciarCache() {//iniciar directorio inactivo sin escribir
		for(int i=0; i<8; i++) {
			for (int j=0; j<2; j++) {
				directorio[i][j]=0;
			}
		}
	}
	
	public int getPalabra(int bl, int pal) { //lee una palabra de MC
		return contenido[bl][pal];
	}
	
	public void setPalabra(int bl, int pal, int dato) { //escribe una palabra de MC
		contenido[bl][pal]=dato;
	}
	
	public int getActivo(int cjto) { //devuelve bit de cjto activo o no
		return directorio[cjto][0];
	}
	
	public boolean esActivo(int cjto) { //devuelve true si el cjto esta activo
		return getActivo(cjto)==1;
	}
	
	public void setActivo(int cjto, int activo) { //cambia la actiovidad de un cjto
		directorio[cjto][0]=activo;
	}
	
	public int getWr(int cjto) { //devuelve bit de escrito o no (dirty)
		return directorio[cjto][1];
	}
	
	public boolean estaEscrito(int cjto) { //devuelve true si el cjto esta modificado
		return getWr(cjto)==1;
	}
	
	public void setWr(int cjto, int escrito) { //cambia el bit de escrito o no
		directorio[cjto][1]=escrito;
	}
	
	public int getTag(int cjto) { //devuelve el tag de un cjto de MC
		return directorio[cjto][2];
	}
	
	public void setTag(int cjto, int tag) { //modifica el tag de un cjto
		directorio[cjto][2]=tag;
	}
	
	public void visualizarMC() { //esquema de la MC con contenidos
		final String[] inf  = {"  Act", "Wr", "Tag", "Pal_0", "Pal_1", "Pal_2", "Pal_3"};
		//System.out.println(Arrays.toString(inf));
		for (int k = 0; k < inf.length; k++){
	        System.out.print("  "+inf[k].toString());
		}
		System.out.println("");
		for(int i=0; i<8; i++) {
			System.out.print("C"+i+"   ");
			for (int j=0; j<3; j++) {
				System.out.print(directorio[i][j]+"   ");
			}
			System.out.print("|  ");
			for (int j=0; j<4; j++) {
				System.out.print(contenido[i][j]+"      ");
			}
			System.out.println("");
		}
	}
	
	public int calculoDirPalabra(int dirByte) { //dada la direccion devuelve la direccion a la palabra
		return dirByte/4;
	}
	
	public int calculoBloqueMP(int dirByte) { //dada la direccion devuelve el bl de MP
		return calculoDirPalabra(dirByte)/4;
	}
	
	public int calculoCjto(int dirByte) { //dada la direccion devuelve el cjto correspondiente de MC
		return calculoBloqueMP(dirByte)%8;
	}
	
	public int calculoTag(int dirByte) { //dada la direccion devuelve el tag de MC
		return calculoBloqueMP(dirByte)/8;
	}
	
	public int calculoPalabra(int dirByte) { //calcula la palabra correspondiente
		if (calculoBloqueMP(dirByte)==0) {
			return 0;
		}
		return calculoDirPalabra(dirByte)%calculoBloqueMP(dirByte);
	}
	
	public static void main(String[] args) { //prueba cache
		Cache mc = new Cache();
		mc.iniciarCache();
		mc.visualizarMC();
	}
}