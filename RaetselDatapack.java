import java.util.ArrayList;
import java.util.HashMap;

public class RaetselDatapack {
	ArrayList<Double> ergebnisse;
	HashMap<Double, String> gleichungen;
	public RaetselDatapack(ArrayList<Double> pErgebnisse, HashMap<Double, String> pGleichungen) {
		ergebnisse = pErgebnisse;
		gleichungen = pGleichungen;
	}
	/**
	 * This Methode is used to combine the Data of pData1 and pData 2 
	 * and return it afterwards as an Object containing both Datasets
	 * the important thing to remember is that duplicates are beeing removed from the hashMap 
	 * but stay in the ArrayList for future identification
	 * @param pData1 
	 * @param pData2
	 * @return the combined Data of pData1 and pData2
	 */
	public RaetselDatapack mergeDatapacks(RaetselDatapack pData1, RaetselDatapack pData2) {
		RaetselDatapack tempDatapack = new RaetselDatapack(new ArrayList<Double>(), new HashMap<Double, String>());
		for(int i=0; i<pData1.ergebnisse.size(); i++) {
			while(pData2.ergebnisse.contains(pData1.ergebnisse.get(i))) {
				pData2.ergebnisse.remove(pData1.ergebnisse.get(i));
				pData2.gleichungen.remove(pData1.ergebnisse.get(i));
				pData1.gleichungen.remove(pData1.ergebnisse.get(i));
			}
		}
		tempDatapack.ergebnisse.addAll(pData1.ergebnisse);
		tempDatapack.ergebnisse.addAll(pData2.ergebnisse);
		tempDatapack.gleichungen.putAll(pData1.gleichungen);
		tempDatapack.gleichungen.putAll(pData2.gleichungen);
		return tempDatapack;
	}
}
