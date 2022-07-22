import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadRaetselOriginal implements Callable<RaetselDatapack>{
	int currTiefe;
	int maxTiefe;
	String oldEquation;
	String operator;
	int[] zahlen;
	
	String[] rechenzeichenOriginal = {"+", "-", "*", ":"};
	ArrayList<String> listeRechenzeichen = new ArrayList<String>();
	RaetselDatapack data = new RaetselDatapack(new ArrayList<Double>(), new HashMap<Double, String>());
	public MultithreadRaetselOriginal(int pCurrTiefe, int pMaxTiefe, String pOldEquation, String pOperator, int[] pZahlen) {
		currTiefe = pCurrTiefe;
		maxTiefe = pMaxTiefe;
		oldEquation = pOldEquation;
		operator = pOperator;
		zahlen = pZahlen;
		
		listeRechenzeichen.add("+");
		listeRechenzeichen.add("-");
		listeRechenzeichen.add("*");
		listeRechenzeichen.add(":");
		listeRechenzeichen.add("(");
		listeRechenzeichen.add(")");
		listeRechenzeichen.add("^");
	}

	public RaetselDatapack call() {//maxTiefe= Anzahl der Operatoren
		//Methode erstellen, welche eine zuf�llige Liste an Zahlen erstellt, welche f�r das gesamte R�tsel verwendet werden.
		if(currTiefe == maxTiefe) {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = this.evalEquation(currEquation);
		//	System.out.println(currEquation + "=" + ergebniss);
			if(ergebniss != null && !data.gleichungen.containsKey(ergebniss) && !data.ergebnisse.contains(ergebniss) ) {
				data.gleichungen.put(ergebniss, currEquation);
				data.ergebnisse.add(ergebniss);//wird trotzdem zur Liste hinzugef�gt, damit auch die errechneten Ergebnisse aus den anderen Teilb�umen ber�cksichtigt werden k�nnen
			}else if(ergebniss != null) {
				data.gleichungen.remove(ergebniss);
				if(!data.ergebnisse.contains(ergebniss)) {
					data.ergebnisse.add(ergebniss);
				}
			}
			return data;
			//Abbruchmethode schreiben
			//Ergebniss zur HashMap hinzuf�gen
		}else {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = 1.0;
			if(operator.equals(":")) {
				ergebniss = this.evalEquation(currEquation);
			}
//			System.out.println(currEquation);
			if(ergebniss != null) {
				currTiefe++;
				if(currTiefe == 1 && maxTiefe >= 8) {
					ExecutorService es = Executors.newFixedThreadPool(6);
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
					//System.out.println("Results are there");
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
				}else {
					for(int i=0; i<rechenzeichenOriginal.length; i++) {
						RaetselDatapack tempDatapack2 = this.testRaetselOriginal(currTiefe, maxTiefe, currEquation, rechenzeichenOriginal[i]);
						data = data.mergeDatapacks(data, tempDatapack2);
					}
				}
			}
		}
		return data;
	}
	public RaetselDatapack testRaetselOriginal(int currTiefe, int maxTiefe, String oldEquation, String operator) {//maxTiefe= Anzahl der Operatoren
		RaetselDatapack tempDatapack = new RaetselDatapack(new ArrayList<Double>(), new HashMap<Double, String>());
		//Methode erstellen, welche eine zuf�llige Liste an Zahlen erstellt, welche f�r das gesamte R�tsel verwendet werden.
		if(currTiefe == maxTiefe) {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = this.evalEquation(currEquation);
			//System.out.println(currEquation + "=" + ergebniss);
			if(ergebniss != null && !tempDatapack.gleichungen.containsKey(ergebniss) && !tempDatapack.ergebnisse.contains(ergebniss) ) {
				tempDatapack.gleichungen.put(ergebniss, currEquation);
				tempDatapack.ergebnisse.add(ergebniss);//wird trotzdem zur Liste hinzugef�gt, damit auch die errechneten Ergebnisse aus den anderen Teilb�umen ber�cksichtigt werden k�nnen
			}else if(ergebniss != null) {
				tempDatapack.gleichungen.remove(ergebniss);
				if(!tempDatapack.ergebnisse.contains(ergebniss)) {
					tempDatapack.ergebnisse.add(ergebniss);
				}
			}
			return tempDatapack;
			//Abbruchmethode schreiben
			//Ergebniss zur HashMap hinzuf�gen
		}else {
			String currEquation = oldEquation + operator + zahlen[currTiefe];
			Double ergebniss = 1.0;
			if(operator.equals(":")) {
				ergebniss = this.evalEquation(currEquation);
			}
	//		System.out.println(currEquation);
			if(ergebniss != null) {
				currTiefe++;
				for(int i=0; i<rechenzeichenOriginal.length; i++) {
					RaetselDatapack tempDatapack2 = this.testRaetselOriginal(currTiefe, maxTiefe, currEquation, rechenzeichenOriginal[i]);
					tempDatapack = tempDatapack.mergeDatapacks(tempDatapack, tempDatapack2);
				}
			}
		}
		//Klammern mechanik einf�gen;
		
		return tempDatapack;
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
							//	System.out.println(this.convertArrayListToString(equArr,0,equArr.size()-1));
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
}
