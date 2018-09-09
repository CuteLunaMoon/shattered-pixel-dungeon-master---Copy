/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Yharnamite;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Random;

public class InhabitantRoom extends SpecialRoom {

//	private static final int NPIRANHAS	= 3;
		public int minWidth() { return 10; }
	

	public int minHeight() { return 10; }
	
	public void paint(Level level ) {
		
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1, Terrain.EMPTY );
	//	Painter.fill( level, this, 2, Terrain.EMPTY_SP );
		Painter.fill( level, this, 2, Terrain.WALL );
		Painter.fill( level, this, 3, Terrain.EMPTY_SP );

		int x =  left + width() / 2;
		int y = bottom - 2;
		if(Random.Int(0,4)>2) {

			y =bottom-2;
		}else {
			y =top+2;
		}

		
		
		int pos = x + y * level.width();

		
		//level.addItemToSpawn( new PotionOfInvisibility() );
		Yharnamite resident = new Yharnamite();
/*
		int k = Random.Int(1,3);

		if(k >= 2){
		resident.PlayIdle2();
		resident.doorType =2;
		}
	*/
	//	int k = entrance().x +entrance().y* level.width();
		int a = Random.IntRange(0,4);
		if(a>2) {

			Painter.set(level, pos, Terrain.DOOR_SP);
		}else {
			Painter.set(level, pos, Terrain.DOOR_SP2);
		}
		resident.pos = pos;
		resident.number = 2;
		resident.number = Dungeon.residentNumber;
		Dungeon.residentNumber++;
		level.mobs.add( resident );
	
	}

}
