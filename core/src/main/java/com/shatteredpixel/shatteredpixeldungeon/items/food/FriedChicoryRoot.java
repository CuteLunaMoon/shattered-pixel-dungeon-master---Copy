package com.shatteredpixel.shatteredpixeldungeon.items.food;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class FriedChicoryRoot extends  Food {
    {
        image = ItemSpriteSheet.BAKEDCHICORY;
        energy = 700;
    }

    @Override
    public int price() {
        return 25 * quantity;
    }

    public static Food cook( ChicoryRoot ingredient ) {
        FriedChicoryRoot result = new FriedChicoryRoot();
        result.quantity = ingredient.quantity();
        return result;
    }
}
