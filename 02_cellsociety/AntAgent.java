/**
 * Example of an Agent class for the Foraging Ants simulation. Works in tandem with AntCell, which only decreases pheromone levels when updated.
 * This shows how the abstract Agent class can be used, and how Agents can work with Cells in a simulation.
 */

package agents;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import XML.AntHolder;
import javafx.scene.paint.Color;
import rectCells.AntCell;
import rectCells.Cell;

/**
 * AntAgent to run with AntCell. Tracks individual ants, updates their position and pheromone release.
 * @author Jack Fitzpatrick
 */
public class AntAgent extends Agent {

	private AntCell location;
	private boolean hasFood;
	
	/**
	 * Possible states of AntCells: Either empty, food, or nest.
	 */
	private static final int FOOD = 1;
	private static final int NEST = 2;

	private static final double PHEROMONE_DIFFUSION = AntHolder.getPheromoneDiffusion();	// The rate at which ants spread pheromones
	private static final Color ANT_COLOR = AntHolder.getAntColor();
	private static final int ANT_SIZE_RATIO = AntHolder.getSizeRatio();						// How relatively large ants are within a Cell
	
	public AntAgent(AntCell location) {
		super(cellWidth(location)/ANT_SIZE_RATIO);
		this.location = location;
		hasFood = false;
		setFill(ANT_COLOR);
	}
	
	/**
	 * Updates hasFood and pheromone levels for relevant cell.
	 */
	public void updateState() {
		if(hasFood) {
			returnToNest();
		} else {
			findFood();
		}
		
		if(location.getState() == FOOD) {
			if(!hasFood) {
				location.reduceDurability();		// Food cells eventually run out of food and disappear
			}
			hasFood = true;
			location.setFoodPheromones(1);			// Pheromone levels range from 0 to 1
		} else if(location.getState() == NEST) {
			hasFood = false;
			location.setNestPheromones(1);
		} else {
			location.setNestPheromones(Math.max(location.getNestPheromones(), getMaxNestPheromones() * PHEROMONE_DIFFUSION));	// Ants spread pheromones 
			location.setFoodPheromones(Math.max(location.getFoodPheromones(), getMaxFoodPheromones() * PHEROMONE_DIFFUSION));	// to neighboring cells.
		}
	}
	
	/**
	 * Mode where ant with food looks for a nest.
	 */
	private void returnToNest() {
		
		Collections.shuffle(location.getNeighbors());
		Collections.sort(location.getNeighbors(), new Comparator<Cell>() {
			
			/**
			 * Comparator to sort cell list by nest pheromone levels
			 */
			@Override
			public int compare(Cell o1, Cell o2) {
				return Double.compare(((AntCell) o2).getNestPheromones(), ((AntCell) o1).getNestPheromones());	// Switched o1 and o2, as ants are attracted
			}																									// to higher pheromone levels.
		});
				
		moveToFirst(location.getNeighbors());
	}
	
	/**
	 * Mode where ant looks for food
	 */
	private void findFood() {
		Collections.shuffle(location.getNeighbors());
		Collections.sort(location.getNeighbors(), new Comparator<Cell>() {
			/**
			 * Comparator to sort list by pheromone levels
			 */
			@Override
			public int compare(Cell o1, Cell o2) {
				return Double.compare(((AntCell) o2).getFoodPheromones(), ((AntCell) o1).getFoodPheromones());
			}
		});
		
		moveToFirst(location.getNeighbors());
	}
	
	/**
	 * Moves to first position in list
	 * @param list Of locations
	 */
	private void moveToFirst(List<Cell> list) {
		for(int k = 0; k < list.size(); k++) {
			if(((AntCell) list.get(k)).roomForAnts()) {
				((AntCell) list.get(k)).addAnt(this);
				location.removeAnt(this);
				location = (AntCell) list.get(k);
				updatePos(location);
				break;
			}
		}
	}
	
	/**
	 * Finds and returns max pheromone levels
	 * @return Max pheromones in neighbors
	 */
	private double getMaxNestPheromones() {
		double max = 0;
		for(Cell c : location.getNeighbors()) {
			if(((AntCell) c).getNestPheromones() > max) {
				max = ((AntCell) c).getNestPheromones();
			}
		}
		return  Math.max(location.getNestPheromones(), max);
	}
	
	/**
	 * Finds and returns max pheromone levels
	 * @return Max pheromones in neighbors
	 */
	private double getMaxFoodPheromones() {
		double max = 0;
		for(Cell c : location.getNeighbors()) {
			if(((AntCell) c).getFoodPheromones() > max) {
				max = ((AntCell) c).getFoodPheromones();
			}
		}
		return  Math.max(location.getFoodPheromones(), max);
	}
}
