/**
 * Copyright 2010 The PlayN Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.alexkrieg.cards.java;

import de.alexkrieg.cards.mygame.MyCardGame;
import playn.core.PlayN;
import playn.java.JavaGraphics;
import playn.java.JavaPlatform;

public class CardsGameJava {

	public static void main(String[] args) {
		JavaPlatform platform = JavaPlatform.register();
		JavaGraphics graphics = (JavaGraphics) PlayN.graphics();
		int width = 1024;
		int height = 768;
		if ( args != null && args.length == 2) {
			width = Integer.parseInt(args[0]);
			height = Integer.parseInt(args[1]);
		}
		
		graphics.setSize(width, height);
		PlayN.run(new MyCardGame());

	}
}
