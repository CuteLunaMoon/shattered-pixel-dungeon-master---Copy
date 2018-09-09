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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.GuardCaptain;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Dagger;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GoblinSprite;
import com.watabou.utils.Random;

public class Goblin extends Mob {
	
	{
		spriteClass = GoblinSprite.class;
		HP = HT =12;
		//HP = HT = 12+(Dungeon.depth*Random.NormalIntRange(1, 3));
		defenseSkill = 4;
		
		EXP = 2;
		maxLvl = 8;
		
		loot = Dagger.class;
		lootChance = 0.33f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 6 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}

	@Override
	public int attackProc( Char enemy, int damage ) {
		sprite.showStatus(CharSprite.NEGATIVE," Die, human scum");
		if(Dungeon.hero.className() == "Huntress" ||Dungeon.hero.className() == "Warden"||Dungeon.hero.className() == "Sniper"){
			sprite.showStatus(CharSprite.NEGATIVE," Don't run. I'll make you the next brood mother");
		}
		
		damage = super.attackProc( enemy, damage );
		int effect = Random.Int(4)+5;

		if (effect > 2) {

				Buff.affect( enemy, Poison.class).set((effect-2) );
		}

		return damage;
	}
	@Override
	public void die( Object cause ) {
	super.die(cause);
		if (cause != Chasm.class){
		sprite.showStatus(CharSprite.NEGATIVE," #%!&*3:<");
		}
	}
		@Override
	public void rollToDropLoot() {
		GuardCaptain.Quest.process( this );
		
		super.rollToDropLoot();
	} 	
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}
}