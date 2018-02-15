/**
 * Abstract Agent class. I believe this is well designed, as it shows the modularity and flexibility of the Agent classes. 
 * Each Agent is able to update its own state and position. Agents can be used for any type of cell or grid, along with their respective AgentMover class.
 */

package agents;

import javafx.scene.shape.Circle;
import rectCells.Cell;

/**
 * Abstract class for Agents, which accompany cells. They will all be circles superimposed on the cell grid.
 * @author Jack Fitzpatrick
 */
public abstract class Agent extends Circle {
	
	/**
	 * Constructor called, only argument is radius of Agent
	 * @param radius Radius of agent
	 */
	public Agent(double radius) {
		super(radius);
	}
	
	/**
	 * Updates the agent state
	 */
	public abstract void updateState();
	
	/**
	 * Updates the agent position
	 * @param c The "owner" cell of the agent, where it is moving to
	 */
	public void updatePos(Cell c) {
		setCenterX(c.getPoints().get(0) + cellWidth(c)/2);
		setCenterY(c.getPoints().get(1) + cellWidth(c)/2);
		this.toFront();
	}
	
	/**
	 * Finds the width of a cell. Useful in many agent classes, as well as updatePos.
	 * @param c Cell to find width of
	 * @return Width of cell
	 */
	public static double cellWidth(Cell c) {						// As polygon points are set in a clockwise direction, x position of 
		return Math.abs(c.getPoints().get(2)-c.getPoints().get(0));	// first and second points are CELL_WIDTH apart.
	}
}
