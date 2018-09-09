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
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndCultist extends Window {

	private static final String TXT_MESSAGE = "The cost for blood treatment is _500 coins_ ...I'm sure that your money will be used for our church's orphanage and its flower garden.";


	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
	private static final int WIDTH		= 120;
private  static  final int BTN_HEIGHT = 20;

	private RedButton btnHeal;


	public WndCultist(Cultist cultist) {



	//	k.detach(Dungeon.hero.bag);
	//	resize( WIDTH, (int)titlebar.bottom() );

		IconTitle titlebar = new IconTitle();
		titlebar.icon( cultist.sprite() );
		titlebar.label( Messages.titleCase( cultist.name ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );



	RenderedTextMultiline message = PixelScene.renderMultiline( TXT_MESSAGE, 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );


			btnHeal = new RedButton("HEAL"){
				@Override
				protected void onClick() {
					Dungeon.gold-=500;
					Dungeon.hero.HP = Dungeon.hero.HT;
					hide();
				}
			};



			btnHeal.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnHeal );


	resize( WIDTH, (int) btnHeal.bottom());
	}

}