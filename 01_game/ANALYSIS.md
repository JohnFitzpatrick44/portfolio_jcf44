CompSci 308: Game Analysis
===================

> This is the link to the assignment: [Game](http://www.cs.duke.edu/courses/compsci308/current/assign/01_game/)

Project Journal
=======

### Time Review

The amount of time I spent on this project is hard to calculate. I should have been more diligent with recording how long I spent working on it. I spent at least an hour or two working on it every weekday since the plan was due, plus a couple of days where I spent an additional 5 hours on it. I started this project when the plan was submitted, on 1/14/18. I finished the submitted version on 1/21/18, and finished the final (refactored) version on 1/25/18. Including the plan, I probably spent around 25-30 hours on it in total.

My general design timeline went like this (bullets do not correspond to commits, but general design checkpoints. My commits were inconsistent, I'll touch on that later):
1. Plan: spent about 5 hours thinking and writing this. I tried to get the ball mechanics down early, as that was the hardest thing in my opinion.
2. Basic physics: Using the lab_bounce template, I made a ball class that bounced off walls, and ended the game when it hit the bottom border. 
3. Paddle and Bricks: I made a paddle and bricks from rectangles that the ball would bounce off. Paddle was not its own class until much later. Initially, all bricks had durability 1 and random colors, which looked quite funny. 
4. Aesthetics: I started to develop a color palette for the game, added rectangle curvature, and introduced durability. Now, bricks would change colors as they lost durability. I also changed the ball from an ImageView to a Circle for better hit detection and a better look.
5. Minor refactoring: I started splitting large, rambling methods into shorter, more descriptive ones. I also added a UI rectangle on the side that didn't really do anything.
6. Ugly placeholders: I added a splash screen and UI information, score and level labels (despite their only being 1 level), and hearts. Hearts are just ImageViews with a bit image of a heart.
7. Miscellaneous additions: Added Life class, heart functionality, and game over condition. Improved score count. Added permanent blocks. Added the paddle jump ability.
8. Level files: A very large addition, I added a file reader that took an input file and read the level from it. It was a big addition, but I haven't had any real bugs with it, apart from level file format mistakes.
9. Added powers: Made a power class with a type, set at random when spawned. Made them fall when their 'owner' brick breaks. Adding effects was easy, as I had included their methods already. Made paddle a separate class to make effects and hit detection easier.
10. Cheat codes: Added all the cheat codes detailed in the plan. Most were just triggering power effects, but some (like jumping levels) required more work. Decided to reset the bufferedReader whenever I jumped to a previous level rather than storing each level in state variables.
11. Game over: Added game over UI, final score, and exit/retry buttons. Retry just used the jumpLevel method. 
12. Added effect: Adding brick movement was simpler than expected. I just added a speed component to the brick class, set it equal to the ball x speed at an intersection, and updated brick position. The hardest part was brick collision detection, but otherwise it went smoothly.
13. After this point, before submission, I only worked on UI text and image placement, making it look nicer.
14. After submission, I majorly refactored the one Breakout.java file into 8 files, including a Main.java and two extra classes, powerUp and powerDown. Also added all the documentation I forgot to do in the main project. 

Most of my time was spent designing, coding, and debugging. Testing was easy and fun to do, as it involved playing the game. Documenting I saved almost completely for the analysis. 

I was very inconsistent with committing. My commits varied quite a bit in size, and I felt that I either pushed too often or not enough. My commits also did not occur at large checkpoints. Rather, I'd simply commit when I was done coding for a while, which usually did not correspond to checkpoints. This is definitely something I could improve upon. 

Overall, I felt that my time was spent very efficiently, with the exception of having to majorly refactor into multiple files at the end. I tried to always look ahead when designing code, and it paid off when I needed to add new features like brick types and power effects. The major timesinks/inefficiencies mostly stemmed from writing everything into one large file, which motivated me to refactor that specific aspect.

### Commits

A general overview of the number/size of commits is shown below.
1. 1/14/18 15:05 - 20 files changed, 350 insertions.
2. 1/14/18 17:11 - 1 file changed, 5 insertions, 3 deletions.
3. 1/15/18 01:58 - 3 files changed, 105 insertions, 23 deletions.
4. 1/15/18 02:02 - 1 file changed, 21 insertions, 20 deletions.
5. 1/15/18 17:39 - 3 files changed, 114 insertions, 43 deletions.
6. 1/15/18 21:10 - 2 files changed, 106 insertions, 20 deletions.
7. 1/15/18 22:01 - 2 files changed, 50 insertions, 9 deletions.
8. 1/15/18 22:44 - 2 files changed, 63 insertions, 59 deletions.
9. 1/16/18 01:30 - 2 files changed, 23 insertions, 14 deletions.
10. 1/16/18 10:53 - 2 files changed, 15 insertions, 4 deletions.
11. 1/19/18 14:45 - 4 files changed, 202 insertions, 95 deletions.
12. 1/21/18 20:23 - 4 files changed, 299 insertions, 111 deletions.
13. 1/21/18 22:04 - 2 files changed, 47 insertions, 31 deletions.
14. 1/22/18 00:06 - 1 files changed, 147 insertions, 89 deletions.
15. 1/22/18 00:55 - 2 files changed, 99 insertions, 112 deletions.
16. 1/22/18 01:45 - 2 files changed, 88 insertions, 95 deletions.
17. 1/25/18 03:24 - 8 files changed, 619 insertions, 323 deletions.
18. The rest are edits on the ReadMe.md and Design.md.

The average commit size before submission is 145 changes. Including the refactoring, the average is 189 changes. I do not feel like these commits were necessarily appropriate, as they do not represent specific added features, but rather times I saved the code. This is something I'll work on improving on. 

One commit I think was effective was the 7th on the list above, commit [f924337a](https://coursework.cs.duke.edu/CompSci308_2018Spring/game_jcf44/commit/f924337a35b3c6daff66e17116a961810571e9e5). The message was "Added level reader (for one level)." The message may be short, but it is concise, and accurately describes what was added. The purpose was to add the level reader, and a level file which it could read from. The reader was limited to one level, but still functioned with different brick types and durabilities. The feature was well encapsulated in its own method, so I felt good about packaging it as a commit. The commit's size, with 50 insertions and 9 deletions, is one of my more reasonably sized commits, which could be somewhat easily reviewed.

A much less effective commit, 11th on the list above, was commit [3ef0add1](https://coursework.cs.duke.edu/CompSci308_2018Spring/game_jcf44/commit/3ef0add18f745636ebd127090cfff4af888c51fc). The message was "Added basic permanent blocks. Game ends when only they are left. Sorted out bug where stuck ball moves when paddle hits edge of screen." Although that message seems specific, it does not encapsulate all that was done. This commit was done after a couple days break, and includes a lot of random bug fixes and features. Permanent blocks were added, as well as the splash text, refactoring of update methods, the exit and retry buttons, and many multiple bug fixes. I clearly committed these changes because I had forgot to for a while, and needed to lump them all together. The commit's size and scope are too big and complicated for a reviewer, so I definitely need to work on being more concise and efficient with my commits.

### Conclusions

 * I think I estimated the size of this project fairly well. I didn't run out of time, but still didn't spend too much of my day working on it.
 * The most editing I had to do was refactoring into separate files. By far, this was the largest amount of time I spent editing. I will definitely break up the classes to begin with next time.
 * To be a better designer, I'm going to think through what classes I will need to begin with, and make them in separate files. Additionally, I will code (and commit) for ease of expansion and review.
 * In addition to the refactoring work I did for the analysis, I would make the level reader more flexible in its requirements. That seems like an important next step for me, and would greatly benefit the user. Also, adding some sort of level editor would also improve user experience.


Design Review
=======

### Status

#### Layout and Style ####

From a design standpoint, my code seems to be in a limbo between completely refactored, and not refactored whatsoever. I split and pulled out many classes from the main file, but there is still a class in the main Breakout file, and there are still multiple lengthy methods that should probably be split. Other than that, however, I feel that my code is very consistent with naming conventions, style, and descriptiveness. My variable names and constants are usually very accurate and precise. There are some instance variables that aren't as descriptive as they should be, such as:
```java
for(Brick b : bricks) {
...
}
```
In this case, I just got lazy while writing the loop, and kept it in. Normally, this wouldn't be too much of a problem, but since I used 'b' in for loops for both balls and bricks, it may be a bit confusing to a reviewer.

#### Readability ####

Overall, the way I split up and named my methods seems readable to me. Of course, I am a bit biased, since I wrote it. The step method is a good example of my code at its most readable:
```java
public void step (double elapsedTime) {
    	if(gameDone) return;
    	
    	updatePaddle(elapsedTime);
    	updateBalls(elapsedTime);
    	if(movable) updateBricks(elapsedTime);
    	updatePowers(elapsedTime);
    	
    	score.setText("Score\n" + scoreValue);
    	level.setText("Level\n" + currentLevel);
}
```
In this example, it is clear what is happening, and when: the program checks that the game isn't over, updates the paddle, balls, bricks (if special mode is on), and powers, and updates the text and score labels. Short, concise methods that link to other, well named methods are what I should aim for. This is the method that constantly updates each new frame, so I was sure to make it very readable. It is clear where it fits in the code, what its purpose is, and what it does.

There are places in my code where readability could definitely be improved, however. For example, one of the above methods, updatePaddle(elapsedTime), is a pain to read. The names aren't confusing, but the many conditional statements are, and there's plenty of duplicated code that should have been refactored.
```java
 private void updatePaddle(double elapsedTime) {
    if(paddle.getXSpeed() > 0 && !rightKeyHeld) {	// rightKeyHeld and leftKeyHeld included to smooth paddle movement
    	paddle.setXSpeed(paddle.getXSpeed() - PADDLE_DRAG * elapsedTime);	// Equation to decelerate paddle, stop at 0
    	paddle.setXSpeed(Math.max(0, paddle.getXSpeed()));
    } else if(paddle.getXSpeed() < 0 && !leftKeyHeld) {	// Otherwise, the paddle would jerkily move between 2 speeds
    	paddle.setXSpeed(paddle.getXSpeed() + PADDLE_DRAG * elapsedTime);
    	paddle.setXSpeed(Math.min(0, paddle.getXSpeed()));
    }
    
	if((paddle.getX() > XSIZE - paddle.getWidth())) {	// Keeps paddle in bounds
		paddle.setX(XSIZE - paddle.getWidth());
	} else if(paddle.getX() < 0) {
		paddle.setX(0);
	} else {
		paddle.setX(paddle.getX() + elapsedTime*paddle.getXSpeed());
	}
	if(paddle.getYSpeed() != 0 || paddle.getY() < YSIZE - 2*paddle.getHeight()) {	// Handles jumping
		paddle.setYSpeed(paddle.getYSpeed() + PADDLE_ACCELERATION*elapsedTime);
		if(paddle.getY() > YSIZE - 2*paddle.getHeight())  {		// If paddle is below its starting position, reset it vertically
			paddle.setYSpeed(0);
			paddle.setY(YSIZE - 2*paddle.getHeight());
		} else paddle.setY(paddle.getY() + elapsedTime*paddle.getYSpeed());
	}
}
```
The purpose of this code is to update the paddle position. This got complicated, as it needs to check when the paddle is at a window boundary. Additionally, when I added the paddle's jump feature, I tacked another conditional statement on the end, further confusing it. It's quite a feat for a newcomer to this program to understand this code quickly. It's not only full of duplicated code, but the conditional statements are long and hard to read. This would definitely be one of the next things to refactor if I had the time. The whole paddle class and its functionality is a bit convoluted, so I would spend some time sorting that out, and putting methods relating to the paddle in the paddle class.

#### Dependencies ####

There are quite a few "back-channel" dependencies going on in my code. When the whole program was one file, I relied heavily on global variables, making the dependencies hard to find. When I split the classes into separate files, I used get methods more often than not to alter those variables, once again creating hidden dependencies. This is an aspect of my code that I didn't even think to check before I read the ANALYSIS.md description, but now that I am aware of the problems hidden dependencies cause, I will start to look out for them.

### Design

Initially, my code was all in one file, which was a poor design choice. After the refactoring, I was left with individual files for Main, Breakout, Ball, Brick, Power, PowerUp, PowerDown, DropPower, and IPower. The power files are a result of my experimentation with inheritance, which is relatively new to me. 
Generally, Breakout instantiates the other classes as objects in the JavaFX scene. Other than Main and Breakout, each class extends a JavaFX node, corresponding to their general shapes. Paddle and Brick extend Rectangle, the Power classes extend ImageView, and Ball extends Circle. This allowed for the easy addition and removal of these objects from root. Each object's position is updated based on their current positions, elapsed time since the last update, and internal speed values. These internal values vary from class to class, depending on their interactions with other objects. Each class has an x and y speed, and the Ball class has additional velocity and angle values, to more easily calculate angled bounces off the paddle. 
The splash screen, UI, and game over screens are all instantiated and added to root in various Breakout methods. If I had more time, I would likely make these their own classes, to be added and removed at once. 
The level reader reads through the level file one level at a time, until it either hits the end of the file, or is told (by jumpLevel) to reload the file. I thought I did this quite well, by assigning type and durability values to various "brick codes." As mentioned before, if I had more time, I would like to make this method more flexible, and probably put it into its own class.
New levels can be added to the game simply by adding brick codes to the level file, the name of which can be changed in the Breakout constant list. The details of the file formatting can be found in the README.md.
Currently, my code is very flexible in regards to powers, power effects, types of bricks, and balls. If you wanted to change the amount of lives, UI, or position calculations, however, you would run into some trouble. The UI sizings are sort of hard coded in, and would be hard to change. Although the XSIZE and YSIZE constants could be changed, the text size is also hard coded, and would remain the same. These sort of flaws in my design are very aesthetic, but could cause a lot of problems if the game were to be expanded in the future.

#### Specific Features ####

One feature I added was the movable bricks, which slide across the screen when hit by a ball. Other than the overseeing Breakout class, only the Brick and Ball classes are really needed to implement this, as well as a button to toggle the ability. This feature is mostly contained in the Brick class. The Brick's x speed is set to the intersecting Ball's speed, and if the Brick is movable, it'll slide, potentially hitting and moving other Bricks. All that is needed is a speed state variable in the Brick class, and an updateBricks method, which updates their positions. This feature does not significantly reduce flexibility, as any type of Brick can be made movable or immovable (like permanent Bricks) fairly easily. There are some flaws with this feature, like Bricks moving when the button is first toggled, and the updateBricks method being in the Breakout class, but overall the implementation was fairly simple.

Another feature added was the random set of power ups and downs that could be triggered. The design I used initially was fairly solid, with the Power class encapsulating both the positive and negative powers, and setting their image based on their type. This class also handled the triggered effects with conditional statements on type. 
After refactoring this feature, the design is much easier to understand and implement. Now, there is a basic Power class and a Power interface that all powers must implement. Then, I made a DropPower class, as the powers I am concerned with simply drop straight down. Then, I made different classes for positive and negative effects, and have their effects be determined randomly when triggered. This design allows for much greater flexibility when adding powers, and allows for much more creative effects and power movements to be implemented.

### Alternate Designs

As mentioned before, and shown by my refactoring, I am now wishing I had used the multiple file alternative to one large class file. 

One design alternative I considered was to use ImageView objects for everything, including the bricks and paddle. There are many pros to this, as using your own image is much more versatile, and can look a lot better than other JavaFX nodes. However, these objects can be finicky with their intersection boundaries, and I found that JavaFX shapes were much more precise with intersection calculations, so ultimately I went with rectangles and circles. For the powers, however, I kept ImageViews, because accurate hit detection with those slower moving objects was less of a concern.

Another design alternative I considered was to make the window resizable, and have the UI and assets re-size accordingly. I initially put quite a bit of thought towards this idea, but ultimately abandoned it when I discovered how difficult it would be to implement, especially with text. Difficulty aside, this alternative design would better the user's experience, and prevent accidental resizing from breaking the game. Ultimately, I would prefer the dynamic window sizing, but did not give it enough priority to work on it for too long.

### Existing Bugs

The main bug that still exists is not necessarily a bug, but the format restriction for the level file. Even a small mistake will throw exceptions, so it is very important that you get the format correct. I should have implemented better error checking, but did not give it enough time.

Secondly, when the 'P' key and jump level number keys are mashed in quick succession, bricks will appear over the game over UI. This is unfortunate, and I am unable to find the source of this bug, but luckily it is somewhat hard to reproduce.

Finally, the bricks store speed information even when they are not supposed to move. This will not be noticeable unless you've bounced a ball at an angle off a block with more than 1 durability, and then turned on the movable block mode. This could be fixed, but requires a good amount of refactoring that I don't necessarily have time for.

