import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;
    private int myNrOfStepsTaken;
    private int myScore;
    
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
            System.out.println("Bewogen: " + nrStepsTaken + "stappen verder");
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

public void check360ForNest(int oldDirection) {
        if (!onNest()) {
            if (nestAhead()) {
                move();
            } 
            turnRight();
            if (!nestAhead()) {
                turnLeft();
                turnLeft();
                if (nestAhead()) {
                    move();
                }
            }
        }
        
        setDirection(oldDirection);
        
    }

public void eggTrailToNest() {
        int oldDirection;
        
        
        while (!onNest()) {
            if(eggAhead()) {
                move();
                
            } else {
                turnLeft();
                if (eggAhead()) {
                    move();
                    
                } else {
                    turnRight();
                    turnRight();
                    if (eggAhead()) {
                        move();
                    }
                }
            }
            
            oldDirection = getDirection();
            
            check360ForNest(oldDirection);
            
            
            
        }
    }
    
    public void faceEast() {
        while (getDirection() != EAST) {
            turnRight();
            if (getDirection() == EAST) {
                break;
            }
        }
    }
    
    public boolean validCoordinates(int x, int y) {
        int maxX = getWorld().getWidth();
        int maxY = getWorld().getHeight();
        
        if (x >= maxX || x < 0) {
            showError("Invalid coordinates");
            return false;
        }
        
        if (y >= maxY || y < 0) {
        showError("Invalid coordinates");
            return false;
        }
        
        return true;
    }
    
    public void goBackToStartOfRowAndFaceBack(int x, int y) {
        goToLocation(0, y);
        setDirection(EAST);
    }
    
    public int countEggsInRow() {
    int y = getY();
        
    int eggCount = 0;
    
    goBackToStartOfRowAndFaceBack(0, y);
    
    if (onEgg()) {
        eggCount++;
    }
    
    while (!borderAhead()) {
        move();
        if (onEgg()) {
            eggCount++;
        }
    }
    
    goBackToStartOfRowAndFaceBack(0, y);
    
    return eggCount;
}

public int rowWithMostEggs() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int rowWithMostEggs = 0;
        int mostEggs = -1;
        
        for (int row = 0; row < getWorld().getHeight(); row++) {
            goToLocation(0, row);
            
            int eggsInRow = countEggsInRow();
            System.out.println("Rij " + row + ": " + eggsInRow + " eieren");
            
            if (eggsInRow > mostEggs) {
                mostEggs = eggsInRow;
                rowWithMostEggs = row;
            }
        }
        
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Rij met de meeste eieren: " + rowWithMostEggs);
        
        return rowWithMostEggs;
    }
    
    public double averageEggsPerRow() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int totalEggCount = 0;
        int numberOfRows = getWorld().getHeight();
        
        for (int row = 0; row < numberOfRows; row++) {
            goToLocation(0, row);
            totalEggCount += countEggsInRow();
        }
        
        double average = (double) totalEggCount / numberOfRows;
        
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Gemiddeld aantal eieren per rij: " + average);
        
        return average;
    }


public int countEggsInWorld() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int totalEggCount = 0;
        
        for (int row = 0; row < getWorld().getHeight(); row++) {
            goToLocation(0, row);
            
            int eggsInRow = countEggsInRow();
            totalEggCount += eggsInRow;
            
            System.out.println("Rij " + row + ": " + eggsInRow + " eieren");
            System.out.println("Totaal tot nu toe: " + totalEggCount);
        }
        
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Aantal eieren in de wereld: " + totalEggCount);
        
        return totalEggCount;
    }
    
public void layTrailOfEggs(int amount) {
    for (int i = 0; i < amount; i++) {
        layEgg();
        move();
    }
}
    
    public void goToLocation(int x, int y) {
        if (!validCoordinates(x, y)) {
            return;
        }
        
        while (getX() < x) {
            setDirection(EAST);
            move();
        }
        
        while (getX() > x) {
            setDirection(WEST);
            move();
        }
        
        while (getY() < y) {
            setDirection(SOUTH);
            move();
        }
        
        while (getY() > y) {
            setDirection(NORTH);
            move();
        }
    }
    
    public void findNestInMaze() {
    while (!onNest()) {

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
    
    public void fixIncorrectBit() {
        if (onEgg()) {
            pickUpEgg();
        } else {
            layEgg();
        }
    }
    
    public int getIncorrectRowNr() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int rowNumber = 0;
        int incorrectRowNumber = -1;
        
        while (rowNumber < getWorld().getHeight()) {
            goToLocation(0, rowNumber);
            
            if (countEggsInRow() % 2 == 1) {
                incorrectRowNumber = rowNumber;
            }
            
            rowNumber++;
        }
        
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Foute rij: " + incorrectRowNumber);
        
        return incorrectRowNumber;
    }
    
    public int countEggsInColumn(int columnNumber) {
        int rowNumber = 0;
        int eggCount = 0;
        
        while (rowNumber < getWorld().getHeight()) {
            goToLocation(columnNumber, rowNumber);
            
            if (onEgg()) {
                eggCount++;
            }
            
            rowNumber++;
        }
        
        return eggCount;
    }
    
    public int getIncorrectColumnNr() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int columnNumber = 0;
        int incorrectColumnNumber = -1;
        
        while (columnNumber < getWorld().getWidth()) {
            if (countEggsInColumn(columnNumber) % 2 == 1) {
                incorrectColumnNumber = columnNumber;
            }
            
            columnNumber++;
        }
        
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Foute kolom: " + incorrectColumnNumber);
        
        return incorrectColumnNumber;
    }
    
    public void fixParityBit() {
        int startX = getX();
        int startY = getY();
        int startDirection = getDirection();
        int rowNumber = getIncorrectRowNr();
        int columnNumber = getIncorrectColumnNr();
        
        if (rowNumber == -1 || columnNumber == -1) {
            System.out.println("Geen fout gevonden");
            goToLocation(startX, startY);
            setDirection(startDirection);
            return;
        }
        
        goToLocation(columnNumber, rowNumber);
        fixIncorrectBit();
        goToLocation(startX, startY);
        setDirection(startDirection);
        
        System.out.println("Fout gefixt op rij " + rowNumber + ", kolom " + columnNumber);
    }
    
    public void makeListOfSurpriseEggs() {
        int size = 10;

        List<SurpriseEgg> eggs = SurpriseEgg.generateListOfSurpriseEggs(size, getWorld());

        getAverageValueOfEggs(eggs);

        for (SurpriseEgg egg : eggs) {
            printCoordinateOfEggAndValue(egg);
        }
    
    
    }
    
    public void printCoordinateOfEggAndValue(Egg egg) {
    System.out.println("Egg is op x: " + egg.getX() + ", y: " + egg.getY());
    System.out.println("Waarde van DEZE ei: " + egg.getValue());
}
    
    
public void makeListOfSupriseEggsAndPrintCoordinates() {
    makeListOfSurpriseEggs();
}

public double getAverageValueOfEggs(List<SurpriseEgg> eggs) {
    int total = 0;

    for (SurpriseEgg egg : eggs) {
        total = total + egg.getValue();
    }

    double average = (double) total / eggs.size();

    System.out.println("Gemiddelde waarde van eieren: " + average);

    return average;
}

public void pickUpNearestEggInList() {
    resetRace();

    List<Egg> eggList = getWorld().getObjects(Egg.class);
    Egg nearestEgg = getNearestEggInList(eggList);

    if (nearestEgg != null) {
        walkToLocationForRace(nearestEgg.getX(), nearestEgg.getY());
    }
}

/**
 * Berekent de afstand tussen Dodo en een ei.
 */
public int distanceToEgg(Egg egg) {
    return distanceBetween(getX(), getY(), egg.getX(), egg.getY());
}

/**
 * Berekent de afstand tussen twee locaties.
 */
public int distanceBetween(int x1, int y1, int x2, int y2) {
    int distanceX = Math.abs(x2 - x1);
    int distanceY = Math.abs(y2 - y1);

    return distanceX + distanceY;
}

public Egg getNearestEggInList(List<Egg> eggList) {
    if (eggList.size() == 0) {
        return null;
    }

    Egg nearestEgg = eggList.get(0);
    int nearestDistance = distanceToEgg(nearestEgg);

    for (Egg egg : eggList) {
        int currentDistance = distanceToEgg(egg);
        System.out.println("Egg op x: " + egg.getX() + ", y: " + egg.getY()
            + ", afstand: " + currentDistance);

        if (currentDistance < nearestDistance) {
            nearestDistance = currentDistance;
            nearestEgg = egg;
        }
    }

    System.out.println("Dichtstbijzijnde ei op x: " + nearestEgg.getX()
        + ", y: " + nearestEgg.getY());

    return nearestEgg;
}

public void moveRandomly() {
    resetRace();

    while (myNrOfStepsTaken < Mauritius.MAXSTEPS) {
        setDirection(randomDirection());

        if (!borderAhead() && !fenceAhead()) {
            moveOneStepForRace();
        }
    }

    showCompliment("Score: " + myScore);
    Greenfoot.stop();
}

public int getNrOfStepsLeft() {
    return Mauritius.MAXSTEPS - myNrOfStepsTaken;
}
/**
 * Zet alle gegevens van de race terug naar de beginstand.
 */
public void resetRace() {
    myNrOfStepsTaken = 0;
    myScore = 0;
    updateRaceScoreboard();
    
    // Pak direct een ei op als Mimi erop staat
    pickUpEggForRace();
}

/**
 * Pakt een ei op en telt de waarde bij de score op.
 */
public void pickUpEggForRace() {
    if (onEgg()) {
        Egg egg = pickUpEgg();
        myScore = myScore + egg.getValue();
        myNrOfEggsHatched++;
        updateRaceScoreboard();
    }
}

/**
 * Zoekt het beste ei dat nog bereikbaar is.
 * Hierbij wordtdan gekeken naar de waarde van het ei
 * en hoeveel stappen nodig zijn om er te komen.
 */
public Egg getBestEggForRace(List<Egg> eggList) {
    Egg bestEgg = null;
    int bestScore = -1;
    int bestDistance = 0;

    for (Egg egg : eggList) {
        int distance = distanceToEgg(egg);
        
        // Alleen eieren bekijken die nog bereikbaar zijn
        if (distance <= getNrOfStepsLeft()) {
            
            // Hoe hoger de waarde en hoe kleiner de afstand,
            // hoe beter de score van het ei dus dan gaat hij voor die ei
            int eggScore = egg.getValue() * 1000 / (distance + 1);

            if (bestEgg == null || eggScore > bestScore || eggScore == bestScore && distance < bestDistance) {
                bestEgg = egg;
                bestScore = eggScore;
                bestDistance = distance;
            }
        }
    }

    return bestEgg;
}

/**
 * Laat Dodo zoveel mogelijk punten verzamelen
 * binnen het maximale aantal stappen.
 */
public void dodoRace() {
    resetRace();

    while (getNrOfStepsLeft() > 0 && getWorld().getObjects(Egg.class).size() > 0) {
        List<Egg> eggList = getWorld().getObjects(Egg.class);
        
        // Kies het beste ei op dit moment
        Egg nextEgg = getBestEggForRace(eggList);

        if (nextEgg == null) {
            break;
        }
        
        // Loop naar het gekozen ei
        walkToLocationForRace(nextEgg.getX(), nextEgg.getY());
    }

    updateRaceScoreboard();
    showCompliment("Score: " + myScore + ", stappen: " + myNrOfStepsTaken);
    Greenfoot.stop();
}


/**
 * Zet één stap tijdens de race.
 * Houdt het aantal stappen en de score bij.
 */
public boolean moveOneStepForRace() {
    
    // Stop als er geen stappen meer over zijn
    if (getNrOfStepsLeft() == 0 || borderAhead() || fenceAhead()) {
        return false;
    }

    move();
    myNrOfStepsTaken++;
    
    // Kijk of er een ei opgepakt kan worden
    pickUpEggForRace();
    updateRaceScoreboard();

    return true;
}

public void getScore(int score1, int score2) {
    Mauritius world = (Mauritius) getWorld();
    world.updateScore(score1, score2);
}

/**
 * Werkt het scorebord bij.
 * Laat zien hoeveel stappen en punten er zijn.
 */
public void updateRaceScoreboard() {
    getScore(getNrOfStepsLeft(), myScore);
}

/**
 * Loopt naar een bepaalde locatie.
 * Tijdens het lopen worden stappen en score bijgehouden.
 */
public boolean walkToLocationForRace(int x, int y) {
    if (!validCoordinates(x, y)) {
        return false;
    }

    while (getX() < x && getNrOfStepsLeft() > 0) {
        setDirection(EAST);
        if (!moveOneStepForRace()) {
            return false;
        }
    }

    while (getX() > x && getNrOfStepsLeft() > 0) {
        setDirection(WEST);
        if (!moveOneStepForRace()) {
            return false;
        }
    }

    while (getY() < y && getNrOfStepsLeft() > 0) {
        setDirection(SOUTH);
        if (!moveOneStepForRace()) {
            return false;
        }
    }

    while (getY() > y && getNrOfStepsLeft() > 0) {
        setDirection(NORTH);
        if (!moveOneStepForRace()) {
            return false;
        }
    }

    return getX() == x && getY() == y;
}
}
