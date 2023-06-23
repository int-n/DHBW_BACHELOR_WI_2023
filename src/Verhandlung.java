import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;


//SIMULATION!

/*
 * Was ist das "Problem" der nachfolgenden Verhandlung?
 * - Fr�he Stagnation, da die Agenten fr�hzeitig neue Contracte ablehnen
 * - Verhandlung ist nur f�r wenige Agenten geeignet, da mit Anzahl Agenten auch die Stagnationsgefahr w�chst
 * 
 * Aufgabe: Entwicklung und Anaylse einer effizienteren Verhandlung. Eine Verhandlung ist effizienter, wenn
 * eine fr�he Stagnation vermieden wird und eine sozial-effiziente Gesamtl�sung gefunden werden kann.
 * 
 * Ideen:
 * - Agenten m�ssen auch Verschlechterungen akzeptieren bzw. zustimmen, die einzuhaltende Mindestakzeptanzrate wird vom Mediator vorgegeben
 * - Agenten schlagen alternative Kontrakte vor
 * - Agenten konstruieren gemeinsam einen Kontrakt
 * - In jeder Runde werden mehrere alternative Kontrakte vorgeschlagen
 * - Alternative Konstruktionsmechanismen f�r Kontrakte
 * - Ausgleichszahlungen der Agenten (nur m�glich, wenn beide Agenten eine monetaere Zielfunktion haben
 * 
 */


public class Verhandlung {	

		public static void main(String[] args) {
			int[] contract, proposal;
			Agent agA, agB;
			Mediator med;
			int maxRounds, round;
			boolean voteA, voteB;
			
			try{
				agA = new SupplierAgent(new File("data/daten3ASupplier_200.txt"));
				agB = new CustomerAgent(new File("data/daten4BCustomer_200_5.txt"));
				//agB = new CustomerAdvanced(new File("data/daten4BCustomer_200_5.txt"));
				med = new Mediator(agA.getContractSize(), agB.getContractSize());
				
				//Verhandlung initialisieren
				ArrayList<int[]> proposals=new ArrayList<int[]>();
				contract  = med.initContract();
				for(int i = 0; i < 10; i++) {
					proposals.add(med.constructProposal(contract));
				}
				agB.initProposalsClone(proposals);
				agA.initProposalsClone(proposals);
				for(int i = 0; i < 10; i++) {
					System.out.println(Arrays.toString(agB.getProposalsClone().get(i)));
					System.out.println(agB.getCosts().get(i));
				}


				
			}
			catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}
		}
		
		public static void ausgabe(Agent a1, Agent a2, int i, int[] contract){
			System.out.print(i + " -> " );
			a1.printUtility(contract);
			System.out.print("  ");
			a2.printUtility(contract);
			System.out.println();
		}

}