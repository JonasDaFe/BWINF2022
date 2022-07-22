package a3Hex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class hexTest1 {
	/* Theoretische Darstellung einer aus Strichen bestehernder Hexadezimalziffer:
	 * a) als Array: hex = [true, true, false, true, true, false, true] => darstellung der ziffer 2 als Array
	 * b) als Bin�rziffer: zwei = 1101101 => Darstellung 2 => muss auch in Array
	Hier betrachte ich nur Vertauschungen innerhalb der ziffers
	*/
	ArrayList<String> ziffern = new ArrayList<String>();
	String[] gruppe2 = {"0110000"};
	String[] gruppe3 = {"1110000"};
	String[] gruppe4 = {"0110011", "1001110","1000111"};
	String[] gruppe5 = {"1101101","1111001","1011011","0011111","0111101","1001111"};
	String[] gruppe6 = {"1111110", "1011111", "1111011", "1110111"};
	String[] gruppe7 = {"1111111"};
	int[][][] Vertauschungsmatrix = {
			{{0,0,0,0,},{1,4,0,4,},{2,2,0,1,},{3,2,0,1,},{4,3,0,2,},{5,2,0,1,},{6,1,0,0,},{7,3,0,3,},{8,1,1,0,},{9,1,0,0,},{10,1,0,0,},{11,2,0,1,},{12,2,0,2,},{13,2,0,1,},{14,2,0,1,},{15,3,0,2,}},
			{{0,4,4,0,},{1,0,0,0,},{2,4,3,0,},{3,3,3,0,},{4,2,2,0,},{5,4,3,0,},{6,5,4,0,},{7,1,1,0,},{8,5,5,0,},{9,4,4,0,},{10,4,4,0,},{11,4,3,0,},{12,4,2,0,},{13,3,3,0,},{14,5,3,0,},{15,4,2,0,}},
			{{0,2,1,0,},{1,4,0,3,},{2,0,0,0,},{3,1,0,0,},{4,3,0,1,},{5,2,0,0,},{6,2,1,0,},{7,3,0,2,},{8,2,2,0,},{9,2,1,0,},{10,2,1,0,},{11,2,0,0,},{12,2,0,1,},{13,1,0,0,},{14,1,0,0,},{15,2,0,1,}},
			{{0,2,1,0,},{1,3,0,3,},{2,1,0,0,},{3,0,0,0,},{4,2,0,1,},{5,1,0,0,},{6,2,1,0,},{7,2,0,2,},{8,2,2,0,},{9,1,1,0,},{10,2,1,0,},{11,2,0,0,},{12,3,0,1,},{13,1,0,0,},{14,2,0,0,},{15,3,0,1,}},
			{{0,3,2,0,},{1,2,0,2,},{2,3,1,0,},{3,2,1,0,},{4,0,0,0,},{5,2,1,0,},{6,3,2,0,},{7,2,0,1,},{8,3,3,0,},{9,2,2,0,},{10,2,2,0,},{11,2,1,0,},{12,3,0,0,},{13,2,1,0,},{14,3,1,0,},{15,2,0,0,}},
			{{0,2,1,0,},{1,4,0,3,},{2,2,0,0,},{3,1,0,0,},{4,2,0,1,},{5,0,0,0,},{6,1,1,0,},{7,3,0,2,},{8,2,2,0,},{9,1,1,0,},{10,2,1,0,},{11,1,0,0,},{12,2,0,1,},{13,2,0,0,},{14,1,0,0,},{15,2,0,1,}},
			{{0,1,0,0,},{1,5,0,4,},{2,2,0,1,},{3,2,0,1,},{4,3,0,2,},{5,1,0,1,},{6,0,0,0,},{7,4,0,3,},{8,1,1,0,},{9,1,0,0,},{10,1,0,0,},{11,1,0,1,},{12,2,0,2,},{13,2,0,1,},{14,1,0,1,},{15,2,0,2,}},
			{{0,3,3,0,},{1,1,0,1,},{2,3,2,0,},{3,2,2,0,},{4,2,1,0,},{5,3,2,0,},{6,4,3,0,},{7,0,0,0,},{8,4,4,0,},{9,3,3,0,},{10,3,3,0,},{11,4,2,0,},{12,3,1,0,},{13,3,2,0,},{14,4,2,0,},{15,3,1,0,}},
			{{0,1,0,1,},{1,5,0,5,},{2,2,0,2,},{3,2,0,2,},{4,3,0,3,},{5,2,0,2,},{6,1,0,1,},{7,4,0,4,},{8,0,0,0,},{9,1,0,1,},{10,1,0,1,},{11,2,0,2,},{12,3,0,3,},{13,2,0,2,},{14,2,0,2,},{15,3,0,3,}},
			{{0,1,0,0,},{1,4,0,4,},{2,2,0,1,},{3,1,0,1,},{4,2,0,2,},{5,1,0,1,},{6,1,0,0,},{7,3,0,3,},{8,1,1,0,},{9,0,0,0,},{10,1,0,0,},{11,2,0,1,},{12,3,0,2,},{13,2,0,1,},{14,2,0,1,},{15,3,0,2,}},
			{{0,1,0,0,},{1,4,0,4,},{2,2,0,1,},{3,2,0,1,},{4,2,0,2,},{5,2,0,1,},{6,1,0,0,},{7,3,0,3,},{8,1,1,0,},{9,1,0,0,},{10,0,0,0,},{11,2,0,1,},{12,3,0,2,},{13,2,0,1,},{14,2,0,1,},{15,2,0,2,}},
			{{0,2,1,0,},{1,4,0,3,},{2,2,0,0,},{3,2,0,0,},{4,2,0,1,},{5,1,0,0,},{6,1,1,0,},{7,4,0,2,},{8,2,2,0,},{9,2,1,0,},{10,2,1,0,},{11,0,0,0,},{12,2,0,1,},{13,1,0,0,},{14,1,0,0,},{15,2,0,1,}},
			{{0,2,2,0,},{1,4,0,2,},{2,2,1,0,},{3,3,1,0,},{4,3,0,0,},{5,2,1,0,},{6,2,2,0,},{7,3,0,1,},{8,3,3,0,},{9,3,2,0,},{10,3,2,0,},{11,2,1,0,},{12,0,0,0,},{13,3,1,0,},{14,1,1,0,},{15,1,0,0,}},
			{{0,2,1,0,},{1,3,0,3,},{2,1,0,0,},{3,1,0,0,},{4,2,0,1,},{5,2,0,0,},{6,2,1,0,},{7,3,0,2,},{8,2,2,0,},{9,2,1,0,},{10,2,1,0,},{11,1,0,0,},{12,3,0,1,},{13,0,0,0,},{14,2,0,0,},{15,3,0,1,}},
			{{0,2,1,0,},{1,5,0,3,},{2,1,0,0,},{3,2,0,0,},{4,3,0,1,},{5,1,0,0,},{6,1,1,0,},{7,4,0,2,},{8,2,2,0,},{9,2,1,0,},{10,2,1,0,},{11,1,0,0,},{12,1,0,1,},{13,2,0,0,},{14,0,0,0,},{15,1,0,1,}},
			{{0,3,2,0,},{1,4,0,2,},{2,2,1,0,},{3,3,1,0,},{4,2,0,0,},{5,2,1,0,},{6,2,2,0,},{7,3,0,1,},{8,3,3,0,},{9,3,2,0,},{10,2,2,0,},{11,2,1,0,},{12,1,0,0,},{13,3,1,0,},{14,1,1,0,},{15,0,0,0,}}
			}; // 16x16 Matrix, welche die Umlegungen von einer Ziffer zur Anderen angibt
	
	int freiePlaetze = 0; //Wie viele frei/nicht besetzte Pl�tze in der ganzen Hex-Zahl noch vorhanden sind
	int besetztePlaetze = 0; //Wie viele Striche theoretisch weggenommen werden k�nnen, sodass noch eine valide Hex-Zahl erziehlt wird. 
	int[] anzahlStricheProZiffer = {6,2,5,5,4,5,6,3,7,6,6,5,5,5,5,4};// H�lt fest, welche Ziffer, wie viele Striche hat, index = Wert der Ziffer
	int  uebrigeUmlegungen = 0;
	int zwischenspeicherUmlegungen = 0; // Speichert die Ergebnisse vorrausgegangener Umlegungen
	ArrayList<ArrayList<Integer>> speicherVertauschungen = new ArrayList<ArrayList<Integer>>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		hexTest1 he = new hexTest1();
	}
	public hexTest1() {
		// TODO Auto-generated constructor stub
		ziffern.add("1111110"); //0
		ziffern.add("0110000"); //1
		ziffern.add("1101101"); //2
		ziffern.add("1111001"); //3
		ziffern.add("0110011"); //4
		ziffern.add("1011011"); //5
		ziffern.add("1011111"); //6
		ziffern.add("1110000"); //7
		ziffern.add("1111111"); //8
		ziffern.add("1111011"); //9
		ziffern.add("1110111"); //A
		ziffern.add("0011111"); //B 
		ziffern.add("1001110"); //C
		ziffern.add("0111101"); //D
		ziffern.add("1001111"); //E
		ziffern.add("1000111");  //F
		// => Position der Number im Array kolliert mit dem zugeordneten Wert
		//this.vertauschungenBerechnen(gruppe4, ziffern.get(4));
		//vertauschungsMatrixBerechnen();
		Scanner scan = new Scanner(System.in);
		System.out.println("Gebe bitte die zu maximierende HexZahl ein:");
		String zahl = scan.nextLine();
		System.out.println("Gebe bitte die erlaubte Zahl an Umlegungen ein:");
		int erlaubteUmlegungen = Integer.valueOf(scan.nextLine());
		char[] charHexZahl = zahl.toCharArray();
		int[] intHexZahl = this.eingeleseneHexZahlKonvertieren(charHexZahl);
		int[] tempIntHexZahl = intHexZahl.clone();
		intHexZahl = this.hexZahlMaximieren(intHexZahl, erlaubteUmlegungen);
		for(int i = 0; i<intHexZahl.length; i++) {
			System.out.print(intHexZahl[i] + ",");
		}
		System.out.println();
		char[] maxHexZahl = this.intHexZahlZuCharKonvertieren(intHexZahl);
		for(int i = 0; i<maxHexZahl.length; i++) {
			System.out.print(maxHexZahl[i]);
		}
		System.out.println();
		System.out.println("Möchtest du die Umlegungen ausgedruckt haben? Wenn ja tippe JA");
		String temp = scan.nextLine();
		if(temp.equals("JA")) {
		printOutUmlegungen(speicherVertauschungen, tempIntHexZahl);
		}
	}
	//Methoden um die Vertauschungsmatrix zu berechnen
	public void vertauschungsMatrixBerechnen() {
		System.out.println("{");
		for(int i = 0; i<ziffern.size()-1; i++) {
			System.out.print("{");
			this.vertauschungenBerechnen(gruppe2, ziffern.get(i));
			System.out.print(",");
			this.vertauschungenBerechnen(gruppe3, ziffern.get(i));
			System.out.print(",");
			this.vertauschungenBerechnen(gruppe4, ziffern.get(i));
			System.out.print(",");
			this.vertauschungenBerechnen(gruppe5, ziffern.get(i));
			System.out.print(",");
			this.vertauschungenBerechnen(gruppe6, ziffern.get(i));
			System.out.print(",");
			this.vertauschungenBerechnen(gruppe7, ziffern.get(i));
			System.out.println("}");
			System.out.print(",");
		}
		System.out.print("{");
		this.vertauschungenBerechnen(gruppe2, ziffern.get(ziffern.size()-1));
		System.out.print(",");
		this.vertauschungenBerechnen(gruppe3, ziffern.get(ziffern.size()-1));
		System.out.print(",");
		this.vertauschungenBerechnen(gruppe4, ziffern.get(ziffern.size()-1));
		System.out.print(",");
		this.vertauschungenBerechnen(gruppe5, ziffern.get(ziffern.size()-1));
		System.out.print(",");
		this.vertauschungenBerechnen(gruppe6, ziffern.get(ziffern.size()-1));
		System.out.print(",");
		this.vertauschungenBerechnen(gruppe7, ziffern.get(ziffern.size()-1));
		System.out.println("}");
		System.out.println("}");
	}
	public void vertauschungenBerechnen(String[] ziffernGruppe, String ziffer) {
		for(int g=0; g<ziffernGruppe.length-1; g++) {
			this.anzahlUnterschiedeFeststellen(ziffer, ziffernGruppe[g], ziffern.indexOf(ziffernGruppe[g]));
			System.out.print(",");
		}
		this.anzahlUnterschiedeFeststellen(ziffer, ziffernGruppe[ziffernGruppe.length-1], ziffern.indexOf(ziffernGruppe[ziffernGruppe.length-1]));
		
	}
	public int anzahlUnterschiedeFeststellen(String ziffer1, String ziffer2, int indexziffer2) {
		//Diese Methode wird daf�r benutzt herauszufinden, wie Vertauschungen angestellt werden m�ssen,
		//um, eine Ziffer x zur Ziffer y umzustellen. Sie macht aus den informationen ein int[], welches in der richtigen Notation
		//in die Konsole gedruckt wird, um es daraus zu kopieren
		System.out.print("{");
		int Unterschiede = 0;
		char[] arrziffer1 = ziffer1.toCharArray();
		char[] arrziffer2 = ziffer2.toCharArray();
		int zeroOne = 0;
		int oneZero = 0;
		for(int i =0; i<arrziffer1.length; i++) {
			if(arrziffer2[i] != arrziffer1[i]) {
				if(arrziffer1[i] == '1') {
					oneZero++;
				}else if(arrziffer1[i] == '0'){
					zeroOne++;
				}
			}
		}
		System.out.print(indexziffer2 + ","); //Zu welcher Ziffer wird die urspr�ngliche ziffer getauscht?
		if(oneZero>zeroOne) {				// WIe viele Vertauschungen werden insgesamt ben�tigt?
			System.out.print(oneZero + ","); 
		}else {
			System.out.print(zeroOne + ",");
		}
		if(zeroOne-oneZero > 0) {
			System.out.print((zeroOne-oneZero) + ",0}"); // Wie viele "Striche" werden f�r die Vertauschungen extra ben�tigt?
		}else if(zeroOne-oneZero < 0) {
			System.out.print("0," + ((zeroOne-oneZero)*-1) +"}"); // Wie viele "Striche" bleiben nach der Vertauschung �brig
		}else {
			System.out.print("0,0}");
		}
		//System.out.println("oneZero:" + oneZero + " zeroOne:" + zeroOne);
		if(oneZero == zeroOne) {
			return oneZero;
		}
		return Unterschiede;
	}
	public void matrixSortieren(int[][][] matrix) {
		for(int i=0; i<matrix.length; i++) {
			int[][] temp = this.sortieren(matrix[i]);
			matrix[i] = temp;
		}
		System.out.println("{");
		for(int i = 0;i<matrix.length; i++) {
			System.out.print("{");
			for(int g = 0;g<matrix[i].length; g++) {
				System.out.print("{");
				for(int j = 0;j<matrix[i][g].length; j++) {
					System.out.print(matrix[i][g][j]);
					System.out.print(",");
				}
				System.out.print("},");
			}
			System.out.println("},");
		}
		System.out.println("},");
	}
	public int[][] sortieren(int[][] pArray) {
		int[][] Array = pArray;
		int swp = 0;
		int cmp = 0;
		for(int i= 1; i<Array.length;i++) {
			for(int g = i; g>0; g--) {
				cmp++;
				if(Array[g-1][0]>Array[g][0]) {
					int[] temp = Array[g];
					Array[g] = Array[g-1];
					Array[g-1] = temp;
					swp++;
				}else {
					g= 0;
				}
			}
		}
		System.out.println("cmp: " + cmp );
		System.out.println("swp: " + swp);
		return Array;
	}
	//Methoden um die HexadezimalZahl zu maximieren
	/**
	 * Diese Methode maximiert die gegebene Hexadezimalzahl indem sie die angegebenen Umlegungen benutzt. Dafür benutzt sie die Methode @rekursivHexZahlMaximieren
	 * Kümmert sich um die richtigen Rahmenbedingungen und sorgt dafür, dass die durch @rekursivHexZahlMaximieren errechneten Vertauschungen angewendet werden 
	 * @param hexZahl die Ausgangszahl als Int[] mit den Werten der einzelnen Ziffern
	 * @param pUebrigeUmlegungen die Anzahl der Umlegungen die noch verfügbar sind.
	 * @return Gibt die maximierte HexZahl als Int[] zurück.
	 */
	public int[] hexZahlMaximieren(int[] hexZahl, int pUebrigeUmlegungen) {
		freiePlaetze = this.freieStellenFeststellen(hexZahl);
		besetztePlaetze = this.besetztePlaetzeFeststellen(hexZahl);
		uebrigeUmlegungen = pUebrigeUmlegungen;
		zwischenspeicherUmlegungen = 0;
		boolean suc = this.rekursivHexZahlMaximieren(hexZahl,Vertauschungsmatrix[hexZahl[0]],uebrigeUmlegungen, freiePlaetze, besetztePlaetze, zwischenspeicherUmlegungen,0);
		if(suc == true) {
		for(int i = 0; i<hexZahl.length; i++) {
			int[] vertauschung = vertauschOptionArrayListKonvertieren(speicherVertauschungen.get(i));
			if(vertauschung != null) {
				//Erhaltene Vertauschung auf die Werte anwenden
				hexZahl[i] = vertauschung[0];
				freiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[i]]);
				besetztePlaetze = besetztePlaetze-(anzahlStricheProZiffer[hexZahl[i]] -2);
				uebrigeUmlegungen = uebrigeUmlegungen-vertauschung[1];
				int NeuZwischenspeicher = zwischenspeicherUmlegungen + vertauschung[2];
				if(zwischenspeicherUmlegungen<0 && NeuZwischenspeicher >0) {
					uebrigeUmlegungen = uebrigeUmlegungen - (vertauschung[1]-vertauschung[2]);
				} 
				NeuZwischenspeicher = zwischenspeicherUmlegungen - vertauschung[3];
				if(zwischenspeicherUmlegungen>0 && NeuZwischenspeicher <0) {
					uebrigeUmlegungen = uebrigeUmlegungen - (vertauschung[1]-vertauschung[3]);
				} 
			}
			if(zwischenspeicherUmlegungen == freiePlaetze) {
				for(int g= i+1; g<hexZahl.length; g++) {
					hexZahl[g] = 8;
				}
				return hexZahl;
				//Methode um den Rest der Zahl zu f�llen
			}else if((zwischenspeicherUmlegungen)*-1 == besetztePlaetze) {
				//Methode um den Rest der Zahl zu lehren/minimieren
				for(int g= i+1; g<hexZahl.length; g++) {
					hexZahl[g] = 2;
				}
				return hexZahl;
			}
		}
		return hexZahl;
		}else {
			System.out.println("failed");
		}
		return null;
	}
	/**
	 * Stellt die Anzahl der besetzten Plätze fest. Dabei werden die in der Ziffer vorhandenen 
	 * Striche-2 zusammengezählt, da die Zahl mit den wenigsten Strichen 1 ist. 
	 * Da immer eine valide Zahl am Ende der Maximierung erhalten werden soll. 
	 * @param phexZahl HexZahl für die die besetzten Plätze festgestellt werden sollen
	 * @return Gibt die Anzahl der besetzten Plätze zurück
	 */
	public int besetztePlaetzeFeststellen(int[] phexZahl) {
		int bPlaetze = 0;
		for(int i = 0; i<phexZahl.length; i++) {
			bPlaetze+= (anzahlStricheProZiffer[phexZahl[i]] -2);
		}
		return bPlaetze;
	}
	/**
	 * Stellt die Anzahl der freien Plätze fest. Dabei werden die in der Ziffer vorhandenen 
	 * Striche s von 7 abgezogen(7-s), da die Zahl mit den 8 ist und alle 7 Plätze gefüllt hat. 
	 * @param phexZahl HexZahl für die die freien Plätze festgestellt werden sollen
	 * @return Gibt die Anzahl der freien Plätze zurück
	 */
	public int freieStellenFeststellen(int[] phexZahl) {
		int freieStellen = 0;
		for(int i = 0; i< phexZahl.length; i++) {
			freieStellen += (7- anzahlStricheProZiffer[phexZahl[i]]);
		}
		return freieStellen;
	}
	/**
	 * Konvertiert die als char[] eingelesene HexZahl in ein int[]. 
	 * Im int[] steht der Wert der Ziffer in dem char[] an der Position
	 * @param hex
	 * @return
	 */
	public int[] eingeleseneHexZahlKonvertieren(char[] hex) {
		int[] rHexZahl = new int[hex.length]; 
		String[][] konvertierungsArray = {{"0","0"},{"1","1"},{"2","2"},{"3","3"},{"4","4"},{"5","5"},{"6","6"},{"7","7"},{"8","8"},{"9","9"},{"A","10"},{"B","11"},{"C","12"},{"D","13"},{"E","14"},{"F","15"},};
		for(int i =0; i<hex.length; i++) {
			for(int j=0; j<konvertierungsArray.length; j++) {
				if(konvertierungsArray[j][0].equals(Character.toString(hex[i]))) {
					rHexZahl[i] = Integer.valueOf(konvertierungsArray[j][1]);
					j = konvertierungsArray.length;
				}
			}
		}
		return rHexZahl;
	}
	/**
	 * Konvertiert eine HexZahl als int[] in die selbe HexZahl als char[] 
	 * @param hex
	 * @return
	 */
	public char[] intHexZahlZuCharKonvertieren(int[] hex) {
		char[] rHexZahl = new char[hex.length]; 
		String[] konvertierungsArray = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		for(int i =0; i<hex.length; i++) {
			rHexZahl[i]= konvertierungsArray[hex[i]].charAt(0);
		}
		return rHexZahl;
	}
	/**
	 * Ist der rekursive Teil, welcher die HexZahl maximiert.  
	 * @param hexZahl
	 * @param tauschMatrix
	 * @param verbleibendeUmlegungen
	 * @param freiePlaetze
	 * @param uebrigePlaetze
	 * @param zwischenspeicherVertauschungen
	 * @param index
	 * @return
	 */
	public boolean rekursivHexZahlMaximieren(int[] hexZahl, int[][] tauschMatrix, int verbleibendeUmlegungen, int freiePlaetze, int uebrigePlaetze, int zwischenspeicherVertauschungen, int index){
		System.out.print(freiePlaetze);
		if(zwischenspeicherVertauschungen == freiePlaetze) {
			ArrayList<Integer> vertauschOption = new ArrayList<Integer>();
			vertauschOption.add(8);
			vertauschOption.add(0);
			vertauschOption.add(0);
			vertauschOption.add(0);
			for(int i = index; i<hexZahl.length; i++) {
				speicherVertauschungen.add(vertauschOption);
			}
			return true;
			//restlichen Zahlen zu 8 machen
		} else if(zwischenspeicherVertauschungen*-1 == uebrigePlaetze) {
			ArrayList<Integer> vertauschOption = new ArrayList<Integer>();
			vertauschOption.add(1);
			vertauschOption.add(0);
			vertauschOption.add(0);
			vertauschOption.add(0);
			for(int i = index; i<hexZahl.length; i++) {
				speicherVertauschungen.add(vertauschOption);
			}
			return true;
			//restlichen Zahlen zu 1 machen
		}
		boolean tauschValide = false;
		int counter = 1;
		while(tauschValide == false && tauschMatrix.length > counter) {
			int[] vertauschOption = tauschMatrix[tauschMatrix.length-counter];
			if(vertauschOption[2] == 0 && vertauschOption[3] == 0 && vertauschOption[1]<=verbleibendeUmlegungen) {
				int pFreiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[index]]);
				int pBesetztePlaetze = uebrigePlaetze-(anzahlStricheProZiffer[hexZahl[index]] -2);
				int pVerbleibendeUmlegungen = verbleibendeUmlegungen-vertauschOption[1];
				System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);

				boolean success = false;
				if(hexZahl.length-1 == index) {
					if(zwischenspeicherVertauschungen == 0 ) {
						success = true;
					}
				}else {
					success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], pVerbleibendeUmlegungen, pFreiePlaetze, pBesetztePlaetze, zwischenspeicherVertauschungen, index+1);
				}
				if(success == true) {
					System.out.println("i"+index);
					System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);
					speicherVertauschungen.add(0, vertauschOptionArrayKonvertieren(vertauschOption));
					return true;
				}
			}else if(vertauschOption[2]!= 0) {
				if(zwischenspeicherVertauschungen>0) {
					int pBenutzteVertauschungen = vertauschOption[1]-vertauschOption[2];
					if((zwischenspeicherVertauschungen - vertauschOption[2])<0) {
						pBenutzteVertauschungen = pBenutzteVertauschungen - (zwischenspeicherVertauschungen - vertauschOption[2]);
					}
					if((zwischenspeicherVertauschungen-vertauschOption[2] )<= freiePlaetze && (zwischenspeicherVertauschungen-vertauschOption[2])*-1<= uebrigePlaetze && pBenutzteVertauschungen <= verbleibendeUmlegungen) {
						int pFreiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[index]]);
						int pBesetztePlaetze = uebrigePlaetze-(anzahlStricheProZiffer[hexZahl[index]] -2);
						int pVerbleibendeUmlegungen = verbleibendeUmlegungen-(vertauschOption[1]-vertauschOption[2]);
						int pZwischenspeicher = zwischenspeicherVertauschungen - vertauschOption[2];
						if(pZwischenspeicher <0) {
							pVerbleibendeUmlegungen = pVerbleibendeUmlegungen - pZwischenspeicher;//neuer Zwischenspeicher wird subtrahiert, da er hier negativ ist und somit die anzahl der zus�tlich benutzen umlegungen angibt
						}
						System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);
						boolean success = false;
						if(hexZahl.length-1 == index) {
							if(zwischenspeicherVertauschungen == 0 ) {
								success = true;
							}
						}else {
							success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], pVerbleibendeUmlegungen, pFreiePlaetze, pBesetztePlaetze, pZwischenspeicher, index+1);
						}
						if(success == true) {
							speicherVertauschungen.add(0, vertauschOptionArrayKonvertieren(vertauschOption));
							return true;
						}
					}
				}else if(zwischenspeicherVertauschungen<=0) {
					if((zwischenspeicherVertauschungen-vertauschOption[2])<= freiePlaetze && (zwischenspeicherVertauschungen-vertauschOption[2])*-1<= uebrigePlaetze && vertauschOption[1] <= verbleibendeUmlegungen) {
						int pFreiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[index]]);
						int pBesetztePlaetze = uebrigePlaetze-(anzahlStricheProZiffer[hexZahl[index]] -2);
						int pVerbleibendeUmlegungen = verbleibendeUmlegungen-vertauschOption[1];
						int pZwischenspeicher = zwischenspeicherVertauschungen - vertauschOption[2];
						System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);

						boolean success = false;
						if(hexZahl.length-1 == index) {
							if(zwischenspeicherVertauschungen == 0 ) {
								success = true;
							}
						}else {
							success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], pVerbleibendeUmlegungen, pFreiePlaetze, pBesetztePlaetze, pZwischenspeicher, index+1);
						}
						if(success == true) {
							speicherVertauschungen.add(0, vertauschOptionArrayKonvertieren(vertauschOption));
							return true;
						}
					}
				}
			}else if(vertauschOption[3] != 0){
				if(zwischenspeicherVertauschungen>=0) {
					if((zwischenspeicherVertauschungen+vertauschOption[3] )<= freiePlaetze && vertauschOption[1]<=verbleibendeUmlegungen) {
						int pFreiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[index]]);
						int pBesetztePlaetze = uebrigePlaetze-(anzahlStricheProZiffer[hexZahl[index]] -2);
						int pVerbleibendeUmlegungen = verbleibendeUmlegungen-vertauschOption[1];
						int pZwischenspeicher = zwischenspeicherVertauschungen + vertauschOption[3];
						System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);

						boolean success = false;
						if(hexZahl.length-1 == index) {
							if(zwischenspeicherVertauschungen == 0 ) {
								success = true;
							}
						}else {
							success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], pVerbleibendeUmlegungen, pFreiePlaetze, pBesetztePlaetze, pZwischenspeicher, index+1);
						}
						if(success == true) {
							speicherVertauschungen.add(0, vertauschOptionArrayKonvertieren(vertauschOption));
							return true;
						}
					}
				}else if(zwischenspeicherVertauschungen<0) {
					int pBenutzteVertauschungen = vertauschOption[1]-vertauschOption[3];
					if((zwischenspeicherVertauschungen + vertauschOption[3])>0) {
						pBenutzteVertauschungen = pBenutzteVertauschungen + (zwischenspeicherVertauschungen + vertauschOption[3]);
					}
					if(((zwischenspeicherVertauschungen+vertauschOption[3]))<= freiePlaetze && pBenutzteVertauschungen <= verbleibendeUmlegungen){
						int pFreiePlaetze = freiePlaetze-(7- anzahlStricheProZiffer[hexZahl[index]]);
						int pBesetztePlaetze = uebrigePlaetze-(anzahlStricheProZiffer[hexZahl[index]] -2);
						int pVerbleibendeUmlegungen = verbleibendeUmlegungen-(vertauschOption[1]-vertauschOption[3]);
						int pZwischenspeicher = zwischenspeicherVertauschungen + vertauschOption[3];
						if(pZwischenspeicher >0) {
							pVerbleibendeUmlegungen = pVerbleibendeUmlegungen + pZwischenspeicher;
						}
						System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + pVerbleibendeUmlegungen + ", Zahl: " + vertauschOption[0]);

						boolean success = false;
						if(hexZahl.length-1 == index) {
							if(zwischenspeicherVertauschungen == 0 ) {
								success = true;
							}
						}else {
							success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], pVerbleibendeUmlegungen, pFreiePlaetze, pBesetztePlaetze, pZwischenspeicher, index+1);
						}
						if(success == true) {
							speicherVertauschungen.add(0, vertauschOptionArrayKonvertieren(vertauschOption));
							return true;
						}
					}
				}
			}
			counter++;
		}
		System.out.println(zwischenspeicherVertauschungen + "Verbleibende Umlegungen: " + verbleibendeUmlegungen + ", Zahl: " + hexZahl[index]);

		boolean success = false;
		if(hexZahl.length-1 == index) {
			if(zwischenspeicherVertauschungen == 0 ) {
				success = true;
			}
		}else {
			success = this.rekursivHexZahlMaximieren(hexZahl, Vertauschungsmatrix[hexZahl[index+1]], verbleibendeUmlegungen, freiePlaetze, uebrigePlaetze, zwischenspeicherVertauschungen, index+1);
		}
		if(success == true){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			temp.add(hexZahl[index]);
			temp.add(0);
			temp.add(0);
			temp.add(0);
			speicherVertauschungen.add(0,temp);
			return true;
		}
		return false;
	}
	/**
	 * Konvertiert das in der Vertauschungsmatrix angegebene int[] in eine ArrayList<Integer>, damit die Vertauschung in die Liste der Vertauschungen getan werden kann.
	 * @param arr
	 * @return
	 */
	public ArrayList<Integer> vertauschOptionArrayKonvertieren(int[] arr){
		//Diese Methode konvertiert ein Array mit dem primitiven Datentyp int zu einer ArrayList<Integer>
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i = 0; i<arr.length; i++) {
			temp.add(arr[i]);
		}
		return temp;
	}
	/**
	 * Konvertiert die in der speicherVertauschungen vorhandene ArrayList<Integer> in ein int[]
	 * @param list
	 * @return
	 */
	public int[] vertauschOptionArrayListKonvertieren(ArrayList<Integer> list) {
		int[] temp = new int[list.size()]; 
		for(int i =0; i<list.size(); i++) {
			temp[i] = list.get(i);
		}
		return temp;
	}
	/**
	 * Gibt die Umlegungen an, welche mithilfe der gespeicherten Vertauschungen, die @param pHexZahl maximieren.
	 * Außerdem werden auch die Zwischenstände der HexZahl angegeben zwischen den einzelnen Umlegungen. 
	 * Geht dabei iterativ vor.
	 * @param pSpeicherVertauschungen
	 * @param pHexZahl
	 */
	public void printOutUmlegungen(ArrayList<ArrayList<Integer>> pSpeicherVertauschungen, int[] pHexZahl){
		ArrayList<char[]> charHexZahl = new ArrayList<char[]>();
		for(int i = 0; i<pHexZahl.length; i++){
			charHexZahl.add(ziffern.get(pHexZahl[i]).toCharArray());
		}
		CharHexZahlAusgeben(charHexZahl);
		for(int i = 0; i< pSpeicherVertauschungen.size(); i++){//geht die Liste der Vertauschungen durch
			ArrayList<Integer> tempVertauschung = pSpeicherVertauschungen.get(i); // speichert die aktuelle Vertauschung als ArrayList ab							
			if(tempVertauschung.get(2) != 0 || tempVertauschung.get(3)!= 0){//braucht die Vertauschung 
				if(tempVertauschung.get(2)!=0){
					char[] tempZahl1 = charHexZahl.get(i);
					char[] tempZahl2 = ziffern.get(tempVertauschung.get(0)).toCharArray();
					while(!charArrEqual(tempZahl1, tempZahl2)){
						if(tempVertauschung.get(2)>0){
							for(int h=i+1; h<pSpeicherVertauschungen.size(); h++){
								if(pSpeicherVertauschungen.get(h).get(3)>0 && tempVertauschung.get(2)>0){
									char[] tempZahl3 = charHexZahl.get(h);
									char[] tempZahl4 = ziffern.get(pSpeicherVertauschungen.get(h).get(0)).toCharArray();
									while(tempVertauschung.get(2)>0 && pSpeicherVertauschungen.get(h).get(3)>0){
										for(int j = 0; j<tempZahl1.length; j++){
											if(tempZahl1[j] != tempZahl2[j]){
												if(tempZahl1[j] == '0'){// An Stelle j soll eine 1 in der Finalen Zahl sein, ist gerade 0
													for(int k = 0; k<tempZahl4.length; k++){
														if(tempZahl3[k] != tempZahl4[k] && tempZahl3[k] == '1'){
															System.out.println(h + "." + k + "=> " + i +"." + j + ", ");
															tempZahl3[k] = '0';
															tempZahl1[j] = '1';
															charHexZahl.set(i, tempZahl1);
															charHexZahl.set(h, tempZahl3);
															CharHexZahlAusgeben(charHexZahl);
															tempVertauschung.set(2, tempVertauschung.get(2) -1);
															tempVertauschung.set(21, tempVertauschung.get(1) -1);
															pSpeicherVertauschungen.get(h).set(3, tempVertauschung.get(3) -1);
															pSpeicherVertauschungen.get(h).set(1, tempVertauschung.get(1) -1);
															k=tempZahl2.length;
														}
													}
												}
											}
										}
									}
								} else if(tempVertauschung.get(2) == 0){
									h = pSpeicherVertauschungen.size();
								}	
							}
						}else{
							while(!charArrEqual(tempZahl1, tempZahl2)){
								for(int j = 0; j<tempZahl1.length; j++){
									if(tempZahl1[j] != tempZahl2[j]){
										if(tempZahl1[j] == '1'){
											for(int k = j; k<tempZahl2.length; k++){
												if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '0'){
													System.out.println(i+ "." + j + " => " + i + "." + k + ", ");
													tempZahl1[k] = '1';
													tempZahl1[j] = '0';
													charHexZahl.set(i, tempZahl1);
													CharHexZahlAusgeben(charHexZahl);
													k=tempZahl2.length;
												}
											}	
										} else if(tempZahl1[j] == '0'){
											for(int k = j; k<tempZahl2.length; k++){
												if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '1'){
													System.out.println(i + "." + k + " => " + i + "." + j + ", ");
													tempZahl1[k] = '0';
													tempZahl1[j] = '1';
													charHexZahl.set(i, tempZahl1);
													CharHexZahlAusgeben(charHexZahl);
													k=tempZahl2.length;
												}
											}
										}
									}
								}
							}
						}
					}
				} else{
					char[] tempZahl1 = charHexZahl.get(i);
					char[] tempZahl2 = ziffern.get(tempVertauschung.get(0)).toCharArray();
					while(!charArrEqual(tempZahl1, tempZahl2)){
						if(tempVertauschung.get(3)>0){
							for(int h=i+1; h<pSpeicherVertauschungen.size(); h++){
								if(pSpeicherVertauschungen.get(h).get(2)>0 && tempVertauschung.get(3)>0){
									char[] tempZahl3 = charHexZahl.get(h);
									char[] tempZahl4 = ziffern.get(pSpeicherVertauschungen.get(h).get(0)).toCharArray();
									while(tempVertauschung.get(3)>0 && pSpeicherVertauschungen.get(h).get(2)>0){
										for(int j = 0; j<tempZahl1.length; j++){
											if(tempZahl1[j] != tempZahl2[j]){
												if(tempZahl1[j] == '1'){// An Stelle j soll eine 0 in der Finalen Zahl sein, ist gerade 1
													for(int k = 0; k<tempZahl4.length; k++){
														if(tempZahl3[k] != tempZahl4[k] && tempZahl3[k] == '0'){
															System.out.println(i+"." + j + " => " + h + "." + k + ", ");
															tempZahl3[k] = '1';
															tempZahl1[j] = '0';
															k=tempZahl2.length;
															charHexZahl.set(i, tempZahl1);
															charHexZahl.set(h, tempZahl3);
															CharHexZahlAusgeben(charHexZahl);
															int tempVertauschungsZahl = tempVertauschung.get(3)-1;
															tempVertauschung.set(3, tempVertauschungsZahl);
															tempVertauschungsZahl = tempVertauschung.get(1) -1;
															tempVertauschung.set(1, tempVertauschungsZahl);
															tempVertauschungsZahl = 1;
															pSpeicherVertauschungen.get(h).set(2, pSpeicherVertauschungen.get(h).get(2) -1);
															pSpeicherVertauschungen.get(h).set(1, pSpeicherVertauschungen.get(h).get(1) -1);
															if(tempVertauschung.get(3) == 0){
																j=tempZahl1.length;
															}
														}
													}
												}
											}
										}
									}
								} else if(tempVertauschung.get(3) == 0){
									h = pSpeicherVertauschungen.size();
								}	
							}
						}else{
							while(!charArrEqual(tempZahl1, tempZahl2)){
								for(int j = 0; j<tempZahl1.length; j++){
									if(tempZahl1[j] != tempZahl2[j]){
										if(tempZahl1[j] == '1'){
											for(int k = j; k<tempZahl2.length; k++){
												if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '0'){
													System.out.println(i+"." + j + " => " + i +"." + k +", ");
													tempZahl1[k] = '1';
													tempZahl1[j] = '0';
													charHexZahl.set(i, tempZahl1);
													CharHexZahlAusgeben(charHexZahl);
													k=tempZahl2.length;
												}
											}
										} else if(tempZahl1[j] == '0'){
											for(int k = j; k<tempZahl2.length; k++){
												if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '1'){
													System.out.println(i+"." + k + " => " + i+ " 1." + j + ", ");
													tempZahl1[k] = '0';
													tempZahl1[j] = '1';
													charHexZahl.set(i, tempZahl1);
													CharHexZahlAusgeben(charHexZahl);
													k=tempZahl2.length;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			} else{ //funktioniert
				char[] tempZahl1 = charHexZahl.get(i);
				char[] tempZahl2 = ziffern.get(tempVertauschung.get(0)).toCharArray();
				while(!charArrEqual(tempZahl1, tempZahl2)){
						for(int j = 0; j<tempZahl1.length; j++){
							if(tempZahl1[j] != tempZahl2[j]){
								if(tempZahl1[j] == '1'){
									for(int k = j; k<tempZahl2.length; k++){
										if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '0'){
											System.out.println( i+ "." + j + " => " + i+  "." + k + ", ");
											tempZahl1[k] = '1';
											tempZahl1[j] = '0';
											charHexZahl.set(i, tempZahl1);
											CharHexZahlAusgeben(charHexZahl);
											k=tempZahl2.length;
										}
									}
								} else if(tempZahl1[j] == '0'){
									for(int k = j; k<tempZahl2.length; k++){
										if(tempZahl1[k] != tempZahl2[k] && tempZahl1[k] == '1'){
											System.out.println(i+ "." + k + " => " + i + "." + j + ", ");
											tempZahl1[k] = '0';
											tempZahl1[j] = '1';
											charHexZahl.set(i, tempZahl1);
											CharHexZahlAusgeben(charHexZahl);
											k=tempZahl2.length;
										}
									}
								}
							}
						}
					}
			}
		}
	}
	public void charArrAusgeben(char[] temp){
		for(int i = 0; i<temp.length; i++){
			System.out.print(temp[i]);
		}
	}
	public boolean charArrEqual(char[] temp1, char[] temp2){
		for(int i=0; i<temp1.length; i++){
			if(temp1[i]!=temp2[i]){
				return false;
			}
		}
		return true;
	}
	public int[] zufälligeHexZahlGenerieren(int laenge){
		int[] temp = new int[laenge];
		Random random = new Random();
		for(int i =0; i<temp.length; i++){
			temp[i] = random.nextInt(16);
		}
		return temp;
	}
	public int umlegungenGenerieren(int laenge){
		int temp = 0;
		Random r = new Random();
		Double w = laenge*0.4;
		int weite = w.intValue();
		temp = laenge + weite/2 - (r.nextInt(weite)+1);
		return temp;
	}
	public void CharHexZahlAusgeben(ArrayList<char[]> hexZahl){
		System.out.print("HexZahl = {");
		for(int i = 0; i<hexZahl.size(); i++){
			charArrAusgeben(hexZahl.get(i));
			System.out.print(" ; ");
		}
		System.out.println("}");
	}
}