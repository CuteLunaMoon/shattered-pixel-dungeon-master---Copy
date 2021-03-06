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
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndInnKeeper extends Window {

	private static final String TXT_GREETING = "Zis is zee adfenturer's guilt. How can I help you, mein herr?";
	private static final String TXT_DUNGEON = " _Loran_ used to be a peaceful place for human und animorphs...But efer since zee first heroes brought zee fake Amulet back to _Loran_ , sings got outta kontrol. Tragische, tragische zeit. Zee underground city vas lost to... _zee geiBel_ . Und it vas unsafe for a fuchs-morph like me to shtay in _Loran_ anymore so I relokated here. Ich kann dir nicht mehr sagen. *whisper* Dieser Abend ist die Nacht der Jagd. Geh nicht nach _Loran_";
	private static final String TXT_EMPLOYMENT = "Ja, you vant to look for a job? Go a head and ask fraulein Urta. I heard zat she's lookink for a helper or so. She's zee former Rittmeister of _Loran_ *whisper* So be careful";
	private static final String TXT_AMULET =" Ah, zee Amulet of Yendor. I don't you much about zat. Zee church claimed zee one brought back to zee surface vas fake. Aber es gibt ein Gerucht. *whisper* Zat zee real one would grant wishes or so. Maybe it's some made-up unsinn, maybe not. ";
	//private static final String TXT_DRINK = "";
	private static final String TXT_COMEBACK ="Come backs soon!";

	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
		private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;

	private RedButton btnDungeon;
	private RedButton btnEmployment;
	private RedButton btnAmuletOfYendor;
	private RedButton btnDrink;
	


	public WndInnKeeper(final InnKeeper keeper) {



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

			btnDungeon = new RedButton("About the city Loran"){
				@Override
				protected void onClick() {
					hide();
						GameScene.show(
					new WndQuest( keeper, TXT_DUNGEON ));
				}
			};
						btnDungeon.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnDungeon );
			btnEmployment = new RedButton("Look for a job"){
				@Override
				protected void onClick() {
					hide();
						GameScene.show(
					new WndQuest( keeper, TXT_EMPLOYMENT));
				}
			};
				btnEmployment.setRect(0, btnDungeon.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnEmployment );
		btnAmuletOfYendor = new RedButton("About the Amulet of Yendor"){
			@Override
			protected void onClick() {
				hide();
				GameScene.show(
						new WndQuest( keeper, TXT_AMULET ));
			}
		};
		btnAmuletOfYendor.setRect(0, btnEmployment.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnAmuletOfYendor);



	resize( WIDTH, (int) btnAmuletOfYendor.bottom());
	}

}