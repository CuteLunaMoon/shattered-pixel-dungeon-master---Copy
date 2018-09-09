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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.BakedChicoryWithHerbs;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChicoryRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.DeadRat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FriedChicoryRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.MysteryMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChargrilledMeat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SlowRoastedRat;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SpicedSauteeBurdockRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.TailedCutlet;
import com.shatteredpixel.shatteredpixeldungeon.items.food.TenderRoastedBabyCarrots;
import com.shatteredpixel.shatteredpixeldungeon.items.food.WildCarrot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FriedBurdockRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FriedCarrot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.BurdockRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Tinder;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.WandOfFireblast;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;


import java.util.ArrayList;

public class FryingPan extends Weapon {

	public static final String AC_COOK = "COOK";
	public static final String AC_COOK_EAT = "Cook & eat";
	public static final float TIME_TO_COOK = 2;
	private boolean cookAndEat;

	protected WndBag.Mode mode = WndBag.Mode.FOOD;
	protected String inventoryTitle = "Select a piece of food";


	{
		image = ItemSpriteSheet.FRYING_PAN;

		unique = true;
		bones = false;

		defaultAction = AC_COOK;

	}


	@Override
	public int min(int lvl) {
		return 1;   //tier 2
	}

	@Override
	public int max(int lvl) {
		return 2;  //tier 1
	}

	@Override
	public int STRReq(int lvl) {
		return 9;  //tier 1
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_COOK);
	//	actions.add(AC_COOK_EAT);
		return actions;
	}

	@Override
	public void execute(final Hero hero, String action) {

		super.execute(hero, action);

		if (action.equals(AC_COOK)) {

			GameScene.selectItem(itemSelector, mode, inventoryTitle);

		}
	//	if (action.equals(AC_COOK_EAT)) {
	//		cookAndEat = true;
	//		GameScene.selectItem(itemSelector, mode, inventoryTitle);

	//	}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int proc(Char attacker, Char defender, int damage) {

		return damage;
	}


	public static void CookStuff(Item item) {

		Hero hero = Dungeon.hero;
		if(hero.CookingSkill <100) {
			hero.CookingSkill++;
		}
		hero.sprite.operate(hero.pos);
		hero.busy();
		hero.spend(TIME_TO_COOK);

		if (item instanceof DeadRat) {
			item.detach(hero.belongings.backpack);
			if(hero.CookingSkill<25) {
				GLog.p("you skin the rat and fried it");
				TailedCutlet fried = new TailedCutlet();

				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}else{
				SlowRoastedRat fried = new SlowRoastedRat();
				GLog.p("you skin the rat, spice it and roast.");
				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}
		} else if (item instanceof MysteryMeat) {

			item.detach(hero.belongings.backpack);
			ChargrilledMeat fried = new ChargrilledMeat();
			GLog.p("You grill the meat. It smells delicious.");
			if (fried.doPickUp(Dungeon.hero)) {
				GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
			} else {
				Dungeon.level.drop(fried, hero.pos).sprite.drop();
			}
		} else if (item instanceof WildCarrot) {
			if(hero.CookingSkill<50) {
				GLog.p("You fried the carrots. Perhaps it would be better if you have some butter.");
				item.detach(hero.belongings.backpack);
				FriedCarrot fried = new FriedCarrot();

				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}else{
				item.detach(hero.belongings.backpack);
				TenderRoastedBabyCarrots fried = new TenderRoastedBabyCarrots();
				GLog.p("You roast the carrots until they are tender and add some spices. The food smell delicious.");
				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}
		} else if (item instanceof BurdockRoot) {
			item.detach(hero.belongings.backpack);
			if(hero.CookingSkill<50) {
				GLog.p("You wash the roots and fried it.");

				FriedBurdockRoot fried = new FriedBurdockRoot();

				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}else{
				GLog.p("You wash the roots then sautee them with some wild herbs that smell like a mix of Sage and Rosemarry.");
				SpicedSauteeBurdockRoot fried = new SpicedSauteeBurdockRoot();

				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}
		}
		else if (item instanceof ChicoryRoot) {
			item.detach(hero.belongings.backpack);
			if (hero.CookingSkill < 95) {
				GLog.p("You wash the roots and fried it.");

				FriedChicoryRoot fried = new FriedChicoryRoot();
				GLog.p("You wash the tubers and fried it.");
				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}else{
				GLog.p("You chop up the chicory tuber, add some smashed Blood berries and leaves of Sun Grass and Lumen Flower and bake it. The food smells delicious.");

				BakedChicoryWithHerbs fried = new BakedChicoryWithHerbs();
				if (fried.doPickUp(Dungeon.hero)) {
					GLog.i(Messages.get(Dungeon.hero, "you_now_have", fried.name()));
				} else {
					Dungeon.level.drop(fried, hero.pos).sprite.drop();
				}
			}
		}

	}


	protected static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null && item instanceof Food) {
				Tinder tinders = Dungeon.hero.belongings.getItem(Tinder.class);
				WandOfFireblast wand = Dungeon.hero.belongings.getItem(WandOfFireblast.class);
				if (item.canBeCook == true) {
					if (tinders != null) {
						Hero hero = Dungeon.hero;
						tinders.detach(hero.belongings.backpack);
						CookStuff(item);
					} else if (wand != null) {
						if (wand.curCharges > 0) {
							wand.curCharges -= 1;
							wand.updateQuickslot();

							CookStuff(item);
						} else {
							GLog.i("You need something to start a fire");
						}
					}else{
						GLog.i("You need something to start a fire");
					}
				}

			}
		}


	};

}
