
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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;

import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.items.rings.Ring;
import com.shatteredpixel.shatteredpixeldungeon.journal.Notes;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DoctorIOSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCaptain;

import com.shatteredpixel.shatteredpixeldungeon.windows.WndCultist;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class Cultist extends NPC {

	{
		spriteClass = DoctorIOSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	
	private boolean seenBefore = false;
	private static final String TXT_YELL = "Hello, weary ";

	@Override
	protected boolean act() {
		
		if (!seenBefore&&Dungeon.level.heroFOV[pos]) {
				String k = TXT_YELL + Dungeon.hero.givenName() + " .Welcome to my humble clinic!";
				yell(k);
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
		if(!seenBefore){
			tell("Oh, well, hello....Splendid. I'm doctor Io of the _Church of the Holy Blood_ .In this little clinic I heal my patients. Come here if you need my service. Or... if you find someone who needs shelter, tell them to seek doctor Io's clinic. I will take proper care of them. They're in your hands, and soon, mine.... ");
		seenBefore = true;
		}else{
			if(Dungeon.gold>=500) {
				GameScene.show(new WndCultist(this));
			}else {
				tell("Oh, well, hello... Good to see you safe. I can perform healing treatment for _500 coins_ .Don't worry, it not only invigorates you but also cures you of your beastly idio... Er... I meant illness. And the money will be used for our church's orphanage. So, be a saint.");
			}
		}
				
		

		return false;
	}
	
	private void tell( String text ) {
		GameScene.show(
			new WndQuest( this, text ));
	}
				

}