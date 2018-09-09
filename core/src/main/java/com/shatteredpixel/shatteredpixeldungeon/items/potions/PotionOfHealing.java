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

package com.shatteredpixel.shatteredpixeldungeon.items.potions;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfHealing extends Potion {

	public String[] bloodborneTxt = {" \n You can understand why. The blood healing is inexplicably intoxicating. The smell of blood is appealing and the sweetness of blood is undeniable.","\n There's a starling sign that somehow you have changed from the repeated injection of blood.  If you are a human, then why aren't you shy away from the blood?","\n Sometime, the thought of injecting this sweet blood into your vein overwhelms you. Water can no longer quench your thirst, only this sweet, sweet blood can. A terrible, maddening thought plagues your mind: What kind of men or to be exact, what kind of creatures in the semblance of men that are drawn to blood like filthy leeches? And what kind of horrible monster lurks within the frame of you?"};

	{
		initials = 2;
        image = ItemSpriteSheet.POTION_CRIMSON;
		bones = true;
	}
	
	@Override
	public void apply( Hero hero ) {
		setKnown();
		//starts out healing 30 hp, equalizes with hero health total at level 11
		hero.bloodUsed++;
		Buff.affect( hero, Healing.class ).setHeal((int)(0.8f*hero.HT + 14), 0.333f, 0);
	//	cure( hero );
		GLog.p( Messages.get(this, "heal") );
	}
	
	public static void cure( Char ch ) {
		Buff.detach( ch, Poison.class );
		Buff.detach( ch, Cripple.class );
		Buff.detach( ch, Weakness.class );
		Buff.detach( ch, Bleeding.class );
		
	}


	@Override
	public boolean isIdentified() {
		return true;
	}
	@Override
	public  String desc(){
		int k = Dungeon.hero.bloodUsed();
		String f=" The sweet, sweet blood.";
		if(k <3){
			f = bloodborneTxt[0];
		}else if (k ==3 || k ==4 || k ==5){
			f = bloodborneTxt[1];
		}else{
			f = bloodborneTxt[2];
		}

		return  super.desc()+f;
	}
}
