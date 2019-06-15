/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Yet Another Pixel Dungeon
 * Copyright (C) 2015-2016 Considered Hamster
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
package com.shatteredpixel.shatteredpixeldungeon.levels.features;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;

import com.shatteredpixel.shatteredpixeldungeon.items.food.FriedCarrot;
import com.shatteredpixel.shatteredpixeldungeon.items.MediKit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.WildBerries;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.Tinder;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.darts.Dart;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Shrub {

    private static final String TXT_FOUND_BERRIES	= "You found some berries!";
    private static final String TXT_FOUND_NOTHING	= "You found nothing interesting.";
    private static final String TXT_FOUND_READING	= "You found %s";


    private static final String[] BOOKS = {

            // LORE 

			"some religious scripture which tells that this world was created by an almighty and mysterious entity named Watabou, but was then abandoned by Him.", 

			"a legend which tells how Watabou used a miraculous Cube to fill this world with wonderful music of the spheres, but this artifact was completely lost long ago.", 

			"some scripture which tells that this world was torn asunder into a dozens of different realities, and every single one of them was different and existed on its own.", 

			"some ancient scripture which tells that this world was, again, shaped by three lesser angels: one considers a hamster, once name was Evan, and strangely the last was a succubus named Luna",

			"a mouldy book which tells about how the first hunters, on moonlit nights, hunted the beasts on the streets to cover the dirty secrets of the Church ",

			"a leather note which tells a vague story of how a group of scholars beckon the Moon. Perhaps it was the origin of the scourge of blood and beast",

            " a half-torn recipe of some sort of concoction. The ingredients are profoundly disturbing: eyes freshly plucked, arcane haze, and warm blood... You wonder what nightmarish potion they wanted to create... ",
			// DENIZENS 

			"an old book which describes gnolls and their savage habits and rituals. Apparently, gnolls believe that they can absorb qualities of those who they are eating.",

			"a half-torn book which describes the savagery nature of the goblins. And how they kidnap cattle and maiden to eat and breed.",

			"some old booklet about the glorious technological achievements of dwarven blacksmiths and artificers. There is a blot of something what looks like dried blood on it.", 

			"a grimoire with half-mad description and mostly ramblings about the Old Gods and how they inhabit our world and our inability to conceive them", 

			"a mouldy tome which, it seems, told about the rituals to summon different creatures of magical origin - undead, golems, elementals... It is too worn out to be of any use.",

			"a strange book which depicts Dwarven ritualistic orgies and sacrifices as entertainment, in order to gratify their time-dulled senses.  ",

			"an old book which depicts strange ritual to beckon the nameless Moon Presence. It states that when the Red Moon hangs low, the line between men and beasts is blurred",

			"a well-used notebook with incomprehensible language and maddening images of strange beasts whose geometry seems all wrong. You feel uneasy and decide to put the book back to the shelf.",

			"a blood-stained scroll which summarizes the tragic fate of the city Loran, how it fell from its golden age and how its denizens slowly degenerate into flesh-hungry beasts",

			"a pristine book with golden cover, but inside you found it's just yet another book about ritualistic sacrifice for the Old Gods: Cthulhu, Shub-Nigurath, Yig... To name a few ",

			// CHARACTERS

			"a history book which tells stories of the ill-tempered, but brave and honourable people of the Thule. No other land is as famous for its warriors and sea traders.", 

			"a geography book which contains description of somewhat wild and barbaric, but cunning and mischievous tribes inhabiting vast southern deserts.",

			"a philosophy book which was written by one of the many sorcerers of the Eastern Empire, which is famous for valuing knowledge above everything else.", 

			"an autobiography book which describes lives of the people of the West - simple, but capable folk who live by the will and blessing of their druidic leaders.",

			// CREDITS 

			"an old book about Watabou the mysterious entity who was worshipped as the First World Shaper",
			
			"a tome which depicts the great deeds of Evan the Shattered, the Second World Shaper, also a great sage who worked tirelessly to improve his world. His deeds inspired many, and were adored by even more.",
			
			"a leather scroll which was written by the Third World Shapers, also a great librarian who can be considered a hamster. ",
			
			"a small hand book depicts Luna- the fourth World Shaper, also a succubus mistress who likes to pose as an angel. The Church has long claimed those who worshipped Luna heretics.",

      

            // MISC

			"scribbles of some ancient philosopher which are filled with thoughts about how this world is off its balance because it is not in its completed state yet.", 

			"a list of bizarre prophecies, which mentions dead men blowing into a horns, arrival of demons in blood-red raiments, and giant crabs in formal dresses.",

    };

	public static void examine( int cell ) {
		Hero ch = Dungeon.hero;
		if (ch != null) {
			SandalsOfNature.Naturalism naturalism = ch.buff( SandalsOfNature.Naturalism.class );
			if (naturalism != null) {
				if (!naturalism.isCursed()) {
					naturalismLevel = naturalism.itemLevel() + 1;
					naturalism.charge();
				} else {
					naturalismLevel = -1;
				}
			}
		}

          GLog.p(TXT_FOUND_BERRIES);
          int k = Random.Int(3,5);
		if (naturalismLevel >= 0) {
		k + = naturalismLevel;
		}
          WildBerries item = new WildBerries();
          item.quantity = k;
		if (item.doPickUp( Dungeon.hero )) {
			GLog.i(" You gather some berries from the shrub" );
		} else {
			Dungeon.level.drop( item, Dungeon.hero.pos ).sprite.drop();
		}
		
		

            Level.set( cell, Terrain.SHRUB );
			Dungeon.observe();
			GameScene.updateMap( cell );
			Sample.INSTANCE.play(Assets.SND_OPEN);

	}
}
