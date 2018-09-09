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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.ThrowingStone;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class PelletCrossbow extends MeleeWeapon {

	public static final String AC_SEARCH = "SEARCH";
	public static final float TIME_TO_SEARCH= 2;
	//protected boolean heavy = false;

	{
		image = ItemSpriteSheet.CROSSBOW;
		
		//check Dart.class for additional properties
		
		tier = 2;
	}
		@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_SEARCH);
	//	actions.add(AC_COOK_EAT);
		return actions;
	}
		@Override
	public void execute(final Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_SEARCH)) {

		Search();

		}

	}
	@Override
	public int max(int lvl) {
		return  2*(tier) +    // 8 damage max
				lvl;     //+1 per level
	}
	
	public void Search(){
		Hero hero = Dungeon.hero;
	
		hero.sprite.operate(hero.pos);
		hero.busy();
		hero.spend(TIME_TO_SEARCH);
		int k = Random.Int(0,100);
		if(k>=50){
		ThrowingStone fried = new ThrowingStone();
		fried.quantity = Random.Int(1,4);
				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
		}else if(k <50 && k >=10 ){
			GLog.i(" You found nothing");
		}else{

			if( Random.Int(0,6) ==5){
				Item g = Generator.random(Generator.Category.MISSILE);
				int h = Random.Int(1,3);
				g.quantity = h;
				String kkk = " You are in luck! You noticed some " + g.name()+" lying in a dark corner.";
				GLog.i(kkk);
				if (g.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", g.name()));
				} else {
					Dungeon.level.drop(g, hero.pos).sprite.drop();
				}
			}else{
				Gold g = new Gold();
				int h = Random.Int(1,10) + Dungeon.depth/2;
				g.quantity = h;
				GLog.i(" You are in luck! You noticed some coins lying here and there on the ground. ");
				if (g.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", g.name()));
				} else {
					Dungeon.level.drop(g, hero.pos).sprite.drop();
				}
			}
			}

	}
	@Override
	public  String desc(){

		String f=" \n Pebbles thrown by magically infused Pellet Crossbow will shatter violently, dealing some damage to nearby enemies.";
	if(level>=5) {

		return super.desc() + f;
	}else{
		return super.desc();
	}
	}
}
