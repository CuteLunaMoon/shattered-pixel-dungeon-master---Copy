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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BeastSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Beast extends Mob {
{
		spriteClass = BeastSprite.class;

		HP = HT = 69*2+Dungeon.depth;
		defenseSkill = 15;

		EXP = 15;

		state = SLEEPING;

	
		
		loot = new PotionOfHealing();
		lootChance = 0.05f;

	//	properties.add(Property.MINIBOSS);
	}



	@Override
	public int attackProc( Char enemy, int damage ) {
		int k = Random.Int(0,10);

		damage = super.attackProc( enemy, damage );
		//The gnoll's attacks get more severe the more the player lets it hit them

		int effect = Random.Int(4);

		if (effect > 2) {

				Buff.affect( enemy, Bleeding.class).set((effect-2) );

		}
		// stun
			if(Random.Int(0,10)>7){
				Buff.prolong( enemy, Paralysis.class, Random.Float( 1, 2) );
				enemy.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
				GLog.n("The Beast knocks you down with its unholy strength");
			}
		return damage;
	}

private boolean enraged = false;
	
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		enraged = HP < HT / 4;
	}
	
	@Override
	public int damageRoll() {
		return enraged ?
			Random.NormalIntRange( 15, 45 +(Dungeon.depth/3)) :
			Random.NormalIntRange( 6, 26 +(Dungeon.depth/2));
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 20+(Dungeon.depth/3);
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 8);
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		super.damage( dmg, src );
		
		if (isAlive() && !enraged && HP < HT / 4) {
			enraged = true;
			spend( TICK );
			if (Dungeon.level.heroFOV[pos]) {
				sprite.showStatus( CharSprite.NEGATIVE, "Enraged!" );
			}
		}
	}
	
	{
		immunities.add( Terror.class );
	}
}
