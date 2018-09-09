
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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Goblin;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;

import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.levels.SewerLevel;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GuardCaptainSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCaptain;

import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class GuardCaptain extends NPC {

	{
		spriteClass = GuardCaptainSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	
	private boolean seenBefore = false;
	private static final String TXT_YELL = "Hey! you there. Yes, you, ";
	private static final String TXT_QUEST_GIVEN = " Ahah! You must be a new adventurer. I'm...Sir Steadfast, friend to you adventurers."+
	" The way to the underground city Loran is now infested with _goblins_ and the city will reward handsomely a florin for each _goblin left ear_ .Whattaya say?";

	private static final String[] TXT_RANDOM = {
		"Two shillings for a _goblin left ear_  ... It's equal to diners for almost a week above ground. Do you think they pay too much? ",
		"There are many pilgrims who seek the city of _Loran_ despite all the rumours these days...",
		"I heard that a plague of some sort is now ravaging _Loran_ , and to stay alive there that you should always bring a _silver sword_ along with you. ",
		"Why are you still here? Just go and kill some _goblins_ ... It's for your own good. You know, it's just what adventurers do! You'll get used to it... ",
		"A two bob bit per goblin left ear. No wonders why half of my men knocked off their work and ran off into the dungeon. If you ever find them, bring them back to me ",};
	private static final String TXT_REWARD = "Oh ho! Good to see you safe. Here's the reward as promised. Come here as often as you like..."; 

	
	@Override
	protected boolean act() {
		
		if (!Quest.given && Dungeon.level.heroFOV[pos]) {
			if (!seenBefore) {
				String k = TXT_YELL + Dungeon.hero.givenName();
				yell(k);
			}
			seenBefore = true;
		} else {
			seenBefore = false;
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
		if (Quest.given) {
			
			GoblinEar tokens = Dungeon.hero.belongings.getItem( GoblinEar.class );
			if (tokens != null) {

				GameScene.show(
			new WndCaptain( this));
			} else {
				int k = Random.Int(TXT_RANDOM.length);
				tell( TXT_RANDOM[k]);
			}
			
		} else {
			tell( TXT_QUEST_GIVEN);
			Quest.given = true;
	
		}

		return false;
	}
	
	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
	

	public static class Quest {
		
		
		
		private static boolean spawned;
		private static boolean given;
		
		public static void reset() {
			spawned = false;
			given = false;
		}
		
		private static final String NODE		= "captain";
		

		private static final String SPAWNED		= "spawned";
		private static final String GIVEN		= "given";
	
		
		public static void storeInBundle( Bundle bundle ) {
			
			Bundle node = new Bundle();
			
			node.put( SPAWNED, spawned );
			
			if (spawned) {
				node.put( GIVEN, given );
			}
			
			bundle.put( NODE, node );
		}
		
		public static void restoreFromBundle( Bundle bundle ) {

			Bundle node = bundle.getBundle( NODE );
			
			if (!node.isNull() && (spawned = node.getBoolean( SPAWNED ))) {
				//alternative	= node.getBoolean( ALTERNATIVE );
				
				given = node.getBoolean( GIVEN );
			}
		}
		
		public static void spawn( SewerLevel level ) {
			if (!spawned && Dungeon.depth ==1) {
				
				GuardCaptain npc = new GuardCaptain();
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
				
				spawned = true;

				
				given = false;
				
				
			}
		}
		
		public static void process( Mob mob ) {
			if (spawned && given) {
				if (( mob instanceof Goblin) ) {
					Dungeon.level.drop( new GoblinEar(), mob.pos ).sprite.drop();
				}
			}
		}
				
		
	}
}