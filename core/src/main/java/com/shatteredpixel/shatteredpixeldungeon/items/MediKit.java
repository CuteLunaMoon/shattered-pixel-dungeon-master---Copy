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

package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class MediKit extends Item {

	{
		//initials = 2;
        image = ItemSpriteSheet.MED_KIT;
		//	bones = true;
		stackable = true;
		defaultAction = AC_USE;
		
	}
		private static final float TIME_TO_HEAL = 2;
	private static final String AC_USE = "USE";
	public void apply( Hero hero ) {

		//Only heal a bit
		hero.HP = hero.HP+Math.min((hero.HT/4), hero.HT-hero.HP);
	//	Buff.affect( hero, Healing.class ).setHeal((int)(0.8f*hero.HT + 14), 0.333f, 0);
		cure( hero );
		GLog.p( " You wash and pack up your wounds" );
	}
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions( hero );
		actions.add ( AC_USE );
		return actions;
	}
	
	@Override
	public void execute(Hero hero, String action) {

		if (action.equals(AC_USE )) {
			apply(hero);
		}
        detach( hero.belongings.backpack );
        hero.sprite.operate(hero.pos);
        hero.spend(TIME_TO_HEAL);
        hero.busy();
        Sample.INSTANCE.play(Assets.SND_OPEN);
        	
		super.execute(hero, action);
	
	}
	
	public static void cure( Char ch ) {
	//	Buff.detach( ch, Poison.class );
		Buff.detach( ch, Ooze.class );
	//	Buff.detach( ch, Burn.class );
		Buff.detach( ch, Bleeding.class );
		
	}

	@Override
	public int price() {
		return 35;
	}
	@Override
	public boolean isIdentified() {
		return true;
	}
}
