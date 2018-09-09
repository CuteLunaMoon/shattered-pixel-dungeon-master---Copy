/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
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
package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.pets;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Terror;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.HashSet;

/*
import com.github.dachhack.sprout.Dungeon;
import com.github.dachhack.sprout.actors.Actor;
import com.github.dachhack.sprout.actors.Char;
import com.github.dachhack.sprout.actors.buffs.Buff;
import com.github.dachhack.sprout.actors.hero.Hero;
import com.github.dachhack.sprout.actors.hero.HeroSubClass;
import com.github.dachhack.sprout.actors.mobs.Mob;
import com.github.dachhack.sprout.effects.CellEmitter;
import com.github.dachhack.sprout.effects.particles.ElmoParticle;
import com.github.dachhack.sprout.items.Heap;
import com.github.dachhack.sprout.items.rings.RingOfHaste;
import com.github.dachhack.sprout.levels.Level;
import com.github.dachhack.sprout.sprites.HeroSprite;
import com.github.dachhack.sprout.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;
*/

public abstract class PET extends Mob {

	{
		HP = HT = 1;
		EXP = 0;

		//hostile = false;
		state = HUNTING;
		alignment = Alignment.ALLY;

	}
	
	public int level;
	public int kills;
	public int type;
	public int experience;
	public int cooldown;
	public int goaways = 0;
	public boolean callback = false;
	public boolean stay = false;
	/*
	 type
	 1 = 
	 2 = bee
	 3 = 
	 4 = 
	 5 = 
	  
	  
	 */
	
	private static final String KILLS = "kills";
	private static final String LEVEL = "level";
	private static final String TYPE = "type";
	private static final String EXPERIENCE = "experience";
	private static final String COOLDOWN = "cooldown";
	private static final String GOAWAYS = "goaways";
	private static final String CALLBACK = "callback";
	private static final String STAY = "stay";


	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(KILLS, kills);
		bundle.put(LEVEL, level);
		bundle.put(TYPE, type);
		bundle.put(EXPERIENCE, experience);
		bundle.put(COOLDOWN, cooldown);
		bundle.put(GOAWAYS, goaways);
		bundle.put(CALLBACK, callback);
		bundle.put(STAY, stay);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		kills = bundle.getInt(KILLS);
		level = bundle.getInt(LEVEL);
		type = bundle.getInt(TYPE);
		experience = bundle.getInt(EXPERIENCE);
		cooldown = bundle.getInt(COOLDOWN);
		goaways = bundle.getInt(GOAWAYS);
		callback = bundle.getBoolean(CALLBACK);
		stay = bundle.getBoolean(STAY);
		adjustStats(level);
	}

	/*
	protected void throwItem() {
		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			int n;
			do {
				n = pos + Level.NEIGHBOURS8[Random.Int(8)];
			} while (!Level.passable[n] && !Level.avoid[n]);
			Dungeon.level.drop(heap.pickUp(), n).sprite.drop(pos);
		}
	}
	*/
	
	public void adjustStats(int level) {
	}
	
	public void spawn(int level) {
		this.level = level;
        adjustStats(level);
	}
	
	@Override
	protected boolean act() {
		
		assignPet(this);
		return super.act();
	}
	
	
	@Override
	public void damage(int dmg, Object src) {
		
		if (src instanceof Hero){
			goaways++;
			GLog.n("Keep that up and your %s may leave you!",name);
		}
		
		if (goaways>2){
			flee();
		}
		
		super.damage(dmg,src);
		
	}
	
	@Override
	public void die(Object cause) {
		
		//    Dungeon.hero.haspet=false;
		//    Dungeon.hero.petCount++;
			GLog.n("Your pet %s dies.",name);

		super.die(cause);

	}

	/*
	@Override
	public float speed() {

		float speed = super.speed();
		
		int hasteLevel = Dungeon.petHasteLevel;
		
		if(hasteLevel>10){
			hasteLevel=10;
		}

		if (hasteLevel != 0)
			speed *= Math.pow(1.2, hasteLevel);

		return speed;
	}
	*/
	
	public void flee() {
	//	Dungeon.hero.haspet=false;
	    GLog.n("Your %s knows when it's not wanted!",name);
		destroy();
		sprite.killAndErase();
		CellEmitter.get(pos).burst(LeafParticle.LEVEL_SPECIFIC, 6);
	}

	@Override
	protected Char chooseEnemy() {
		
		
		if(enemy != null && !enemy.isAlive() && enemy instanceof Mob){
			kills++;
			experience+= Dungeon.depth;
		}
		
		if (experience >= level*(level+level) && level < 20){			
			level++;
			GLog.p("Your pet %s gains a level!",name);
			adjustStats(level);
			experience=0;
		}


		Terror terror = buff( Terror.class );
		if (terror != null) {
			Char source = (Char)Actor.findById( terror.object );
			if (source != null) {
				return source;
			}
		}

		//find a new enemy if..
		boolean newEnemy = false;
		//we have no enemy, or the current one is dead
		if ( enemy == null || !enemy.isAlive() || state == WANDERING)
			newEnemy = true;
			//We are an ally, and current enemy is another ally.
		else if (alignment == Alignment.ALLY && enemy.alignment == Alignment.ALLY)
			newEnemy = true;
			//We are amoked and current enemy is the hero
		else if (buff( Amok.class ) != null && enemy == Dungeon.hero)
			newEnemy = true;

		if ( newEnemy ) {

			HashSet<Char> enemies = new HashSet<>();

			//if the mob is amoked...
			if ( buff(Amok.class) != null) {
				//try to find an enemy mob to attack first.
				for (Mob mob : Dungeon.level.mobs)
					if (mob.alignment == Alignment.ENEMY && mob != this && fieldOfView[mob.pos])
						enemies.add(mob);

				if (enemies.isEmpty()) {
					//try to find ally mobs to attack second.
					for (Mob mob : Dungeon.level.mobs)
						if (mob.alignment == Alignment.ALLY && mob != this && fieldOfView[mob.pos])
							enemies.add(mob);

					if (enemies.isEmpty()) {
						//try to find the hero third
						if (fieldOfView[Dungeon.hero.pos]) {
							enemies.add(Dungeon.hero);
						}
					}
				}

				//if the mob is an ally...
			} else if ( alignment == Alignment.ALLY ) {
				//look for hostile mobs that are not passive to attack
				for (Mob mob : Dungeon.level.mobs)
					if (mob.alignment == Alignment.ENEMY
							&& fieldOfView[mob.pos]
							&& mob.state != mob.PASSIVE)
						enemies.add(mob);

				//if the mob is an enemy...
			} else if (alignment == Alignment.ENEMY) {
				//look for ally mobs to attack
				for (Mob mob : Dungeon.level.mobs)
					if (mob.alignment == Alignment.ALLY && fieldOfView[mob.pos])
						enemies.add(mob);

				//and look for the hero
				if (fieldOfView[Dungeon.hero.pos]) {
					enemies.add(Dungeon.hero);
				}

			}

			//neutral character in particular do not choose enemies.
			if (enemies.isEmpty()){
				return null;
			} else {
				//go after the closest potential enemy, preferring the hero if two are equidistant
				Char closest = null;
				for (Char curr : enemies){
					if (closest == null
							|| Dungeon.level.distance(pos, curr.pos) < Dungeon.level.distance(pos, closest.pos)
							|| Dungeon.level.distance(pos, curr.pos) == Dungeon.level.distance(pos, closest.pos) && curr == Dungeon.hero){
						closest = curr;
					}
				}
				return closest;
			}

		} else
			return enemy;
}
	
	@Override
	protected boolean getCloser(int target) {
		if (enemy != null && !callback) {
		   target = enemy.pos;		
		} else if (checkNearbyHero()) {
		   target = wanderLocation() != -1 ? wanderLocation() : Dungeon.hero.pos;	
		   callback = false;
		} else if(Dungeon.hero.invisible==0){
			target = Dungeon.hero.pos;
		} else {
			target = wanderLocation() != -1 ? wanderLocation() : Dungeon.hero.pos;				
		}
		
		if (stay) {
			
			return false;
			
		}
		
		return super.getCloser(target);
	}


	protected boolean checkNearbyHero(){
		return Dungeon.level.adjacent(pos, Dungeon.hero.pos);
	}

	public int wanderLocation(){
		  int newPos = -1;
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Dungeon.level.passable;

			for (int n =0; n < PathFinder.NEIGHBOURS8.length; n++) {
				int c = pos + + PathFinder.NEIGHBOURS8[n];
				if (passable[c] && Actor.findChar(c) == null) {
					candidates.add(c);
				}
			}

			newPos = candidates.size() > 0 ? Random.element(candidates) : -1;
			
		return newPos;
	}

	
	@Override
	public void aggro(Char ch) {		
	}
	
	@Override
	public void beckon(int cell) {
	}


	private void assignPet(PET pet){
	/*
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		  Dungeon.hero.petKills=pet.kills;	
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;		
	*/
	}
		
	abstract public void interact();
}