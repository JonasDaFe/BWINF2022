import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class matheRaetsel {
	ArrayList<String> listeRechenzeichen = new ArrayList<String>();
	ArrayList<Double> entfernteErgebnisse = new ArrayList<Double>();
	HashMap<Double, String> Gleichungen = new HashMap<Double, String>();
	int[] zahlen;
	String[] rechenzeichenOriginal = {"+", "-", "*", ":"};
	String[] rechenzeichenErweitert = {"+", "-", "*", ":", "^"};
	public static void main(String[] args) {
		System.out.println("test1");
		matheRaetsel t = new matheRaetsel();
	}
	public matheRaetsel() {
		listeRechenzeichen.add("+");
		listeRechenzeichen.add("-");
		listeRechenzeichen.add("*");
		listeRechenzeichen.add(":");
		listeRechenzeichen.add("(");
		listeRechenzeichen.add(")");
		listeRechenzeichen.add("^");
		raetselErstellen();
	}
	public void raetselErstellenMultithreading(int laenge) {
		zahlen = this.generateRandomArr(laenge);
		ExecutorService es = Executors.newCachedThreadPool();
		List<MultithreadRaetselOriginal> tasklist = new ArrayList<>();
		for(int i=0; i<rechenzeichenOriginal.length; i++) {
			MultithreadRaetselOriginal task = new MultithreadRaetselOriginal(1, zahlen.length-1, "3", rechenzeichenOriginal[i], zahlen);
			tasklist.add(task);
		}
		List<Future<RaetselDatapack>> resultList =null;
		try {
			resultList = es.invokeAll(tasklist);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		es.shutdown();
		System.out.println("Results are there");
		RaetselDatapack data = new RaetselDatapack(new ArrayList<Double>(), new HashMap<Double, String>());
		for(int i = 0; i<resultList.size(); i++) {
			Future<RaetselDatapack> future = resultList.get(i);
			try {
				RaetselDatapack result = future.get();
				data = data.mergeDatapacks(data, result);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<zahlen.length; i++) {
			System.out.print(zahlen[i] + ",");
		}
		System.out.println();
		
		printOutHashMap(data.gleichungen);
		Object[] tempSetKeys =  data.gleichungen.keySet().toArray();
		Random random = new Random();
		int randomIndex = random.nextInt(tempSetKeys.length-1)+1;
		String raetsel = data.gleichungen.get(tempSetKeys[randomIndex]) + "=" + tempSetKeys[randomIndex];
		System.out.println(raetsel);
	}
	public boolean raetselErstellen() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Geben sie bitte die Länge des Rätsels an das sie haben möchten: (1-15 empfohlen, darüber dauert es mitunter länger)");
		int laenge = Integer.valueOf(scan.nextLine());
		System.out.println("Wenn sie irgendwann während dem Prozess abbrechen wollen, geben sie RESET ein.");
		boolean zahlenBestaetigt = false;
		while(zahlenBestaetigt == false){
			System.out.print("Zahlen des Rätsels: ");
			zahlen = this.generateRandomArr(laenge);
			for(int i=0; i<zahlen.length; i++) {
				System.out.print(zahlen[i] + ",");
			}
			System.out.println();
			System.out.println("Möchten sie diese Zahlen benutzen? Geben sie JA oder NEIN ein. Bei NEIN werden neue Zahlen generiert");
			String input = scan.nextLine();
			if(input.equals("JA")){
				zahlenBestaetigt = true;
			} else if(input.equals("NEIN")){
				zahlenBestaetigt = false;
			} else{
				return false;
			}
		}
		boolean varianteBestaetigt = false;
		while(varianteBestaetigt == false){
			System.out.println("Variante 1: Operatoren = {+,-,*,:}");
			System.out.println("Variante 2: Operatoren = {+,-,*,:, ^} mit Klammern");
			System.out.println("Geben sie 1 für Variante 1 ein und 2 für Variante 2");
			String input = scan.nextLine();
			if(input.equals("1")){
				ExecutorService es = Executors.newCachedThreadPool();
				List<MultithreadRaetselOriginal> tasklist = new ArrayList<>();
				for(int i=0; i<rechenzeichenOriginal.length; i++) {
					MultithreadRaetselOriginal task = new MultithreadRaetselOriginal(1, zahlen.length-1, String.valueOf(zahlen[0]), rechenzeichenOriginal[i], zahlen);
					tasklist.add(task);
				}
				List<Future<RaetselDatapack>> resultList =null;
				try {
					resultList = es.invokeAll(tasklist);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				es.shutdown();
				System.out.println("Results are there");
				RaetselDatapack data = new RaetselDatapack(new ArrayList<Double>(), new HashMap<Double, String>());
				for(int i = 0; i<resultList.size(); i++) {
					Future<RaetselDatapack> future = resultList.get(i);
					try {
						RaetselDatapack result = future.get();
						data = data.mergeDatapacks(data, result);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				Object[] tempSetKeys =  data.gleichungen.keySet().toArray();
				Random random = new Random();
				Boolean raetselBesteatigt = false;
				while(raetselBesteatigt == false){
					int randomIndex = random.nextInt(tempSetKeys.length-1)+1;
					while(String.valueOf(tempSetKeys[randomIndex]).charAt(0) == '-'){
						randomIndex = random.nextInt(tempSetKeys.length-1)+1;
					}
					String raetselLösung = data.gleichungen.get(tempSetKeys[randomIndex]) + "=" + tempSetKeys[randomIndex];
					String raetsel = raetselVerstecken(raetselLösung);
					System.out.println("Dies ist ihr Rätsel:");
					System.out.println(raetsel);
					System.out.println(raetselLösung);
					System.out.println("Möchten sie dieses Rätsel lösen oder neues Rätsel bekommen? Tippen sie LOESEN oder NEU");
					String input2 = scan.nextLine();
					if(input2.equals("LOESEN")){
						System.out.println("Haben sie das Rätsel gelöst? Wenn ja tippen sie JA um die Lösung zu erhalten");
						System.out.println(raetselLösung);
						raetselBesteatigt = true;
						varianteBestaetigt = true;
					} else if(input2.equals("NEU")){
						raetselBesteatigt = false;
					} else{
						return false;
					}
				}
			} else if(input.equals("2")){
				System.out.println("Warten sie, bis ihr Rätsel fertig generiert ist");
				for(int i=0; i<rechenzeichenErweitert.length; i++) {
					this.testRaetselOriginalMitKlammern(1, zahlen.length-1, String.valueOf(zahlen[0]), rechenzeichenErweitert[i], 0);
				}
				Object[] tempSetKeys =  Gleichungen.keySet().toArray();
				System.out.println(tempSetKeys.length);
				Random random = new Random();
				Boolean raetselBesteatigt = false;
				while(raetselBesteatigt == false){
					int randomIndex = random.nextInt(tempSetKeys.length);
					while(String.valueOf(tempSetKeys[randomIndex]).charAt(0) == '-'){
						randomIndex = random.nextInt(tempSetKeys.length-1)+1;
					}
					String raetselLösung = Gleichungen.get(tempSetKeys[randomIndex]) + "=" + tempSetKeys[randomIndex];
					String raetsel = raetselVerstecken(raetselLösung);
					System.out.println("Dies ist ihr Rätsel:");
					System.out.println(raetsel);
					System.out.println(raetselLösung);
					System.out.println("Möchten sie dieses Rätsel lösen oder neues Rätsel bekommen? Tippen sie LÖSEN oder NEU");
					String input2 = scan.nextLine();
					if(input2.equals("LÖSEN")){
						System.out.println("Haben sie das Rätsel gelöst? Wenn ja tippen sie JA um die Lösung zu erhalten");
						System.out.println(raetselLösung);
						raetselBesteatigt = true;
						varianteBestaetigt = true;
					} else if(input2.equals("NEU")){
						raetselBesteatigt = false;
					} else{
						return false;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}
	public void printOutHashMap(HashMap<Double, String> tempMap) {
		Object[] tempSetKeys =  tempMap.keySet().toArray();
		System.out.println("test 1" + tempSetKeys.length);
		for(int i=0; i<tempSetKeys.length; i++) {
			System.out.println("test 2");
			System.out.println(tempMap.get(tempSetKeys[i]) + "=" + tempSetKeys[i]);
		}
	}
	public int[] generateRandomArr(int tiefe) {
		int[] tempArr = new int[tiefe];
		Random r = new Random();
		for(int i=0; i<tiefe; i++) {
			boolean tempBool = r.nextBoolean();
			if(tempBool == true){
			tempArr[i] = r.nextInt(9)+1;
			} else{
				tempArr[i] = -r.nextInt(9)-1;
			}
		}
		return tempArr;
	}
	public boolean testRaetselOriginal(int currTiefe, int maxTiefe, String oldEquation, String operator) {//maxTiefe= Anzahl der Operatoren
		HashMap<Double, String> tempMap = new HashMap<Double, String>();
		//Methode erstellen, welche eine zufällige Liste an Zahlen erstellt, welche für das gesamte Rätsel verwendet werden.
		if(currTiefe == maxTiefe) {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = this.evalEquation(currEquation);
			System.out.println(currEquation + "=" + ergebniss);
			if(ergebniss != null && !tempMap.containsKey(ergebniss) && !entfernteErgebnisse.contains(ergebniss) ) {
				Gleichungen.put(ergebniss, currEquation);
				entfernteErgebnisse.add(ergebniss);//wird trotzdem zur Liste hinzugefügt, damit auch die errechneten Ergebnisse aus den anderen Teilbäumen berücksichtigt werden können
			}else if(ergebniss != null) {
				Gleichungen.remove(ergebniss);
				if(!entfernteErgebnisse.contains(ergebniss)) {
					entfernteErgebnisse.add(ergebniss);
				}
			}
			return true;
			//Abbruchmethode schreiben
			//Ergebniss zur HashMap hinzufügen
		}else {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = 1.0;
			if(operator.equals(":")) {
				ergebniss = this.evalEquation(currEquation);
			}
			System.out.println(currEquation);
			if(ergebniss != null) {
				currTiefe++;
				for(int i=0; i<rechenzeichenOriginal.length; i++) {
					this.testRaetselOriginal(currTiefe, maxTiefe, currEquation, rechenzeichenOriginal[i]);
				}
			}
		}
		//Klammern mechanik einfügen;
		
		return true;
	}
	public HashMap<Double, String> testRaetselOriginalMitKlammern(int currTiefe, int maxTiefe, String oldEquation, String operator, int AnzahlOffeneKlammern) {//maxTiefe= Anzahl der Operatoren
		//Methode erstellen, welche eine zufällige Liste an Zahlen erstellt, welche für das gesamte Rätsel verwendet werden.
		if(currTiefe == maxTiefe) {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = this.evalEquation(currEquation);
			System.out.println(currEquation + "=" + ergebniss);
			if(ergebniss != null && !Gleichungen.containsKey(ergebniss) && !entfernteErgebnisse.contains(ergebniss) ) {
				Gleichungen.put(ergebniss, currEquation);
			}else if(ergebniss != null) {
				Gleichungen.remove(ergebniss);
				if(!entfernteErgebnisse.contains(ergebniss)) {
					entfernteErgebnisse.add(ergebniss);
				}
			}
			return Gleichungen;
			//Abbruchmethode schreiben
			//Ergebniss zur HashMap hinzufügen
		}else {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = 1.0; 
			if(operator.equals(":")) {
				ergebniss = this.evalEquation(currEquation);
			}
			System.out.println(currEquation);
			if(ergebniss != null || AnzahlOffeneKlammern > 0) {
				currTiefe++;
				for(int i=0; i<rechenzeichenOriginal.length; i++) {
					System.out.println("1");
					Gleichungen.putAll(this.testRaetselOriginalMitKlammern(currTiefe, maxTiefe, currEquation, rechenzeichenOriginal[i], AnzahlOffeneKlammern));
				}
				if(AnzahlOffeneKlammern<(maxTiefe-currTiefe-2)) {
					int tempAnzahloffeneKlammern = AnzahlOffeneKlammern+1;
					System.out.println("2");
					Gleichungen.putAll(this.testRaetselOriginalMitKlammern(currTiefe, maxTiefe, currEquation, "(", tempAnzahloffeneKlammern));
				}
				if(AnzahlOffeneKlammern>0 && !operator.equals("(")) {
					int tempAnzahloffeneKlammern = AnzahlOffeneKlammern-1;
					System.out.println("3");
					Gleichungen.putAll(this.testRaetselOriginalMitKlammern(currTiefe, maxTiefe, currEquation, ")", tempAnzahloffeneKlammern));
				}
			}
		}
		//Klammern mechanik einfügen;
		
		return Gleichungen;
	}
	public Double evalEquation(String equ){
		ArrayList<String> equArr = this.convertStringToArrayList(equ);
 		if(equArr.contains("(") && equArr.contains(")")){
			int anzahlKlammernAuf = 0;
			int anzahlKlammernZu = 0;
			for(int g=0; g<equArr.size(); g++) {
				if(equArr.get(g).equals("(")) {
					anzahlKlammernAuf++;
					for(int i = g+1; i<equArr.size() ; i++){
						if(equArr.get(i).equals("(")){
							anzahlKlammernAuf++;
						}else if(equArr.get(i).equals(")")){
							anzahlKlammernZu++;
							if(anzahlKlammernAuf == anzahlKlammernZu){
								if(i+1<equArr.size()) {
									if(!listeRechenzeichen.contains(equArr.get(i+1))){
										equArr.add(i+1, "*");//soll vorherr passieren bevor die klammern rausgekuerzt werden
									}
								}
								Double tempErgebniss = evalEquation(this.convertArrayListToString(equArr, g+1, i-1));
								if(tempErgebniss == null) { 
									return null; //prueft ob ein valides Ergebniss gefunden wurde
								}
								equArr = this.shortEquation(equArr, String.valueOf(tempErgebniss), g, i);
								if(g-1>= 0) {
									if(listeRechenzeichen.contains(equArr.get(g-1)) == false){
										equArr.add(g,"*"); //nachgucken, ob es wirklich so funktioniert
									}
								}
								System.out.println(this.convertArrayListToString(equArr,0,equArr.size()-1));
								i=equArr.size();
							}
						}
					}
					if(anzahlKlammernAuf != anzahlKlammernZu) {
						return null;
					}
				}
			}
		}
		if(equArr.contains("(") || equArr.contains(")")) {
			return null;
		}
		while(equArr.contains("^")) {
			for(int i=0; i<equArr.size(); i++){
				if(equArr.get(i).equals("^")){
					double temp = Math.pow(Double.valueOf(equArr.get(i-1)),Double.valueOf(equArr.get(i+1)));
					equArr = this.shortEquation(equArr, String.valueOf(temp), i-1, i+1);
				}
			}
		}
		while(equArr.contains("*")  || equArr.contains(":")) {
			for(int i=0; i<equArr.size(); i++){
				if(equArr.get(i).equals("*")){
					double temp = (Double.valueOf(equArr.get(i-1)) * Double.valueOf(equArr.get(i+1)));
					equArr = this.shortEquation(equArr, String.valueOf(temp), i-1, i+1);
				}else if(equArr.get(i).equals(":")){
					double temp = (Double.valueOf(equArr.get(i-1)) / Double.valueOf(equArr.get(i+1)));
					if(temp%1 != 0) {
						return null;
					}
					equArr = this.shortEquation(equArr, String.valueOf(temp), i-1, i+1);
				}
			}
		}
		while(equArr.contains("+")  || equArr.contains("-")) {
			for(int i=0; i<equArr.size(); i++){
				if(equArr.get(i).equals("+")){
					double temp = (Double.valueOf(equArr.get(i-1)) + Double.valueOf(equArr.get(i+1)));
					equArr = this.shortEquation(equArr, String.valueOf(temp), i-1, i+1);
				}else if(equArr.get(i).equals("-")){
					double temp = (Double.valueOf(equArr.get(i-1)) - Double.valueOf(equArr.get(i+1)));
					equArr = this.shortEquation(equArr, String.valueOf(temp), i-1, i+1);
				}
			}
		}
		if(equArr.size() == 1) {
			return Double.valueOf(equArr.get(0));
		}
		return null;
	}
	public ArrayList<String> shortEquation(ArrayList<String> originalEqu, String Ergebniss, int startPosition, int endPosition){
		ArrayList<String> tempEqu = (ArrayList<String>) originalEqu.clone();
		for(int i=startPosition; i<= endPosition; i++) {
			tempEqu.remove(startPosition);
		}
		tempEqu.add(startPosition, Ergebniss);
		return tempEqu; 
	}
	public ArrayList<String> convertStringToArrayList(String equ){
		//System.out.println("Start Conversion");
		ArrayList<String> converted = new ArrayList<String>();
		char lastOperator = '+';
		boolean wasOperator = true;
		for(int i =0; i<equ.length(); i++) {
			//System.out.print(i +": ");
			if(!listeRechenzeichen.contains(equ.substring(i,i+1))) {
				int j=i;
				while(listeRechenzeichen.indexOf(equ.substring(j,j+1)) == -1) {
					j++;
					if(j == equ.length()) {
						break;
					}
				}
				converted.add(equ.substring(i,j));
				//System.out.println(equ.substring(i,j));
				i=j-1;
				wasOperator = false;
			} else if( wasOperator == true && equ.charAt(i) == '-'){

				int j=i+1;
				while(listeRechenzeichen.indexOf(equ.substring(j,j+1)) == -1) {
					j++;
					if(j == equ.length()) {
						break;
					}
				}
				converted.add(equ.substring(i,j));
				//System.out.println(equ.substring(i,j));
				i=j-1;
				wasOperator = false;
			}else {
				converted.add(String.valueOf(equ.charAt(i)));
				wasOperator = true;
				//System.out.println(String.valueOf(equ.charAt(i)));
			}
		}
		return converted;
	}
	public String convertArrayListToString(ArrayList<String> equList, int start, int ende){
		String temp = equList.get(start);
		for(int i =start+1; i<=ende; i++) {
			temp += equList.get(i);
		}
		return temp;
	}
	public void testDouble() {
		String temp = "3+4";
		ArrayList<String> tempList = convertStringToArrayList(temp);
		for(int i=0; i<tempList.size(); i++) {
			System.out.print(tempList.get(i) + " ");
		}
		for(int i=0; i<tempList.size(); i++){
			if(tempList.get(i).equals("+")){
				double tempDouble = (Double.valueOf(tempList.get(i-1)) + Double.valueOf(tempList.get(i+1)));
				System.out.println("Ausgang: " + tempList.get(i-1) + ", " + tempList.get(i+1) 
				+ " DoubleValues: " + Double.valueOf(tempList.get(i-1)) + ", "+ Double.valueOf(tempList.get(i+1))
				+ "Ergebniss: " + tempDouble);
			}
		
		}
	}
	public String raetselVerstecken(String raetsel){
		String tempRaetsel = raetsel;
		ArrayList<String> converted = new ArrayList<String>();
		boolean wasOperator = true;
		for(int i =0; i<raetsel.length(); i++) {
			if(raetsel.charAt(i) == '='){
				converted.add(raetsel.substring(i, raetsel.length()-1));
			}
			//System.out.print(i +": ");
			if(!listeRechenzeichen.contains(raetsel.substring(i,i+1))) {
				int j=i;
				while(listeRechenzeichen.indexOf(raetsel.substring(j,j+1)) == -1) {
					j++;
					if(j == raetsel.length()) {
						break;
					}
				}
				converted.add(raetsel.substring(i,j));
				i=j-1;
				wasOperator = false;
			} else if( wasOperator == true && raetsel.charAt(i) == '-'){

				int j=i+1;
				while(listeRechenzeichen.indexOf(raetsel.substring(j,j+1)) == -1) {
					j++;
					if(j == raetsel.length()) {
						break;
					}
				}
				converted.add(raetsel.substring(i,j));
				i=j-1;
				wasOperator = false;
			}else {
				converted.add(String.valueOf('?'));
				wasOperator = true;
			}
		}
		tempRaetsel = convertArrayListToString(converted, 0, converted.size()-1);
		return tempRaetsel;
	}
}