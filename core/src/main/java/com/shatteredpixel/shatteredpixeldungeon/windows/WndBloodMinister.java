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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Minister;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
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

public class WndBloodMinister extends Window {

	private static final String TXT_GREETING = "Very well. Now let's sign you up for a werewolf hunting contrast. Good, good. All signed and sealed. Now, what's your past, to begin with?";
	private static final String TXT_VETERAN ="A military veteran? So you came back from that bitter war with the Orcs. Tragic, tragic my friend. But I trust that your strength and skill surpass others.";
	private static final String TXT_STREET ="A street urchin? My apologize, my dear. Life must have been harsh to you. But in comparison to others, you know how to make the most out of any situation.";
	private static final String TXT_ARCANE = "A magic user? There aren't many of your irk today and the magic is fading fast like a flickering flame in the wind. I trust that you can see and hear what others couldn't";
	private static final String TXT_CRIMINAL =" An outlaw? There're many who seeks second chance in our city. Don't worry my friend.";

	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
		private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;

	private RedButton btnDungeon;
	private RedButton btnEmployment;
	private RedButton btnAmuletOfYendor;
	private RedButton btnDrink;
	


	public WndBloodMinister(final Minister keeper) {



	//	k.detach(Dungeon.hero.bag);
	//	resize( WIDTH, (int)titlebar.bottom() );

	IconTitle titlebar = new IconTitle();
		titlebar.icon( keeper.sprite() );
		titlebar.label( Messages.titleCase( keeper.name ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );



	RenderedTextMultiline message = PixelScene.renderMultiline( TXT_GREETING, 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );

			btnDungeon = new RedButton("Military Veteran"){
				@Override
				protected void onClick() {
					hide();
						GameScene.show(
					new WndQuest( keeper, TXT_VETERAN ));
					Dungeon.hero.STR +=2;
					Dungeon.hero.SKL +=1;
					GLog.p("Your Strength and SKill are increased");
					keeper.giveBlood = true;
						End(keeper);
				}
			};
						btnDungeon.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnDungeon );
			btnEmployment = new RedButton("Street Urchin"){
				@Override
				protected void onClick() {
					hide();
						GameScene.show(
					new WndQuest( keeper, TXT_STREET));
					Dungeon.hero.STR +=1;
					Dungeon.hero.SKL +=3;
					GLog.p("Your SKill and Strength  are increased");
					keeper.giveBlood = true;
						End(keeper);
				}
			};
				btnEmployment.setRect(0, btnDungeon.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnEmployment );
		btnAmuletOfYendor = new RedButton("Practice Arcane Art"){
			@Override
			protected void onClick() {
				hide();
				GameScene.show(
						new WndQuest( keeper, TXT_ARCANE ));
						Dungeon.hero.ARC +=5;
						GLog.p("Your Arcane power is increased");
						keeper.giveBlood = true;
							End(keeper);
			}
		};

		btnAmuletOfYendor.setRect(0, btnEmployment.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnAmuletOfYendor);
		btnDrink = new RedButton("Bush Ranger"){
			@Override
			protected void onClick() {
				hide();
				GameScene.show(
						new WndQuest( keeper, TXT_CRIMINAL ));
						Dungeon.hero.HT +=5;
						Dungeon.hero.STR+=1;
						Dungeon.hero.SKL +=1;
						GLog.p("Your feel stronger. Your HP, Skill and Strength are slightly increased.");
						keeper.giveBlood = true;
						End(keeper);
			}
		};
		btnDrink.setRect(0, btnAmuletOfYendor.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnDrink);


	resize( WIDTH, (int) btnDrink.bottom());
	}
	
	public void End(Minister keeper){
	//keeper.yell( "Farewell, not it's your own nightmare.");
		keeper.destroy();
		
		keeper.sprite.die();
		
	}

}