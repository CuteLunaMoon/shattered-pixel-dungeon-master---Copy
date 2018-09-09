package com.shatteredpixel.shatteredpixeldungeon.items.food;


import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class SpicedSauteeBurdockRoot extends FriedBurdockRoot {
    {
        image = ItemSpriteSheet.BAKEDROOT;
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
            GLog.i( "Your hand turns invisible!");
            Buff.affect( hero, Invisibility.class, Invisibility.DURATION );
        }
    }
}
