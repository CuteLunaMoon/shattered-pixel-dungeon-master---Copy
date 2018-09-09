package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class Arbalest extends Crossbow {

    public class Crossbow extends MeleeWeapon {


        protected boolean heavy = true;

        {
            image = ItemSpriteSheet.CROSSBOW;

            //check Dart.class for additional properties

            tier = 5;
        }

        @Override
        public int max(int lvl) {
            return 2 * (tier) +    // 8 damage max
                    lvl;     //+1 per level
        }
    }
}
