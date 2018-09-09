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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.GuardCaptain;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.watabou.noosa.BitmapTextMultiline;

public class WndCaptain extends Window {

	private static final String TXT_MESSAGE = "Very good! Now here's your reward!";
	

	private static final int WIDTH = 120;
	private static final float GAP = 2;

	public WndCaptain(GuardCaptain troll) {



	//	k.detach(Dungeon.hero.bag);
	//	resize( WIDTH, (int)titlebar.bottom() );

	IconTitle titlebar = new IconTitle();
		titlebar.icon( troll.sprite() );
		titlebar.label( Messages.titleCase( troll.name ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );
	


	RenderedTextMultiline message = PixelScene.renderMultiline( TXT_MESSAGE, 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
	add( message );


		Item k = Dungeon.hero.belongings.getItem(GoblinEar.class);
		if(k!= null){
		Dungeon.gold += 24*k.quantity;
		k.detachAll(Dungeon.hero.belongings.backpack);
		}


	resize( WIDTH, 25 );
}

}