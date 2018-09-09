package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Drowsy;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Invisibility;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Slow;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Vertigo;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Weakness;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

public class BakedChicoryWithHerbs extends FriedChicoryRoot {
    {
        image = ItemSpriteSheet.BAKEDCHICORY;
        energy = 700;
    }

    @Override
    public int price() {
        return 75 * quantity;
    }
    @Override
    public void execute(Hero hero, String action ) {

        super.execute( hero, action );
        if (hero.HP < hero.HT) {
            hero.HP = Math.min( hero.HP + hero.HT/4, hero.HT );
            hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
        }
        if (action.equals( AC_EAT )) {
            GLog.i( "You feel refresh! Your wounds are partially healed.");
            GLog.i( Messages.get(FrozenCarpaccio.class, "refresh") );
            Buff.detach( hero, Poison.class );
            Buff.detach( hero, Cripple.class );
            Buff.detach( hero, Weakness.class );
            Buff.detach( hero, Bleeding.class );
            Buff.detach( hero, Drowsy.class );
            Buff.detach( hero, Slow.class );
            Buff.detach( hero, Vertigo.class);
        }
    }
}
