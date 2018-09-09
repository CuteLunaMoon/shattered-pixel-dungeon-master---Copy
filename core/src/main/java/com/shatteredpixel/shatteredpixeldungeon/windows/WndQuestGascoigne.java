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
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Yharnamite;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GoblinEar;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextMultiline;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;

public class WndQuestGascoigne extends Window {

	private static final String TXT_MESSAGE = 	"Is it you again, mister? And you are an adventurer? Mm... Then, please, can I ask you a small kindness? My dad went to Loran for the hunt, and rarely came back on his own so my mum and grand dad went to find him, and now they are gone, too... Can you please look for them? I'm home with my sister but we are so scared.";
		private static final String TXT_MESSAGE2 = 	"Is it you again, miss? And you are an adventurer? Mm... Then, please, can I ask you a small kindness? My dad went to Loran for the hunt, and rarely came back on his own so my mum and grand dad went to find him, and now they are gone, too... Can you please look for them? I'm home with my sister but we are so scared.";
	



	private static final int BTN_SIZE	= 36;
	private static final float GAP		= 2;
	private static final float BTN_GAP	= 10;
	private static final int WIDTH		= 120;
private  static  final int BTN_HEIGHT = 20;

	private RedButton btnHeal;
	private RedButton btnRefuse;


	public WndQuestGascoigne(final Yharnamite girl) {



	//	k.detach(Dungeon.hero.bag);
	//	resize( WIDTH, (int)titlebar.bottom() );

		IconTitle titlebar = new IconTitle();
		titlebar.icon( girl.sprite() );
		titlebar.label( Messages.titleCase( "Young Girl" ) );
		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );


		
	RenderedTextMultiline message = PixelScene.renderMultiline( TXT_MESSAGE, 6 );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );


			btnHeal = new RedButton("ACCEPT"){
				@Override
				protected void onClick() {
				hide();
					girl.acceptQuest = true;
					girl.tell(girl.QUEST_TXT_GASCOIGNE[2]);	
				}
			};



			btnHeal.setRect(0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT);
		add( btnHeal );
		btnRefuse = new RedButton("REFUSE"){
				@Override
				protected void onClick() {
					hide();
				if(Dungeon.hero.givenName() == "Huntress"){
				girl.tell(girl.QUEST_TXT_GASCOIGNE[1]);
				}else{
				girl.tell(girl.QUEST_TXT_GASCOIGNE[0]);
				}
				
				}
			};



			btnRefuse.setRect(0, btnHeal.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add( btnRefuse );


	resize( WIDTH, (int) btnRefuse.bottom());
	}

}