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

package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;


import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Amok;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Bat;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Skeleton;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Wraith;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.food.BurdockRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.ChicoryRoot;
import com.shatteredpixel.shatteredpixeldungeon.items.food.GiantPuffball;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Mandrake;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Truffle;
import com.shatteredpixel.shatteredpixeldungeon.items.food.WildBerries;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.SandalsOfNature;
import com.shatteredpixel.shatteredpixeldungeon.items.food.WildCarrot;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.missiles.Garlic;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
//import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

import java.util.ArrayList;

public class Shovel extends Weapon{
	public static final String AC_MINE	= "MINE";
	
	public static final float TIME_TO_MINE = 2;
	

	
	{
		image = ItemSpriteSheet.SHOVEL;
		
		unique = true;
		bones = false;
		
		defaultAction = AC_MINE;

	}
	
	public boolean bloodStained = false;

	@Override
	public int min(int lvl) {
		return 2;   //tier 2
	}

	@Override
	public int max(int lvl) {
		return 15;  //tier 2
	}

	@Override
	public int STRReq(int lvl) {
		return 14;  //tier 3
	}

	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_MINE );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {

		super.execute( hero, action );
		
		if (action.equals(AC_MINE)) {
			
		
			
			for (int i = 0; i < PathFinder.NEIGHBOURS8.length; i++) {
				
				final int pos = hero.pos + PathFinder.NEIGHBOURS8[i];
				if (Dungeon.level.map[pos] == Terrain.HIGH_GRASS) {
				
					hero.spend( TIME_TO_MINE );
					hero.busy();
					
					hero.sprite.attack( pos, new Callback() {
						
						@Override
						public void call() {

							CellEmitter.get( pos ).burst( LeafParticle.LEVEL_SPECIFIC, 15 );
							Sample.INSTANCE.play( Assets.SND_EVOKE );
							
							Level.set( pos, Terrain.GRASS );
							GameScene.updateMap( pos );


							int naturalismLevel =0;
								
									SandalsOfNature.Naturalism naturalism = hero.buff( SandalsOfNature.Naturalism.class );
										if (naturalism != null) {
											if (!naturalism.isCursed()) {
													naturalismLevel = naturalism.itemLevel() + 1;
													naturalism.charge();
												} else {
													naturalismLevel = -1;
												}
											}
							//harder to get seed than trampling grass				
							if (Random.Int(75 - ((int) (naturalismLevel * 3))) == 0) {
								Item seed = Generator.random(Generator.Category.SEED);

								Dungeon.level.drop( seed, pos ).sprite.drop();
							}
							// Wild berries
							if(Random.Int (50- naturalismLevel*3)<=3){
								Dungeon.level.drop(new WildBerries(), pos).sprite.drop();
							}
							//Puffballs
							if(Random.Int (250- naturalismLevel*3)<=3){
								Dungeon.level.drop(new GiantPuffball(), pos).sprite.drop();
							}
							
							// more chance to face skeleton underground
							if(Random.Int(100)<= 5+ (Dungeon.depth/2)){
								if(Random.Int(10)<=5){
									Skeleton skele = new Skeleton();
									skele.pos = pos;

									GameScene.add(skele, 2f);
									GLog.n(" You found a buried skeleton.");
                                }else{
									Wraith boo = new Wraith();
									boo.pos = pos;
									
									GameScene.add(boo, 1f);
									GLog.n(" Your shovel hit something hard. You feel a sudden cold: it's a human skull! A wraith materializes because of the rude awakening!");
                                }
                            }

                                if(Random.Int (40- naturalismLevel*3)<=3){
                                int ttt = Random.Int(10);
                                if(ttt>2){
                                    WildCarrot gold = new WildCarrot();
                                    if (gold.doPickUp( Dungeon.hero )) {
                                        GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
                                    } else {
                                        Dungeon.level.drop( gold, hero.pos ).sprite.drop();
                                    }
                                    }else{
                                     ChicoryRoot gold = new ChicoryRoot();
                                    if (gold.doPickUp( Dungeon.hero )) {
                                        GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
                                    } else {
                                        Dungeon.level.drop( gold, hero.pos ).sprite.drop();
                                    }
                                    
                                    }
                                }else{
                                    if(Random.Int (100- naturalismLevel*3)<=3){
                                    int ttt =Random.Int(10);
										if(ttt>2)                {
                                        BurdockRoot gold = new BurdockRoot();
                                        if (gold.doPickUp( Dungeon.hero )) {
                                            GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
                                        } else {
                                            Dungeon.level.drop( gold, hero.pos ).sprite.drop();
                                        }
                                        }else{
											Garlic gold = new Garlic();
											if (gold.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
											} else {
												Dungeon.level.drop( gold, hero.pos ).sprite.drop();
											}
                                        }
                                    }else{
										if(Random.Int(100)<=5){
										int ccc= Random.Int(5);
										if(ccc>=3){
											Truffle gold = new Truffle();
											if (gold.doPickUp( Dungeon.hero )) {
												GLog.i( Messages.get(Dungeon.hero, "you_now_have", gold.name()) );
											} else {
												Dungeon.level.drop( gold, hero.pos ).sprite.drop();
											}
                                        }else{
											GLog.n(" You found a mandrake. Its terrifying screech pierces your ears! You are bleeding and  poisoned!");
											Buff.affect( hero, Poison.class ).set( hero.HT /6 );
											Buff.affect(hero, Bleeding.class).set(3);
											CellEmitter.center( pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
											for (Mob mob : Dungeon.level.mobs.toArray( new Mob[0] )) {
												mob.beckon( curUser.pos );
												if (Dungeon.level.heroFOV[mob.pos]) {
													Buff.prolong(mob, Amok.class, 5f);
												}
											}
											Sample.INSTANCE.play( Assets.SND_ALERT );
											Mandrake drake = new Mandrake();
											Dungeon.level.drop( drake, pos ).sprite.drop();
                                        }
                                        
										}else{
										    GLog.i("You found nothing useful underground");
										}
                                    }
                                }

							hero.onOperateComplete();
						}
					} );
					
					return;
				}
			}
			
			GLog.w( "NO high grass nearby" );
			
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public int proc( Char attacker, Char defender, int damage ) {
		if ( defender instanceof Bat && (defender.HP <= damage)) {
		
			updateQuickslot();
		}
		return damage;
	}

	/*
	private static final String BLOODSTAINED = "bloodStained";
	
	@Override
	public void storeInBundle( Bundle bundle ) {
		super.storeInBundle( bundle );
		
	//	bundle.put( BLOODSTAINED, bloodStained );
	}
	*/
	/*
	@Override
	public void restoreFromBundle( Bundle bundle ) {
		super.restoreFromBundle( bundle );
		
	//	bloodStained = bundle.getBoolean( BLOODSTAINED );
	}
	*/
	
}