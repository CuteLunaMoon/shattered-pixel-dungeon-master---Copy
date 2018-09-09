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
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Blob;
import com.shatteredpixel.shatteredpixeldungeon.actors.blobs.Fire;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
//import com.github.dachhack.sprout.items.weapon.enchantments.Leech;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.MissileWeapon;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SwordHuntsmanSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SwordHuntsman extends Mob {

	{
		spriteClass = SwordHuntsmanSprite.class;

		HP = HT = 69;
		defenseSkill = 27;

		EXP = 22;

		state = SLEEPING;

	
		
		loot = new Crossbow();
		lootChance = 0.05f;

	//	properties.add(Property.MINIBOSS);
	}
	private String[] deathCurse ={"Mercy, please...","This town is done for","Aaargh... you monster",
	"Beast"," God... help me...", "It's so cold", "Curse you, you monster","I hope they have your head before the morning"};
	private String[] attackCurse ={"Burn! Burn!","Sick creature","Away, away!","You killed my son! Die, you monster!",
	"You devious rat","Beast! A foul Beast!","Die, you filthy werewolf!","Argh!","Die in raging flames!","You plague-ridden rat!"};
	private int combo = 0;

	@Override
	public int attackSkill( Char target ) {
		return 16;
	}
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 21, 26 );
	}



	@Override
	public int attackProc( Char enemy, int damage ) {
		int k = Random.Int(0,10);
		if(k >7) {
			int a = Random.Int(attackCurse.length);
			sprite.showStatus(CharSprite.NEGATIVE, attackCurse[a]);
		}
		damage = super.attackProc( enemy, damage+combo );
		//The gnoll's attacks get more severe the more the player lets it hit them
		combo++;
	
					if (Dungeon.level.flamable[enemy.pos])
					GameScene.add(Blob.seed(enemy.pos, 4, Fire.class));
					if(enemy.buff(Burning.class) == null){
					Buff.affect(enemy, Burning.class).reignite( enemy );
				}

		
		if(combo>5){
			//reset combo
			combo =1;
		}
		return damage;
	}
	@Override
	public void die( Object cause ) {
		super.die(cause);
		if (cause != Chasm.class){
			int k = Random.Int(deathCurse.length);
			sprite.showStatus(CharSprite.NEGATIVE,deathCurse[k]);
		}
	}


	private static final String COMBO = "combo";

	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle(bundle);
		bundle.put(COMBO, combo);
	}

	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		combo = bundle.getInt( COMBO );
	}

}
