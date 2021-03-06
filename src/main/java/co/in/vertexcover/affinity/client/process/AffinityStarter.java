package co.in.vertexcover.affinity.client.process;

import co.in.vertexcover.affinity.client.dto.Affinity;
import co.in.vertexcover.affinity.client.dto.Affinity.PROCESS;

public class AffinityStarter {

	public static void start(final String inputFilePath, final Affinity affinity) throws Exception {
		start(inputFilePath, affinity, PROCESS.INPUT_PROCESSING);
	}
	
	
	public static void start(final String inputFilePath, final Affinity affinity, final PROCESS process) throws Exception {
		try {
			
			switch (process) {
			case INPUT_PROCESSING:
				inputProcessing(inputFilePath, affinity);
				
			case AFFINITY_CALCULATION:
				affinityCalculation(affinity);
			
			case TERM_BOND_CALCULATION:
				termBondCalculation(affinity);
				
			default:
				Process.saveAffinityObject(affinity);
				break;
			}
			
		} catch(Exception e) { 
			throw e;
		}
	}
	
	
	private static void inputProcessing(final String inputFilePath, final Affinity affinity) throws Exception {
		final String OUTPUT_DIRECTORY_PATH = affinity.getConfigurations().getROOT_PATH();
		Process inputValidationProcess = new InputValidation(affinity, 
				inputFilePath, 
				OUTPUT_DIRECTORY_PATH);
		final boolean inputValidationstatus = inputValidationProcess.process(affinity);
		if(!inputValidationstatus) {
			if(inputValidationProcess.processException != null)
				throw inputValidationProcess.processException;
			else
				throw new Exception("Input validation was unsuccessful");
		}
	
		
		Process inputProcessingProcess = new InputProcessing(affinity, OUTPUT_DIRECTORY_PATH);
		final boolean inputProcessingStatus = inputProcessingProcess.process(affinity);
		if(!inputProcessingStatus) {
			if(inputProcessingProcess.processException != null)
				throw inputProcessingProcess.processException;
			else
				throw new Exception("Input processing was unsuccessful");
		}
	}
	
	
	private static void affinityCalculation(final Affinity affinity) throws Exception {
		final String OUTPUT_DIRECTORY_PATH = affinity.getConfigurations().getROOT_PATH();
		Process affinityCalculationProcess = new AffinityCalculation(affinity, OUTPUT_DIRECTORY_PATH);
		
		final boolean affinityCalculationStatus = affinityCalculationProcess.process(affinity);
		if(!affinityCalculationStatus) {
			if(affinityCalculationProcess.processException != null)
				throw affinityCalculationProcess.processException;
			else
				throw new Exception("Affinity calculation was unsuccessful");
		}
	}
	
	
	private static void termBondCalculation(final Affinity affinity) throws Exception {
		final String OUTPUT_DIRECTORY_PATH = affinity.getConfigurations().getROOT_PATH();
		Process termBondCalculationProcess = new TermBondCalculation(affinity, OUTPUT_DIRECTORY_PATH);
		
		final boolean termBondCalculationStatus = termBondCalculationProcess.process(affinity);
		if(!termBondCalculationStatus) {
			if(termBondCalculationProcess.processException != null)
				throw termBondCalculationProcess.processException;
			else
				throw new Exception("Term bond calculation was unsuccessful");
		}
	}
}
