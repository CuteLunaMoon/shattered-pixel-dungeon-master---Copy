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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BrownBatSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class BrownBat extends Mob {

	{
		spriteClass = BrownBatSprite.class;
		
		HP = HT = 6+Dungeon.depth;
		defenseSkill = 15;
		baseSpeed = 3f;
		
		EXP = 2;
		maxLvl = 5;
		
		flying = true;
		
		loot = null;
		lootChance = 0.3667f; 
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1+(Dungeon.depth/2), 4+(Dungeon.depth/2) );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 4;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(1, 4);
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		damage = super.attackProc( enemy, damage );
		if (Random.Int(10) == 0) {
			Buff.prolong(enemy, Blindness.class, Random.Int(3, 10));
			GLog.n("The brown bat scratches your eyes!");
			Dungeon.observe();
			state = FLEEING;
		}
		
		return damage;
	}
	
	@Override
	public void die(Object cause) {
		
		if (Random.Int(5) == 0) {
			  for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
				  if (Random.Int(2) == 0 && enemy!=null){mob.beckon(enemy.pos);}
			      }
			  GLog.n("The brown bat's shrieks alert nearby enemies!");
			}

		super.die(cause);

	}

}
