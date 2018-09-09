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
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
//import com.github.dachhack.sprout.items.weapon.enchantments.Leech;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.KindOfWeapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Crossbow;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Gauntlet;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Knuckles;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.levels.features.Chasm;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShieldHuntsmanSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ShieldHuntsman extends Mob {

	{
		spriteClass = ShieldHuntsmanSprite.class;

		HP = HT = 69;
		defenseSkill = 35;

		EXP = 35;

		state = SLEEPING;

	
		
		loot = new PotionOfHealing();
		lootChance = 0.15f;

	//	properties.add(Property.MINIBOSS);
	}
	private String[] deathCurse ={"Mercy, please...","This town is done for","Aaargh... you monster",
	"Beast"," Curse!", "Loran is done for", "Curse you, you monster","Aaargh..."};
	private String[] attackCurse ={"Stay away from me!","Foul beast!","Away, away!","You killed my brother! Die, you monster!",
	"Fiendish creature!","Beast! A foul Beast!","Filty beast!","Curse!","You plague-ridden rat!"};
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
	
		//disarm
					if (enemy == Dungeon.hero) {
			int hitsToDisarm =Random.Int(0,10);
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;
			
			if (weapon != null
					&& !(weapon instanceof Knuckles)
					&& !(weapon instanceof Gauntlet)
					&& !weapon.cursed) {


				if (hitsToDisarm > 8) {
					hero.belongings.weapon = null;
					Dungeon.quickslot.convertToPlaceholder(weapon);
					weapon.updateQuickslot();
					Dungeon.level.drop(weapon, hero.pos).sprite.drop();
					GLog.w(" The huntsman knocks your weapon from your arm");
				}
			}
		}
			// stun
			if(Random.Int(0,10)>7){
				Buff.prolong( enemy, Paralysis.class, Random.Float( 1, 2) );
				enemy.sprite.emitter().burst(Speck.factory(Speck.LIGHT), 12 );
				GLog.n("The Huntsman rams his shield into your chest, choke you of your breath and momentarily stun you!");
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

	/*
	@Override
	protected Item createLoot() {
		MissileWeapon drop = (MissileWeapon)super.createLoot();
		//half quantity, rounded up
		drop.quantity((drop.quantity()+1)/2);
		return drop;
	}
	*/


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
