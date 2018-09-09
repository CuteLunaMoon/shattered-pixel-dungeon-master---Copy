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

package com.shatteredpixel.shatteredpixeldungeon.items.food;


import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Roots;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

public class DeadRat extends Food {

	{
		image = ItemSpriteSheet.DEAD_RAT;
		energy = 10;
		canBeCook =true;
	}
	
	@Override
	public int price() {
		return 1 * quantity;
	}
	
		@Override
	public void execute(Hero hero, String action ) {
		
		super.execute( hero, action );
		
		if (action.equals( AC_EAT )) {
			effect(hero);
		}
	}
		public static void effect(Hero hero){
		switch (Random.Int( 5 )) {
			case 0:
			case 1:
				GLog.w( "You can't feel your legs! Why would you eat a dead rat?" );
				Buff.prolong( hero, Roots.class, Paralysis.DURATION );
				break;
			case 2:case 4:
				GLog.w("Your stomach aches! Why would you eat a dead rat?" );
				Buff.affect( hero, Poison.class ).set( hero.HT / 5 );
				break;
			case 3: case 5:
				GLog.w( "Your head is spinning and you feel nauseus! Why would you eat a dead rat?");
				Buff.prolong( hero, Slow.class, Slow.DURATION );
				break;
		}
	}
}
