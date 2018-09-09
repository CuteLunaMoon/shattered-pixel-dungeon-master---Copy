package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Barkskin;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class TenderRoastedBabyCarrots extends FriedCarrot {
    {
        image = ItemSpriteSheet.BAKEDCARROT;
        energy = 275;
    }

    @Override
    public int price() {
        return 10 * quantity;
    }
    @Override
    public void execute(Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {
            GLog.i( "Your skin hardens!");
            Buff.affect( hero, Barkskin.class ).level( hero.HT / 4 );
        }
    }
}
