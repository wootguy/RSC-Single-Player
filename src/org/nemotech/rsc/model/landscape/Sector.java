package org.nemotech.rsc.model.landscape;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Sector {
    
    public static final int WIDTH = 48, HEIGHT = 48;
    
    private Tile[] tiles;
    
    public Sector() {
        tiles = new Tile[WIDTH * HEIGHT];
        for(int i = 0; i < tiles.length; i++) {
            tiles[i] = new Tile();
        }
    }
    
    public static int numTile = 0;
    
    public static Sector unpack(ByteBuffer in, int sectorX, int sectorY, int layer) throws IOException {
        int length = WIDTH * HEIGHT;
        if(in.remaining() < (10 * length)) {
            throw new IOException("Provided buffer too short");
        }
        Sector sector = new Sector();
        for (int i = 0; i < length; i++) {
            sector.setTile(Tile.unpack(in), i);
            
            if (layer == 0 && sectorY >= 45 && sectorY <= 52 && sectorX >= 49 && sectorX <= 57) {
            	final int xOffset = -18;
            	final int yOffset = 18;
            	
            	// actual height = 616 -> 620
            	// actual width = 166 -> 170
            	
            	int elevation = sector.getTile(i).groundElevation;
            	int x = (sectorX-48)*WIDTH + (i % WIDTH) + xOffset;
            	int y = (sectorY-37)*HEIGHT + (i / WIDTH) + yOffset;
            	if (sectorX == 51 && sectorY == 49 && elevation == 0)
            		System.out.println("LOADING TILE " + numTile++ + " (" + x + ", " + y + ") = " + elevation);
            }
        }
        return sector;
    }
    
    public Tile getTile(int i) {
        return tiles[i];
    }
    
    public Tile getTile(int x, int y) {
        return getTile(x * Sector.WIDTH + y);
    }
    
    public void setTile(Tile t, int i) {
        tiles[i] = t;
    }
    
}