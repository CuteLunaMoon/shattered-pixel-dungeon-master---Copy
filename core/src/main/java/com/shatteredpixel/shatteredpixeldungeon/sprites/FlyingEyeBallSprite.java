/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Eye;
import com.shatteredpixel.shatteredpixeldungeon.tiles.DungeonTilemap;
import com.watabou.noosa.TextureFilm;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FlyingEyeBall;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.Beam;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.watabou.noosa.particles.Emitter;

public class FlyingEyeBallSprite extends MobSprite {
	
	
	private int zapPos;

	private Animation charging;
	private Emitter chargeParticles;
	
	
	public FlyingEyeBallSprite() {
		super();
		
		texture( Assets.EYE );
		
		TextureFilm frames = new TextureFilm( texture, 20, 16 );
		
		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1 );
		
		run = new Animation( 12, true );
		run.frames( frames, 0, 1 );
		
		attack = new Animation( 12, false );
		attack.frames( frames, 2, 3, 0, 1 );
		
		die = new Animation( 12, false );
		die.frames( frames, 4, 5, 6 );
		
		play( idle );
		
		charging = new Animation( 12, true);
		charging.frames( frames, 2, 3, 0, 1 );
		
//		chargeParticles = centerEmitter();
//		chargeParticles.autoKill = false;
//		chargeParticles.pour(MagicMissile.MagicParticle.ATTRACTING, 0.05f);
//		chargeParticles.on = false;
	}
	

	@Override
	public void update() {
		super.update();
	//	chargeParticles.pos(center());
//		chargeParticles.visible = visible;
	}

	public void charge( int pos ){
	//	turnTo(ch.pos, pos);
	//	play(charging);
	}
	
		@Override
	public void onComplete( Animation anim ) {
		super.onComplete( anim );

	}
	
}
