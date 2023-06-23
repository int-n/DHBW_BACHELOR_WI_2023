import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SupplierAgent extends Agent {

	private int[][] costMatrix;
	private ArrayList<int[]> proposalsClone = new ArrayList<int[]>();
	private ArrayList<Integer> costs = new ArrayList<Integer>();

	@Override
	public ArrayList<Integer> getCosts() {
		return costs;
	}

	@Override
	public void setCosts(ArrayList<Integer> costs) {
		this.costs = costs;
	}

	@Override
	public ArrayList<int[]> getProposalsClone() {
		return proposalsClone;
	}

    @Override
	public void setProposalsClone(ArrayList<int[]> proposalsClone) {
		this.proposalsClone = proposalsClone;
	}

	public SupplierAgent(File file) throws FileNotFoundException {

		Scanner scanner = new Scanner(file);
		int dim = scanner.nextInt();
		costMatrix = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				int x = scanner.nextInt();
				costMatrix[i][j] = x;
			}
		}
		scanner.close();
	}

	public boolean vote(int[] contract, int[] proposal) {
		int costContract = evaluate(contract);
		int costProposal = evaluate(proposal);
		if (costProposal < costContract)
			return true;
		else
			return false;
	}

	public int getContractSize() {
		return costMatrix.length;
	}

	public void printUtility(int[] contract) {
		System.out.print(evaluate(contract));
	}

	
	private int evaluate(int[] contract) {

		int result = 0;
		for (int i = 0; i < contract.length - 1; i++) {
			int zeile = contract[i];
			int spalte = contract[i + 1];
			result += costMatrix[zeile][spalte];
		}

		return result;
	}


	@Override
	public void initProposalsClone(ArrayList<int[]> proposals) {
		for(int i=0;i<proposals.size();i++)this.proposalsClone.add(proposals.get(i));
		for(int[] array: proposalsClone) {
			costs.add(evaluate(array));
		}
	}
}