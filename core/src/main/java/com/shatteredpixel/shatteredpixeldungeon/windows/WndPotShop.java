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
package com.shatteredpixel.shatteredpixeldungeon.windows;


import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Cultist;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.GuardCaptain;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.InnKeeper;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Yharnamite;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.HunterSoup;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.BitmapTextMultiline;

public class WndPotShop extends Window {

	private static final String TXT_GREETING = "Ah? You don't smell like one of our hunters... But welcome to _Loran_ , stranger. This is _Loran_ finest pot-shop. What do you want for diner? Well, just speaking... we only have Hunter's Soup here... But give it a try, only 15 gold a dish.";
	private static final String TXT_GREETING2 = "Still alive? Do you have money this time? Or do you have some meat with you?";

	private static final String TXT_DUNGEON = " Ah... As you can see, the hunt occurs every full moon, when the creatures of the night and all sort evil are at full sway... But tonight, there's something different... Something far more sinister and malicious... Hunters leave home as men, and return as beasts... You and I will be one of them , sooner or later...";
	private static final String TXT_DUNGEON2 = "These hunts get worse and worse... _Loran_ is done for, I tell ya... If I had a chance I'll flee this accursed city... I suggest you do the same while you still can";
	private static final String TXT_EMPLOYMENT = " What's in MY SOUP? Oh... It's... a business secret... But don't you worry. I'm an honest pot-shop owner! I swear in the name of Yig the great snake ! I'm  not like the bloody fool on Amygdala street who throws all  kind of dubious mushrooms into his soup... And I heard that the shop in Vicar Laurence's square use corpse parts in their soup... Oh, no I'm not trying to low-ball 'em. I only speak the truth... Honesty is the best policy, as they often say.";
	private static final String TXT_EMPLOYMENT2 = " Huh... What's in the soup? Whatever meat the huntsmen brought me...";
	private static final String TXT_AMULET ="  Huh? As the legend said, the Amulet holds some sort of power... But, you know, a hundred years ago, a group of heroes brought it to _Loran_ , and caused this beastly curse... Now they say that amulet was fake and the real one still lies hidden in the depth of this dungeon... But who knows what truth is... Anyway, wanna have some hot soup?";
	private static final String TXT_AMULET2 =" Tsk... Don't even think of it. Many tried. None come back. Whatever you get from the depth of this hellish dungeon, it only does you harm... Believe mem I did try myself when I was younger and dumber. I went down to the city's underground catacombs...And you know what lie below this city? Death! Death and madness and nightmare!";
	private static final String TXT_NOTENOUGH_MONEY = " No money? Sigh... thought travellers were richer than the flea-ridden citizens of Loran...Tsk... consider this charity. Next time, if you are short in money, bring me an ounce of meat. I'll return ya a hot dish of soup for that ounce of meat.";
	private static final String TXT_NO_MONEY = " If you have no money, bring me an ounce of meat. Any meat is fine... Be it rat, bat or even beast. Strictly speaking, everyone already knows what's in the soup, so no sense splitting hair.";
	private static final String TXT_COMEBACK ="Come backs soon!";

	private boolean secretTold;

	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
		private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;


	private RedButton btnDungeon;
	private RedButton btnEmployment;
	private RedButton btnAmuletOfYendor;
	private RedButton btnDrink;
	


	public WndPotShop (final Yharnamite keeper) {



	//	k.detach(Dungeon.hero.bag);
	//	resize( WIDTH, (int)titlebar.bottom() );

	IconTitle titlebar = new IconTitle();
		titlebar.icon( keeper.sprite() );
		titlebar.label( Messages.titleCase( keeper.name ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
		String a = "";
		String b = "";
		String c = "";
	if(!secretTold) {
		a=  TXT_GREETING;
		b = TXT_DUNGEON;
		c = TXT_EMPLOYMENT;
	}else {
	 a = TXT_GREETING2;
	 b = TXT_DUNGEON2;
	 c = TXT_EMPLOYMENT2;
	}

	RenderedTextMultiline message = PixelScene.renderMultiline( a, 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

			btnDungeon = new RedButton("About the hunt"){
				@Override
				protected void onClick() {
					hide();
					if(!secretTold) {
						GameScene.show(
								new WndQuest(keeper, TXT_DUNGEON));
					}else {
						GameScene.show(
								new WndQuest(keeper, TXT_DUNGEON2));
					}
				}
			};
						btnDungeon.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnDungeon );
			btnEmployment = new RedButton("What's in your soup?"){
				@Override
				protected void onClick() {
					hide();
					if(!secretTold) {
						GameScene.show(
								new WndQuest(keeper, TXT_EMPLOYMENT));
					}else {
						GameScene.show(
								new WndQuest(keeper, TXT_EMPLOYMENT2));
					}
				}
			};
				btnEmployment.setRect(0, btnDungeon.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnEmployment );
		btnAmuletOfYendor = new RedButton("About the Amulet of Yendor"){
			@Override
			protected void onClick() {
				hide();
				if(!secretTold) {
					GameScene.show(
							new WndQuest(keeper, TXT_AMULET));
				}else {
					GameScene.show(
							new WndQuest(keeper, TXT_AMULET2));
				}
			}
		};
		btnAmuletOfYendor.setRect(0, btnEmployment.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnAmuletOfYendor);

		
			btnDrink  = new RedButton(" Buy soup"){
			@Override
				protected void onClick() {
				hide();
				if(Dungeon.gold>=15) {
					GameScene.show(
							new WndQuest(keeper, TXT_COMEBACK));
					Dungeon.gold -= 15;
					HunterSoup gold = new HunterSoup();
					if (gold.doPickUp(Dungeon.hero)) {
						GLog.i(Messages.get(Dungeon.hero, "you_now_have", gold.name()));
					} else {
						Dungeon.level.drop(gold, Dungeon.hero.pos).sprite.drop();
					}
				}else{


					if(!secretTold){
						GameScene.show(
							new WndQuest(keeper, TXT_NOTENOUGH_MONEY));
						secretTold = true;
						HunterSoup gold = new HunterSoup();
						if (gold.doPickUp(Dungeon.hero)) {
							GLog.i(Messages.get(Dungeon.hero, "you_now_have", gold.name()));
						} else {
							Dungeon.level.drop(gold, Dungeon.hero.pos).sprite.drop();
						}
					}else {
						GameScene.show(
								new WndQuest(keeper, TXT_NO_MONEY));
					}

				}
			}
		};
		btnDrink.setRect(0, btnAmuletOfYendor.bottom() + GAP, WIDTH, BTN_HEIGHT);
				add(btnDrink);
		resize( WIDTH, (int) btnDrink.bottom());


	resize( WIDTH, (int) btnDrink.bottom());



	}

}