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
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.watabou.utils.Random;



public class MeleeWeapon extends Weapon {


	public int tier;


	@Override
	public int min(int lvl) {
		
		return qualityMinBonusDamage()+ tier +  //base
				lvl;    //level scaling
	}

		@Override
		public String desc() {
			String k = "";
			switch (quality) {
				case Well_Worn: {
					k = " From the number of dents and scratches on this weapon, you can tell it has seen lots of battle.\n\n";
				}
				break;
				case Used: {
					k = "This weapon seems to be a little worn from repeated usage.\n\n ";
				}
				break;
				case Good: {
					k = " This weapon is in good condition. \n\n";
				}
				break;
				case Well_Crafted: {
					k = "A well-crafted weapon. Its sturdy frame and flexibility shows the fineness of the craftmanship.\n\n";
				}
				break;

			}
			return k + Messages.get(this, "desc");
		}
	public  void SetQuality(itemQuality newQuality){
		quality = newQuality;
	}
	@Override
	public  void RandomlySetQuality()
	{
		int k = Random.Int(0,4);
		switch (k){
			case 0:{quality = itemQuality.Well_Worn;}break;
			case 1:{quality = itemQuality.Used;}break;
			case 2:{quality = itemQuality.Normal;}break;
			case 3:{quality = itemQuality.Good;}break;
			case 4:{quality = itemQuality.Well_Crafted;}break;
		}
	}
	@Override
	public int max(int lvl) {
		
		return qualityMaxBonusDamage()+5*(tier+1) +    //base
				lvl*(tier+1);   //level scaling
	}
	
	public int qualityMaxBonusDamage(){
	int k =0;
		switch (quality){
			case Well_Worn:{
				k=-2;
			}break;
			case Used:{
				k=-1;
			}break;
			case Good:{
				k=1+tier/2;
			}break;
			case Well_Crafted:{
				k=1+tier;
			}break;
		}
		return k;
	}
	

	
	

	
	public int qualityMinBonusDamage(){
		int k =0;
		switch (quality){
			case Good:{
				k=1+tier/2;
			}break;
			case Well_Crafted:{
				k=tier;
			}break;
		}
		return k;
	}

	public int STRReq(int lvl){
		lvl = Math.max(0, lvl);
		//strength req decreases at +1,+3,+6,+10,etc.
		return (8 + tier * 2) - (int)(Math.sqrt(8 * lvl + 1) - 1)/2;
	}
	
	@Override
	public int damageRoll(Char owner) {
		int damage = augment.damageFactor(super.damageRoll( owner ));

		if (owner instanceof Hero) {
			int exStr = ((Hero)owner).STR() - STRReq();
			if (exStr > 0) {
				damage += Random.IntRange( 0, exStr );
			}
		}
		
		return damage;
	}
	
	@Override
	public String info() {

		String info = desc();

		if (levelKnown) {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_known", tier, augment.damageFactor(min()), augment.damageFactor(max()), STRReq());
			if (STRReq() > Dungeon.hero.STR()) {
				info += " " + Messages.get(Weapon.class, "too_heavy");
			} else if (Dungeon.hero.STR() > STRReq()){
				info += " " + Messages.get(Weapon.class, "excess_str", Dungeon.hero.STR() - STRReq());
			}
		} else {
			info += "\n\n" + Messages.get(MeleeWeapon.class, "stats_unknown", tier, min(0), max(0), STRReq(0));
			if (STRReq(0) > Dungeon.hero.STR()) {
				info += " " + Messages.get(MeleeWeapon.class, "probably_too_heavy");
			}
		}

		String stats_desc = Messages.get(this, "stats_desc");
		if (!stats_desc.equals("")) info+= "\n\n" + stats_desc;

		switch (augment) {
			case SPEED:
				info += "\n\n" + Messages.get(Weapon.class, "faster");
				break;
			case DAMAGE:
				info += "\n\n" + Messages.get(Weapon.class, "stronger");
				break;
			case NONE:
		}

		if (enchantment != null && (cursedKnown || !enchantment.curse())){
			info += "\n\n" + Messages.get(Weapon.class, "enchanted", enchantment.name());
			info += " " + Messages.get(enchantment, "desc");
		}

		if (cursed && isEquipped( Dungeon.hero )) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed_worn");
		} else if (cursedKnown && cursed) {
			info += "\n\n" + Messages.get(Weapon.class, "cursed");
		}
		
		return info;
	}

	
	@Override
	public int price() {
		int price = 30 * tier;
		int k =0;
		switch (quality){
			case Well_Worn:{
				k=-20;
			}break;
			case Used:{
				k=-10;
			}break;
			case Good:{
				k=10;
			}break;
			case Well_Crafted:{
				k=30;
			}break;
		}
		price+=k;
		if (hasGoodEnchant()) {
			price *= 1.5;
		}
		if (cursedKnown && (cursed || hasCurseEnchant())) {
			price /= 2;
		}
		if (levelKnown && level() > 0) {
			price *= (level() + 1);
		}
		if (price < 1) {
			price = 1;
		}
		return price;
	}

}