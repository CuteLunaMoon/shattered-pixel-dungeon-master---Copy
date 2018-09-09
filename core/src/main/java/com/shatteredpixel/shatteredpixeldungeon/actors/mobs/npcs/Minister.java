
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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;

import com.shatteredpixel.shatteredpixeldungeon.sprites.BloodMinisterSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.InnKeeperSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBloodMinister;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndInnKeeper;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.PathFinder;

public class Minister extends NPC {

	{
		spriteClass = BloodMinisterSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	
	private boolean seenBefore = false;

	public boolean giveBlood = false;
	public boolean questGiven =false;
	
	private static final String TXT_YELL = "Hello weary traveller. Welcome to great city Loran. ";
	private static final String  TXT_MESSAGE = " You must have heard of the plague that ravages our city. Well, the rumour was true. Our city is indeed plagued by the scourge of blood and beast. Well, but where's an outsider like yourself to begin? Easy... Bring me 5 _Blood Vials_ and I'll perform Blood Ministration for you, to bring out your real strength for the ordeal ahead";
	private static final String TXT_REQUEST =" Bring me 5 _Blood Vials_ and I'll perform a miracle for you.";

	@Override
	protected boolean act() {
		
		if (!seenBefore&&Dungeon.level.heroFOV[pos]) {
				String k = TXT_YELL + Dungeon.hero.givenName();
				yell(k);
					seenBefore = true;
		}
		
		throwItem();
		
		return super.act();
	}
	
	@Override
	public int defenseSkill( Char enemy ) {
		return 10000;
	}
	
	@Override
	public void damage( int dmg, Object src ) {
	}
	
	@Override
	public void add( Buff buff ) {
	}
	
	@Override
	public boolean reset() {
		return true;
	}
	
	@Override
	public boolean interact() {
		sprite.turnTo( pos, Dungeon.hero.pos );
		if(!questGiven){
			GameScene.show(new WndQuest(this,TXT_MESSAGE));
			questGiven = true;
		}else{
			PotionOfHealing blood = Dungeon.hero.belongings.getItem(PotionOfHealing.class);
			if(blood!= null && blood.quantity >=5){

				GameScene.show(new WndBloodMinister(this));
			}else {
				GameScene.show(new WndQuest(this,TXT_REQUEST));
			}
		}
			

		return false;
	}
	public static void spawn( SewerLevel level ) {
			if ( Dungeon.depth ==17) {
				
				Minister npc = new Minister();
				do {
					npc.pos = level.randomRespawnCell();
				} while (
						npc.pos == -1 ||
						level.heaps.get( npc.pos ) != null ||
						level.traps.get( npc.pos) != null ||
						level.findMob( npc.pos ) != null ||
						//The imp doesn't move, so he cannot obstruct a passageway
						!(level.passable[npc.pos + PathFinder.CIRCLE4[0]] && level.passable[npc.pos + PathFinder.CIRCLE4[2]]) ||
						!(level.passable[npc.pos + PathFinder.CIRCLE4[1]] && level.passable[npc.pos + PathFinder.CIRCLE4[3]]));
				level.mobs.add( npc );
				
				
			}
		}
}