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

				contract = med.initContract();
				int maxProposals = 101;

				// Erzeugen der Proposals
				ArrayList<int[]> proposals = new ArrayList<int[]>();
				for(int i=0; i < maxProposals; i++) {
					proposals.add(med.constructProposal(contract));
				}
				agA.initProposalsClone(proposals);
				agB.initProposalsClone(proposals);

				int removedElement = 0;

				// Bestimmung, wer mit dem Rausstreichen der ungünstigsten Lösung beginnt
				boolean coin = Math.random() < 0.5 ;

				// Rausstreichen der ungünstigsten Lösungen
				for(round = 0; round < (proposals.size() + 1) / 2; round++) {

					boolean firstLoop;

					if (round == 0) {
						firstLoop = true;
					} else {
						firstLoop = false;
					}

					if (coin) {
						removedElement = agA.identifyWorstProposal(firstLoop, removedElement);
						System.out.println("Supplier: " + removedElement);
						firstLoop = false;
						removedElement = agB.identifyWorstProposal(firstLoop, removedElement);
						System.out.println("Customer: " + removedElement);
					} else {
						removedElement = agB.identifyWorstProposal(firstLoop, removedElement);
						System.out.println("Customer: " + removedElement);
						firstLoop = false;
						removedElement = agA.identifyWorstProposal(firstLoop, removedElement);
						System.out.println("Supplier: " + removedElement);
					}

				}

				System.out.println("Proposal: " + agA.getCosts().get(0) + " : " + agB.getCosts().get(0));
				System.out.println("Supplier: " + agA.getCosts().toString());
				System.out.println("Customer: " + agB.getCosts().toString());


				
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