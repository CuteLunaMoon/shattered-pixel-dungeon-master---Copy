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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.BlastParticle;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.SmokeParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.enchantments.Projecting;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.PelletCrossbow;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ThrowingStone extends MissileWeapon {
	
	{
		image = ItemSpriteSheet.THROWING_STONE;
		
		sticky = false;
		
		bones = false;
		
	}
	
	@Override
	public int min(int lvl) {
		return bow != null ? 2 +bow.level():1;
	}
	
	@Override
	public int max(int lvl) {
			return bow != null ? 7 +bow.level():5;
	}
	
	@Override
	public int STRReq(int lvl) {
		return 9;
	}
	
	@Override
	protected float durabilityPerUse() {
		if(bow != null && bow.level >=5){
		return durability; //break
		}else{
			return super.durabilityPerUse()*3.34f;
		}
	}
	private static PelletCrossbow bow;
		private void updateCrossbow(){
		if (Dungeon.hero.belongings.weapon instanceof PelletCrossbow){
			bow = (PelletCrossbow) Dungeon.hero.belongings.weapon;
		} else {
			bow = null;
		}
	}
	
		@Override
	public int proc(Char attacker, Char defender, int damage) {
	if(Random.Int(0,5)>3){
		Buff.prolong( defender, Blindness.class, Random.Float( 1f, 1f + level ) );
		}
		if (bow != null && bow.enchantment != null){
			damage = bow.enchantment.proc(bow, attacker, defender, damage);
		}
		return super.proc(attacker, defender, damage);
	}
	
	@Override
	public int throwPos(Hero user, int dst) {
		if (bow != null && bow.hasEnchant(Projecting.class)
				&& !Dungeon.level.solid[dst] && Dungeon.level.distance(user.pos, dst) <= 4){
			return dst;
		} else {
			return super.throwPos(user, dst);
		}
	}
	@Override
	protected void onThrow(int cell) {
		updateCrossbow();
		if(bow != null && bow.level >=5) {
			Sample.INSTANCE.play(Assets.SND_BLAST);

			if (Dungeon.level.heroFOV[cell]) {
				CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
			}

			boolean terrainAffected = false;
			for (int n : PathFinder.NEIGHBOURS9) {
				int c = cell + n;
				if (c >= 0 && c < Dungeon.level.length()) {
					if (Dungeon.level.heroFOV[c]) {
						CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
					}


					Char ch = Actor.findChar(c);
					if (ch != null ) {
						//those not at the center of the blast take damage less consistently.


						int dmg = Random.NormalIntRange(min(), max())/2 - ch.drRoll();
						if (dmg > 0) {
							ch.damage(dmg, this);
						}
						int procDmg = dmg/2;
						if(bow.enchantment!=null)
						bow.enchantment.proc(bow, Dungeon.hero, ch, procDmg);
					}
				}

			}
		}
		
		super.onThrow(cell);
	}

	
	@Override
	public String info() {
		updateCrossbow();
		return super.info();
	}
	
	@Override
	public int price() {
		return 0;
	}
}
