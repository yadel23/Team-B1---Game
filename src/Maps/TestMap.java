package Maps;

import Enemies.BugEnemy;
import Enemies.DinosaurEnemy;
import Engine.ImageLoader;
import EnhancedMapTiles.EndLevelBox;
import EnhancedMapTiles.HorizontalMovingPlatform;
import GameObject.Rectangle;
import Level.*;
import NPCs.Walrus;
import Tilesets.CommonTileset;
import Utils.Direction;
import Utils.Point;

import java.util.ArrayList;

// Represents a test map to be used in a level
public class TestMap extends Map {
	private BugEnemy SecondBug, ThirdBug;

    public TestMap() {
        super("test_map.txt", new CommonTileset(), new Point(1, 11));
    }

    @Override
    public ArrayList<Enemy> loadEnemies() {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), Direction.LEFT));
        enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
        return enemies;
    }
 
/*   
    //NEW CODE
    public ArrayList<Enemy> loadEnemies() {   
    	ArrayList<Enemy> enemies = new ArrayList<>();        
        //enemies.add(new BugEnemy(getPositionByTileIndex(15, 9), Direction.LEFT));  
        
    	SecondBug = new BugEnemy(new Point(675, 300), Direction.LEFT);     
    	enemies.add(SecondBug);    
    	System.out.println("this is the state of the bug " + SecondBug.getAirGroundState());
    	if(SecondBug.getAirGroundState() == true && SecondBug.getFacingDirection() == Direction.LEFT) {
    		SecondBug.setFacingDirection(Direction.RIGHT); 
    	} else if(SecondBug.getAirGroundState() == true && SecondBug.getFacingDirection() == Direction.RIGHT) {
    		SecondBug.setFacingDirection(Direction.LEFT);
    	}
//    	//System.out.println("this is the x position of the bug **** " + SecondBug.currentFrame.getX());         
//    	if(SecondBug.getX() > 592) {     
//    		SecondBug.setFacingDirection(Direction.RIGHT);         
//    	}else if(SecondBug.getX() < 786) {         
//    		SecondBug.setFacingDirection(Direction.LEFT);         
//    	}        
//    	ThirdBug = new BugEnemy(getPositionByTileIndex(10, 9), Direction.LEFT);     
     // enemies.add(ThirdBug);              
    	enemies.add(new DinosaurEnemy(getPositionByTileIndex(19, 1).addY(2), getPositionByTileIndex(22, 1).addY(2), Direction.RIGHT));
    return enemies;
}
*/
    @Override
    public ArrayList<EnhancedMapTile> loadEnhancedMapTiles() {
        ArrayList<EnhancedMapTile> enhancedMapTiles = new ArrayList<>();

        enhancedMapTiles.add(new HorizontalMovingPlatform(
                ImageLoader.load("GreenPlatform.png"),
                getPositionByTileIndex(24, 6),
                getPositionByTileIndex(27, 6),
                TileType.JUMP_THROUGH_PLATFORM,
                3,
                new Rectangle(0, 6,16,4),
                Direction.RIGHT
        ));

        enhancedMapTiles.add(new EndLevelBox(
                getPositionByTileIndex(32, 7)
        ));

        return enhancedMapTiles;
    }

    @Override
    public ArrayList<NPC> loadNPCs() {
        ArrayList<NPC> npcs = new ArrayList<>();

        npcs.add(new Walrus(getPositionByTileIndex(30, 10).subtract(new Point(0, 13)), this));

        return npcs;
    }
}