package MemoriaCache;

import java.util.Random;
import java.util.Scanner;

public class CacheApp {
	
//	Cache mc = new Cache();
//	int dirByte, tag, pal, bl, cjto, tacceso,  dirPal;
//	boolean AF, Wr, act;
//	Scanner s = new Scanner(System.in);
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cache mc = new Cache();
		mc.iniciarCache();
		
		System.out.println(" * @author Daniel Ruskov ");
		System.out.println(" * 09/10/2018, Arquitectura de Computadores");
		System.out.println(" * Sumilacion de Memoria Cache Directa con 8 conjuntos, palabras de 4 bytes y bloques de 4 palabras. Politica de escritura WB. Direccionamiento al byte,");
		System.out.println(" * espacio de MC: 128 Bytes. Act(0=no, 1=si); Wr(0=no, 1=si)");
		System.out.println(" * ");
		System.out.println(" * Esquema:         <--------------------dirByte n bits-------------------->    (Direcciones MP de n bits, direccionada al byte)");
		System.out.println(" *                  ________________________________________________________");
		System.out.println(" *                  |        n-7      |      3	    |     2     |     2    |");
		System.out.println(" *    MC:           |        Tag      |	   Cjto     |   pal/bl  | byte/pal |  Tcm = 2c.; Tmp = 21c.; Tbuf = 1c");
		System.out.println(" *                  |_________________|_____________|___________|__________|");
		System.out.println(" *                  <------------------dirPal------------------>");
		System.out.println(" *                  <------------bl(MP)------------>");
		System.out.println("");

		int dirByte, datoWr, tag, pal, bl, cjto, dirPal, tacceso, contCiclos=0, contVeces=0, contAciertos=0;
		//boolean AF, Wr, act;
		Scanner s = new Scanner(System.in);
		Random aleat= new Random();
		boolean running=true;
		int opc;
		while(running) {
			System.out.println("Introduce 0 para leer de MC, 1 para escribir en MC, 2 para visualizar MC o 3 para salir.");
			opc=s.nextInt();
			
			switch (opc) {
			case 0:
				contVeces=contVeces+1;
				System.out.println("Introduce direccion:");
				dirByte=s.nextInt();
				System.out.println("@byte: "+dirByte);
				dirPal=mc.calculoDirPalabra(dirByte);
				System.out.println("@pal: "+dirPal);
				bl=mc.calculoBloqueMP(dirByte);
				System.out.println("bloque MP: "+bl);
				pal=mc.calculoPalabra(dirByte);
				System.out.println("pal: "+pal);
				cjto=mc.calculoCjto(dirByte);
				System.out.println("cjto MC: "+cjto);
				tag=mc.calculoTag(dirByte);
				System.out.println("tag: "+tag);
				if(mc.esActivo(cjto) && mc.getTag(cjto)==tag) { //acierta lectira en MC
					tacceso=2;
					contAciertos=contAciertos+1;
					contCiclos=contCiclos+tacceso;
					System.out.println("Acierto");
					System.out.println("Transferencia bl: no");
					System.out.println("Tacceso: "+tacceso);
				}else { //fallo
					if(mc.esActivo(cjto) && mc.estaEscrito(cjto)) {//con reemplazo
						tacceso=2+21+3*1+21+3*1;
						contCiclos=contCiclos+tacceso;
						System.out.println("Fallo con reemplazo");
						System.out.println("Transferencia bl: MC->MP y MP->MC");
						System.out.println("Tacceso: "+tacceso);
						//mc.setActivo(cjto, 1);
						mc.setTag(cjto, tag);
						mc.setWr(cjto, 0);
						for(int i=0; i<4; i++) {
							mc.setPalabra(cjto, i, aleat.nextInt(500));
						}
					}else {//sin reemplazo
						tacceso=2+21+3*1;
						contCiclos=contCiclos+tacceso;
						System.out.println("Fallo");
						System.out.println("Transferencia bl: MP->MC");
						System.out.println("Tacceso: "+tacceso);
						mc.setActivo(cjto, 1);
						mc.setTag(cjto, tag);
						mc.setWr(cjto, 0);
						for(int i=0; i<4; i++) {
							mc.setPalabra(cjto, i, aleat.nextInt(99));
						}
					}
				}
				System.out.println("");
				break;
				
			case 1:
				contVeces=contVeces+1;
				System.out.println("Introduce direccion:");
				dirByte=s.nextInt();
				System.out.println("Introduce dato:");
				datoWr=s.nextInt();
				System.out.println("@byte: "+dirByte);
				dirPal=mc.calculoDirPalabra(dirByte);
				System.out.println("@pal: "+dirPal);
				bl=mc.calculoBloqueMP(dirByte);
				System.out.println("bloque MP: "+bl);
				pal=mc.calculoPalabra(dirByte);
				System.out.println("pal: "+pal);
				cjto=mc.calculoCjto(dirByte);
				System.out.println("cjto MC: "+cjto);
				tag=mc.calculoTag(dirByte);
				System.out.println("tag: "+tag);
				if(mc.esActivo(cjto) && mc.getTag(cjto)==tag) { //acierta para escritura en MC
					tacceso=2;
					contAciertos=contAciertos+1;
					contCiclos=contCiclos+tacceso;
					System.out.println("Acierto");
					System.out.println("Transferencia bl: no");
					System.out.println("Tacceso: "+tacceso);
					mc.setWr(cjto, 1);
					mc.setPalabra(cjto, pal, datoWr);
				}else {//fallo
					if(mc.esActivo(cjto) && mc.estaEscrito(cjto)) {//con reemplazo
						tacceso=2+21+3*1+21+3*1;
						contCiclos=contCiclos+tacceso;
						System.out.println("Fallo con reemplazo");
						System.out.println("Transferencia bl: MC->MP y MP->MC");
						System.out.println("Tacceso: "+tacceso);
						//mc.setActivo(cjto, 1);
						mc.setTag(cjto, tag);
						mc.setWr(cjto, 1);
						mc.setPalabra(cjto, pal, datoWr);
					}else {//sin reemplazo
						tacceso=2+21+3*1;
						contCiclos=contCiclos+tacceso;
						System.out.println("Fallo");
						System.out.println("Transferencia bl: MP->MC");
						System.out.println("Tacceso: "+tacceso);
						mc.setActivo(cjto, 1);
						mc.setTag(cjto, tag);
						mc.setWr(cjto, 1);
						mc.setPalabra(cjto, pal, datoWr);
					}
				}
				System.out.println("");
				break;
				
			case 2:
				mc.visualizarMC();
				System.out.println("");
				break;
				
			case 3:
				running=false;
				System.out.println("Referencias: "+contVeces);
				System.out.println("Aciertos: "+contAciertos);
				System.out.println("Tasa de aciertos: "+(Double.valueOf(contAciertos)/Double.valueOf(contVeces))*100+"%" );
				System.out.println("Tiempo total: "+contCiclos);
				System.out.println("END");
				s.close();
				break;
				
			default:
				System.out.println("Introduce opcion valida");
				System.out.println("");
				break;
			}
		}
	}
}