CompSci 308: Cell Society Analysis
===================

> Assignment: [Cell Society](http://www.cs.duke.edu/courses/compsci308/current/assign/02_cellsociety/)


Design Review
=======

### Overall Design

The overall design of the program is broken down into the following categories:
 * XML Reader and Parser - The XML reader takes a specified file for a specific simulation and reads through it, pulling out relevant strings. The parser then allocates these variables into DataHolders, which is a group of classes that "holds" the read data statically. Each simulation has its own holder, that is filled with data from its respective simulation file.
 * Main and MainView - Main is the main file for the program, that starts the application. MainView is a static class that governs the GUI and instantiates all the required resources for the simulations. MainView interacts with specific grid classes to instantiate those grids and set neighbors. It has no interactions with the XML readers or DataHolders. Other classes in the View package are ButtonView and ChartView, which set up the GUIs for the buttons and chart, respectively. Similarly, the buttons package contains each type of button used in the GUI, and their setup specifications. The actions from these buttons mostly pertain to simulation steps and speed, which means they mostly interact with MainView.
 * Grids - Both triangular and rectangular, grids instantiate cells to be used in the simulation, and trigger them to update their states. Grids use information passed from DataHolders to set the initial states of the cells, and create the 2D array.
 * Neighbors - The neighbors package is used to set the neighbors for each cell. The logic for this can get quite complex, so each new neighborhood setup is a different class. They are used from MainView on cell grids to set the neighborCells array in each cell. They basically just contain the neighborhood logic, however.
 * Cells - These classes house the state logic for each simulation. The neighborStates array of each cell is updated before their own states, so that the simulation is effectively synchronous. Simulation specific cells get their simulation variables from their respective DataHolders. Other than that, the only contact they have to other classes is through grid, which tells them what their initial state is, and when to update. They also have event handlers for when a mouse is clicked or dragged over them, which will change their states. 
 * Agents - Agents are "helper" classes for certain simulation cell classes, such as foraging ants and sugar scape. These agents are separate entities, denoted as colored circle nodes, that can move from cell to cell. They can interact with their home cells and with each other, and have update state methods of their own. "Mover" classes are used to track these agents, and update them before the cells update. Their internal logic is dependent on the specific simulation. They also interact with DataHolder to get variables from the simulation XML documents. 

To add a new simulation, you must add a new Cell class, extending Cell and implementing IGrid. This cell must have an update state method that updates its state based on its neighborStates array. Agents can be used if needed. A new XML document and corresponding DataHolder must be created and filled from the XML Parser, and a grid (with the correct type of neighborhood) must be created and initialized from MainView.

Overall, dependencies are fairly clear, and only exist through public classes. There are some back channels between agents and MainView, as agents will get the root Group from MainView and add/remove themselves from it. Other than that, the dependencies are clear.

#### Specific Examples
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

As seen above, DataHolders use private static variables to "hold" pieces of data, which are set using the XML parser and read with getters. This whole class kind of seems like a way to use global public state variables indirectly. Other than that, the class is very readable, as it is fairly simple. It is encapsulated in the sense that only one holder is necessary for each simulation, but otherwise its implementation is quite rigid and inflexible. I have learned that it is probably a better design choice to pass down simulation variables in a better way, to keep those values more protected, as well as making the implementation of a new simulation much simpler. These holders have so many dependencies that it can be a nightmare to find and add all of them when making a new sim.

Overall, this team did a good job of staying consistent with naming conventions. For example, the grid classes and cell classes for the basic implementation were written by different people, but their terminology was conveniently similar. This is the updateStates method in Grid:
```java
	/**
	 * 
	 * @param grid 2D Array of Cells
	 * Updates each cell
	 */
	public void updateStates(Cell[][] grid) {
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				grid[i][j].updateNeighborStates();
			}
		}
		for(int i=0;i<grid.length;i++) {
			for(int j=0;j<grid[i].length;j++) {
				grid[i][j].updateState();
			}
		}
	}
```
And this is the updateState method from LifeCell:

```java
	public void updateState() {
		int numAlive = sumArray(this.getNeighborStates());
		if(this.getState() == ALIVE) {
			if(numAlive < MIN_ALIVE || numAlive > MAX_ALIVE) {
				this.setState(DEAD);
			}
		} else {
			if(numAlive == MAX_ALIVE) {
				this.setState(ALIVE);
			}
		}
		updateFill();
	}
```
By keeping the names different but similar, and using relevant variable names, it is very clear what is going on in this code, as well as which methods belong to which classes. The Grid class updates multiple states, whereas the Cell class updates only one state (itself). 

### Your Design

I personally wrote all the Cell classes, as well as the secondary Grid classes for the later simulations. I also wrote the Agent classes to use with the Cell classes. My design uses an abstract Cell class that would work with a Grid class, irregardless of the specific simulation it was running. My idea was to only have one grid class that would use an interface to interact with the cells, and only the specific type of cell would determine the simulation, but that idea was scrapped. Overall, the cells interact with each other through neighbor arrays, which are set by grid. They can get their neighbor states in one step, and update their own state in another. Cells can also interact with Agents, which are generally independent entities. Certain simulations will have different interactions, so public methods vary, but in general, the Agents only care about the state of the Cell. The Agents are able to alter the Cell states, such as in SugarScape, when an Agent takes a Cell's sugar. The state and color of a Cell are otherwise determined internally.

#### Specific Examples
One implementation that I am proud of is SugarScape. I created and used Agents in this simulation, and have a separate governing class for the Agents which updates them when needed. Other than the SugarGrid class, the Agents are invisible to the rest of the program, so that avoids many negative dependencies. Implementing vision with the Agents was a challenging part of the design of this class, but I feel that I got around it in a creative way, by writing my own list comparators for the neighbor cells that an Agent could move to.

I am not fond of my implementation of the Segregation simulation. The challenging part of this simulation is the fact that a cell can potentially move to any free cell on the board if it is unhappy with its current location. I made a CellMover class to do this, but this unfortunately caused many other dependencies with the grid classes that had to be taken care of. Additionally, during the development of the basic implementation, the extra cutoff value variable caused many problems higher up in the program, within MainView and XML reader. These dependencies were eventually removed and hidden within the SegregationGrid class, however. 

### Flexibilty

Our design was quite flexible when it came to different board types. Cell already extended Polygon, so other shapes like triangles were easily implemented. This program is not very flexible with new simulations, however. Initially, the design was to have one Grid class that could initialize any Cell type, which would have been  very flexible and desirable. However, now the program is very rigid with adding new simulations, as a new holder class, grid class, cell class, and potentially agent class must be added, as well as implementations in XML parser, MainView, and XML reader. I think this lack of flexibility is partially due to miscommunication within our group during the design phase, which is something I will be looking to improve.

#### Specific Examples
One example of some code I did not implement is MainView, as discussed above. I find this code interesting, because it is such a big class that has a very large reach in the program. This class references every grid class for every simulation in the program, severly limiting its flexibility. It defines the files for each simulation, initializes the grid based on the type of simulation, sets up the GUI, buttons, drop-down menu, and handles all of their actions. MainView handles a simulation switch, simulation speed up or down, and single steps. MainView even tells the grids when to update the cells. In my opinion, having one large class to do all this was a bad choice, as adding new types of buttons (and their effects), GUI elements, and simulations will all have to go through MainView. This class is very inflexible, making it difficult to implement new related features.

Another example of a feature I did not implement is the XML reader class. I find this code interesting because although it is not flexible in some ways, in regards to its many private variables for each simulation, it is very flexible in others. Other than this class's initialization in Main, this class only interacts with DataHolders. I mentioned above that I am iffy about these holders, but they do make it easy to separate the reader from the grid, agent, and cell classes that depend on it. It is flexible in the sense that those cell and grid classes can use the holders however they like, and the reader is able to stay separate. The reader is slightly inflexible when adding a new simulation, as new rules for that simulation's XML file must be created, but otherwise this feature is fairly solid.


### Alternate Designs

#### Extensions
We were able to extend our project to implement the configuration and visualization extensions fairly easily, despite the inflexibility of MainView. We already had cells that the user could interact with, grid methods to find the number of a certain type of cell, and XML methods that could initialize the cells in a variety of ways. Extending the project with the simulation extensions proved to be a good deal harder, due to our poor design with multiple cell, grid, and data holder types, as well as references to those specific simulation types in many other classes (such as MainView). We ended up just adding these extra classes and lines of code, which was tedious, but it worked. The next step for this project would be to refactor this area of the project so that only the cell and agent classes would be simulation specific, along with the data holder class. 

#### Alternate Design Ideas 
The first real design decision made was to have separate Cell classes for each simulation, that would internally determine the logic behind switching states. Alternatives to this design included having the simulation's grid make those state decisions, or having separate classes from the cells that calculated that cell's new state. 
Having the grid determine state would have allowed the grid to consider the entire board, rather than just the cell's neighbors, but this came at the cost of complexity, and we determined this was unnecessary for the simulations we were implementing.
Separate classes from the cells would have allowed us to have one grid class and one cell class, that could easily be used by higher program classes. This would make the design simpler, as the cell would basically only carry a state and a color. However, this simulation class (depending on the simulation) would have dependencies on many other classes, and it seemed most logical to simply combine the dumb cell class with these simulation classes. This would also allow cells to control their own color.
Overall, I would prefer the simulation specific cell classes that we used in this project. They are very self-contained, and only need to be told when to update. 

Another design decision that was made was to have simulation-specific grid classes to accompany each unique cell class, which was a decision I disagreed with. An alternative to this was to have one grid class, that used an interface to initialize the cells and tell them when to update.
Having one grid class makes the design much more modular and flexible. It would make implementing new simulations much easier, as the cell would simply have to implement the grid interface to be compatible with that grid. The drawbacks to this system is that it is significantly harder to have different types of neighborhoods, although this problem could have been fixed by a getNeighborHoodType method in the cell class, which would tell the grid what type of neighborhood it requires.
Having multiple classes makes the interactions with specific cells much easier, as that grid class can be tailored to its respective simulation. However, it makes the implementation of MainView much more complex, as MainView has to accomodate many different grid classes.
Ultimately, I would have preferred to have one grid class, as it would have made the project design much more flexible, and adding new simulations would have been easy. MainView would not have been nearly as complicated, and a lot of duplicated code would have been eliminated.


### Conclusions

In my opinion, the best part of this project's design is the implementation of different cell classes for each simulation. This makes the code much more modular, and would theoretically make adding new simulations much easier. Writing this portion of the code also taught me how to use abstract classes and interfaces, which is something I needed to improve on from my first project.

In my opinion, the worst feature of this project is MainView. It is a massive, inflexible class that is highly dependent on many other classes and features. This class showed me how important polymorphism can be to avoid the large if trees present in this class. Additionally, it shows me the importance of modularity, as this class should be split into multiple smaller ones, with more specialized purposes. 

To be a better designer for the next project, I am going to start meeting with my team more. Messaging over Facebook is alright, but meeting in person will ensure that everyone is on the same page about the design of the project before that project spirals out of control. I am also going to start looking at my code from everyone else's perspective, and do my best to make their implementation of my classes as painless as possible.
I am going to keep creating interfaces for my classes (that hopefully will be used), that limit the user's interaction with my classes, reducing dependency, and hopefully making their implementation easier. I will keep my classes relatively short and specialized to keep them modular.
I am going to stop using backchannels to alter data, such as between agents and MainView's root. Dependencies should be limited, but also very obvious, so that if an exception or error does result from one, it can be easily traced.

