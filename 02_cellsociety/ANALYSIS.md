CompSci 308: Cell Society Analysis
===================

> Assignment: [Cell Society](http://www.cs.duke.edu/courses/compsci308/current/assign/02_cellsociety/)


Design Review
=======

### Overall Design

The overall design of the program is broken down into the following categories:
 1) XML Reader and Parser - The XML reader takes a specified file for a specific simulation and reads through it, pulling out relevant strings. The parser then allocates these variables into DataHolders, which is a group of classes that "holds" the read data statically. Each simulation has its own holder, that is filled with data from its respective simulation file.
 2) Main and MainView - Main is the main file for the program, that starts the application. MainView is a static class that governs the GUI and instantiates all the required resources for the simulations. MainView interacts with specific grid classes to instantiate those grids and set neighbors. It has no interactions with the XML readers or DataHolders. Other classes in the View package are ButtonView and ChartView, which set up the GUIs for the buttons and chart, respectively. Similarly, the buttons package contains each type of button used in the GUI, and their setup specifications. The actions from these buttons mostly pertain to simulation steps and speed, which means they mostly interact with MainView.
 3) Grids - Both triangular and rectangular, grids instantiate cells to be used in the simulation, and trigger them to update their states. Grids use information passed from DataHolders to set the initial states of the cells, and create the 2D array.
 4) Neighbors - The neighbors package is used to set the neighbors for each cell. The logic for this can get quite complex, so each new neighborhood setup is a different class. They are used from MainView on cell grids to set the neighborCells array in each cell. They basically just contain the neighborhood logic, however.
 5) Cells - These classes house the state logic for each simulation. The neighborStates array of each cell is updated before their own states, so that the simulation is effectively synchronous. Simulation specific cells get their simulation variables from their respective DataHolders. Other than that, the only contact they have to other classes is through grid, which tells them what their initial state is, and when to update. They also have event handlers for when a mouse is clicked or dragged over them, which will change their states. 
 6) Agents - Agents are "helper" classes for certain simulation cell classes, such as foraging ants and sugar scape. These agents are separate entities, denoted as colored circle nodes, that can move from cell to cell. They can interact with their home cells and with each other, and have update state methods of their own. "Mover" classes are used to track these agents, and update them before the cells update. Their internal logic is dependent on the specific simulation. They also interact with DataHolder to get variables from the simulation XML documents. 

To add a new simulation, you must add a new Cell class, extending Cell and implementing IGrid. This cell must have an update state method that updates its state based on its neighborStates array. Agents can be used if needed. A new XML document and corresponding DataHolder must be created and filled from the XML Parser, and a grid (with the correct type of neighborhood) must be created and initialized from MainView.

Overall, dependencies are fairly clear, and only exist through public classes. There are some back channels between agents and MainView, as agents will get the root Group from MainView and add/remove themselves from it. Other than that, the dependencies are clear.

I did not implement any of the Main or View classes. 
MainView: MainView has always been the one large, looming class that our group has had to deal with. It should have been refactored into smaller, more specialized groups. For example, the initiation of the GUI should have been separate from the creation of the grids. Some parts, such as the following method to add cells to the root Group, are readable and easy to follow:
```java
	/**
	 * Adds cells to the scene
	 */
	public static void addCells() {
		for(int i=0;i<myCellGrid.length;i++) {
			for(int j=0;j<myCellGrid[i].length;j++) {
				group.getChildren().add(myCellGrid[i][j]);
			}
		}
	}
```
But others, such as the methods to initiate the simulation grids, are confusing if trees. These could have been refactored into a much better structure with the polymorphism already present in the grid types.
```java
	/**
	 * 
	 * @param name String containing name of the simulation
	 * Sets up a rectangle grid for a specific simulation
	 */
	public static void setupGrid(String name) {
		if(name.equals("Game Of Life")) {
			grid = new LifeGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleAllNeighbors();
		} else if(name.equals("Spreading Fire")) {
			grid = new FireGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleCardinalNeighbors();
		} else if(name.equals("Segregation")) {
			grid = new SegregationGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleAllNeighbors();
		} else if(name.equals("Predator")) {
			grid = new PredPreyGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleCardinalNeighbors();
		} else if(name.equals("SugarScape")) {
			grid = new SugarGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleCardinalNeighbors();
		} else if(name.equals("Bacteria")) {
			grid = new BacteriaGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleCardinalNeighbors();
		} else if(name.equals("Ants")) {
			grid = new AntGrid();
			setupCellGrid(GRID_SIZE);
			setupRectangleCardinalNeighbors();
		}
	}
```
As seen above, implementing a new simulation requires a lot of areas of code to be changed. This could have been avoided using abstract Grid classes to allow for any simulation type to be handled from MainView. This would also require neighbors to be set up from the grid class, which is probably desirable anyway. This large MainView class helped show me how important it is to create modular, encapsulated code, so that long messy if trees and duplicated code can be avoided.

DataHolder (specifically, LifeHolder): DataHolders have always seemed a bit weird to me. They work, but in a weirdly indirect way. LifeHolder (in its entirety) is shown below.
```java
	package XML;
	
	import javafx.scene.paint.Color;
	
	public class LifeHolder extends DataHolder {
		private static Color DEAD_COLOR;
		private static Color ALIVE_COLOR;
		private static double PERCENTDEAD;
		private static String LIFE_GRID;
		
		public static double getPercentDead() {
			return PERCENTDEAD;
		}
		
		public static String getLifeGrid() {
			return LIFE_GRID;
		}
		
		public static Color getDeadColor() {
			return DEAD_COLOR;
		}
		
		public static Color getAliveColor() {
			return ALIVE_COLOR;
		}
		
		public static void setGameOfLife(Color aliveColor, Color deadColor, double percent, String lifeGrid) {
			ALIVE_COLOR = aliveColor;
			DEAD_COLOR = deadColor;
			PERCENTDEAD = percent;
			LIFE_GRID = lifeGrid;
		}
		
	}
```
efsef


### Your Design

You can put blocks of code in here like this:
```java
    /**
     * Returns sum of all values in given list.
     */
    public int getTotal (Collection<Integer> data) {
        int total = 0;
        for (int d : data) {
            total += d;
        }
        return total;
    }
```

### Flexibilty

You can also just write some text

in separate paragraphs.


### Alternate Designs

Here is a graphical look at my design:

![This is cool, too bad you can't see it](online-shopping-uml-example.png "An alternate design")

made from a tool that generates UML from existing code like the
Eclipse plugin [ObjectAid](http://www.objectaid.com/).


### Conclusions

