import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    
    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }
    
    public void climbOverFence() {
        if (fenceAhead()) {
            turnLeft();
            move();
            turnRight();
            move();
            move();
            turnRight();
            move();
            turnLeft();
        }
    }

    /**
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }
    
    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
    }
    
    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
            System.out.println("Moved: " + nrStepsTaken + "steps further");
        }
    }
    
    public void stepOneCellBackwards() {
        turn180();
            move();
            turn180();
    }
    
    public boolean grainAhead() {
        move();
        if (onGrain()) {
            stepOneCellBackwards();
            return true;
        } else {
            stepOneCellBackwards();
             return false;
        }
    }
    
    public void walkToWorldEdge(){
        while( ! borderAhead() ){
            move();
        }
    }
    
    public void worldEmptyNestsTopRow() {
        while (!borderAhead()) {
        if (onNest()) {
            if (!onEgg()) {
                layEgg();
            }
        }
        move();
    }
    }
    
    public void walkToWorldEdgeClimbingOverFencesNest() {
        while(!borderAhead()){

            if (fenceAhead()) {
            climbOverFence();
            } 
            
            if (onNest()) {
            layEgg();
            }
            move();
        }
    }
    
    public void walkAroundFencedArea() {
    while (!onEgg()) {

        turnRight();

        if (!fenceAhead()) {
            move();
        } else {
            turnLeft();

            if (!fenceAhead()) {
                move();
            } else {
                turnLeft();
            }
        }
    }
}

    
    public void goBackToStartOfRowAndFaceBack() {
        setDirection(WEST);
        walkToWorldEdge();
        setDirection(EAST);
    }
    
    public void walkToWorldEdgeClimbingOverFences() {
        while( ! borderAhead() ){

            if (fenceAhead()) {
            climbOverFence();
        } else {
             System.out.println("X cord: "+ getX() + " Y cord: " + getY());
            move();
        }
        }
    }
    
    public void pickUpGrainsAndPrintCoordinates() {
        while (!borderAhead()) {
            if (onGrain()) {
                pickUpGrain();
                System.out.println("Picked up grain on X: " + getX() + " Y: " + getY() );
            } else {
                move();
            }
        }
    }
    
    public void gotoEgg() {
       while (!onEgg()) {
           move();
           
           if (borderAhead()) { 
            break;
            }
               
       }
    }
    
    
    
    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdgePrintingCoordinates( ){
        while( ! borderAhead() ){
            System.out.println("X cord: "+ getX() + " Y cord: " + getY());
            move();
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
            if( onEgg() ){
             return false;
       }else{
            return true;
      }
    }
    
    public void turn180() {
        for (int i = 0; i < 2; i++) {
            turnRight();
        }
    }
}
