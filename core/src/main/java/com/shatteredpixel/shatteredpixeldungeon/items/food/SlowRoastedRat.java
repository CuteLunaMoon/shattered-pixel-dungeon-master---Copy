package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class SlowRoastedRat extends TailedCutlet {
    {
        image = ItemSpriteSheet.SLOWROAST;
        energy = 150;
    }
    @Override
    public void execute(Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals( AC_EAT )) {
            if (hero.HP < hero.HT) {
                hero.HP = Math.min( hero.HP + 3, hero.HT );
                hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
            }
        }
    }
    @Override
    public int price() {
        return 5 * quantity;
    }
}
