package backend;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javax.imageio.ImageIO;
import static java.nio.file.StandardOpenOption.*;

/*
//Adding new cards:
//1. Use Chrome to download all urls on a page to a txt file, copy and paste the new card pages to cards.txt
//2. Copy the new card urls to filler.txt
//3. Using Find/Replace, Find: /card/ Replace: /wp-content/uploads/
//4. Using Find/Replace, Find: (\d)/ Replace: $1.jpg

//sets with no abbreviations:
//Victory Medals
//Nintendo Black Star Promos
//Best of Game
//Wizards Black Star Promos
//POP Series 1-8
//Diamond & PEarl Promos end with DP##
//HeartGold SoulSilver Promos end with HGSS##
//Black & White Promos end with bw##
//XY Promos end with XY##
//Furious Fists images end with frf instead of ffi
//Generations does not end with -gen, just -#.jpg (EXCEPT Jolteon-EX GEN28a)
//Fates Collide images end with fcl instead of fco (EXCEPT Zygarde-EX FCO54a) 
//SM Promos all goofy end with SM##
//Sun & Moon: name-sun-moon-#.jpg
//Guardian Rising does not end with -gri, just -#.jpg
//Shining Legends ends with shl instead of slg
//Crimson Invasion does not end with -cri, just .jpg


//known issues: 
//Mr. Mime, Mr. Fuji, Mr. Briney, Mr. Stone, Lt. Surge, EXP.ALL, Mt. Moon, Prof. Oak's Research, Rocket's Admin., Mime Jr., Pichu Bros. don't have the period in the card url but do in the image url --fixed
//gym heroes images don't have the g1 abbreviation on them -- fixed
//gym challenge images don't have the g2 abbreviation on them -- fixed
//gym challenge Lt. Surge's jolteon starts with lit. instead of lt. -- fixed
//neo genesis images don't have the n1 abbreviation on them -- fixed
//neo discovery images don't have the n2 abbreviation on them -- fixed
//neo revelation images don't have the n3 abbreviation on them -- fixed
//neo destiny images don't have the n4 abbreviation on them -- fixed
//neo destiny dark exeggutor has a typo on card. image follows typo, page does not -- fixed
//neo destiny thought wave machine has though wave machine url -- fixed
//skyridge venonat has venomoth image url -- fixed
//firered leafgreen drowzee has a typo on card. image follows typo, page does not -- fixed
//EX UnseenForces unown question-mark and exclamation point(-.jpg) -- fixed
//Legend Maker Lunatone image url says lanturn instead of lunatone -- fixed
//EX Holon Phantom Mightyena ex image url is misspelled mighyena-ex
//EX Holon Water Energy image url is misspelled grass-energy -- fixed 
//Diamond Pearl Unown C image url says call, its Poke-power instead of unown-c -- fixed
//Mysterious Treasures Nidoran Female image url says male -- fixed
//Secret Wonders Nidoran Male image url says female --fixed
//Majestic Dawn Pachirisu iamge url is totally messed up -- fixed
//Legends Awakened Forretress image url is misspelled forrestress- -- fixed
//Legends Awakened unown question-mark -- fixed
//Stormfront Poke blower +, Poke Drawer +, and Poke Healer + don't have + in page url but do in image url -- fixed
//Platinum Purugly G image url is missing the g -- fixed
//Platinum Pokedex HANDY910is image url is misspelled -handy901is -- fixed
//Rising Rivals Rapidash 4, Flareon 4 image url is missing -4- -- fixed
//Arceus Arceus have the type listed in the image url -- fixed
//World Collection Pikachu image urls end with language instead of their number -- fixed
//McDonald's Collection 2011 image urls become mcdonalds-promos -- fixed
//BW promos victory cups are image/page inconsistent and don't end with the -1.jpg suffix
//Plasma Blast Virizion image url is misspelled Viriziong -- fixed
//XY Promos Greninja image url suffix is messed up: https://pkmncards.com/wp-content/uploads/greninja-xy-promos24.jpg -- fixed
//XY Promos Kingdra image url is xy-39-1.jpg -- fixed
//XY Promos Meoletta XY120 image url misspelled Meolette -- fixed
//XY Arbox XY48 image url is misspelled arbox -- fixed
//XY Cassius XY115 image url is misspelled cassisu -- fixed
//Furious Fists M Lucario-EX frf55a ends .jpg -- fixed
//EPO Pokemon Catcher EPO95 has an even more messed suffix 
//FFI secret rares are listed as rare holos?
//Furious Fists image url suffix is frf instead of ffi -- fixed
//Primal Clash image url is pcl instead of prc -- fixed
//Primal Clash Tangrowth PRC5 image url is misspelled trangrowth -- fixed 
//Roaring Skies Trainer's Mail 92a has a bad picture
//Generations Venasaur-EX GEN1 image url is missing the -EX -- fixed
//Generations RC cards have a capital -RC# instead of -rc# -- fixed
//Fates Collide image url is fcl instead of fco -- fixed (EXCEPT Zygarde-EX FCO54a)
//Fates Collide Whismur FCO80 image url is misspelled Whimsur -- fixed
//Steam Siege Magearna-EX STS75 image url is missing the -EX -- fixed
//Evolutions Venusaur-EX EVO1 image url is misspelled venusuar-ex -- fixed
//Sun & Moon Promos Jangmo-o SM40 ard url is misspelled janmgo-o -- fixed
//Sun & Moon Promos SM61 89, 90, 92-97 are MIA
//sun and moon energies are weird? psychic has a gold nameplate and is secret rare, the rest don't have numbers? secret rare energies with gold nameplates in other sets
//Guardians Rising Barboach GRI70 image url is misspelled barboarch -- fixed
//Burning Shadows uses ## format - so card 1 - 9 need to be 01, etc. -- fixed
//EXCEPT Burning Shadows Vileplume BUS06, which has a really bad -6.jpg picture as well
//Burning Shadows Electabuzz BUS42 image url is misspelled elecabuzz -- fixed
//Burning Shadows Eelektrik BUS45 image url is misspelled elektrik -- fixed
//Burning Shadows Eelektross BUS46 image url is misspelled elektross -- fixed
//Burning Shadows Scolipede BUS58 image url is misspelled scolipeded -- fixed 
//Burning Shadows Alolan Muk-GX BUS84 image url is missing the -gx -- fixed
//Burning Shadows Malamar BUS90 image url is misspelled malmar -- fixed
//Burning Shadows Cutiefly BUS95 image url is misspelled cuteifly -- fixed
//Burning Shadows Porygon2 BUS104 image url is misspelled porygon-2 --fixed
//Burning Shadows Bodybuilding Dumbbells BUS113 image url is misspelled bodybuilding-dumbells -- fixed
//Burning Shadows Alolan Muk-GX BUS138 image url is missing the -gx -- fixed
//Shining Legends format is all unique: ###-name-shining-legends-shl.jpg
//Find: /uploads/(.+)-slg-(\d+)
//Replace: /uploads/0$2-$1-shl
//Manually insert extra 0 for 1-9
//Crimson Invasion format is all unique: ###-name-crimson-invasion.jpg
//Find: /uploads/(.+)-cri-(\d+)
//Replace: /uploads/0$2-$1
//Manually insert extra 0 for 1-9, remove extra 0 for 100-124
//Victory Medals format is all unique: victory-medal-season-battle-road-####-####.jpg
//Best of Game Rocket's Mewtwo and Rocket's Hitmonchan have -winner added to their image urls
//


////////////////////////////////////////////////////////
//some delta species images have an added adjective after the pokemon name and before the set name (starts with EX Delta Species) --fixed
//see: https://bulbapedia.bulbagarden.net/wiki/%CE%94_Delta_Species_(TCG)
////////////////////////////////////////////////////////
//delta species -- fixed 
//Bagon delta no 57. doesn't have the -d- in its image url --fixed
//ex-delta species -- fixed
//star delta species -- didn't have a delta addition, never an issue
//EX Delta Species has -d- -- fixed
//EX Legend Maker Pikachu has -delta- -- fixed
//EX Holon Phantoms uses -delta- -- fixed
//EX Holon Phantom Nosepass No. 25 incorrectly has a -delta- added, it's not a delta species
//EX Holon Phantom Trapinch No 85 incorrectly lacks -delta-, it is a delta species
//EX Crystal Guardians uses -delta- -- fixed
//pop series uses -delta- -- fixed 
//EX Dragon Frontiers uses -delta- -- fixed
////////////////////////////////////////////////////////

////////////////////////////////////////////////////////
//Lv.X cards have lv-x on card page and lv.x on image in:
//DPPromos -- fixed
//DP -- fixed
//MT -- fixed
//SW -- fixed
//GE -- fixed
//MD -- fixed
//LA -- fixed
//SF -- fixed
//PL -- fixed
//RR -- fixed
//SV -- fixed
//AR -- fixed 
////////////////////////////////////////////////////////

////////////////////////////////////////////////////////
//sets with ptcgo-1.png suffix
////////////////////////////////////////////////////////
//HS
//UL
//UD
//TM
//CL
//some BLW promos end with -1.jpg some, end without either suffix
//EPO, with at least one exception in Pokemon Catcher
//NVI
//NXD
//DEX, with pokemon catcher exception
//DRX
//DRV
//BCR (also the skyla 149 trainer card has a typo)
//PLS
//PLF
//PLB
//LTR
//KSS
//XY
//FLF
//FFI
////////////////////////////////////////////////////////

////////////////////////////////////////////////////////
//Cards ending with -ptcgo-#.png and no alternative: (NOTE: pngs are much higher quality, should probably replace jpgs where possible)
////////////////////////////////////////////////////////
//HeartGold SoulSilver Unown 54 and 55 -- fixed
//HeartGold SoulSilver Pokegear 3.0 (also has a decimal in it) --fixed
//HeartGold SoulSilver Ho-oh LEGEND (111 and 112) -- fixed
//HeartGold SoulSilver Lugia LEGEND (113 and 114) -- fixed
//HeartGold SoulSilver Gyrados (123) -- fixed
//Unleashed Steelix Prime (87) -- fixed
//Unleashed Tyranitar Prime (88) -- fixed
//Unleashed Ursaring Prime (89) -- fixed
//Unleashed Entei & Raikou LEGEND (090 and 91) (note the three digits too!) -- fixed
//Unleashed Raikou & Suicune LEGEND (92 and 93) (93 ends in -2.png!) -- fixed
//Unleashed Suicune & Entei LEGEND (94 and 95) (95 ends in -2.png!) -- fixed
//Undaunted Unown 40 -- fixed
//Undaunted Team rocket's Trickery 78 -- fixed
//Undaunted Raichu Prime 83 -- fixed
//Undaunted Scizor Prime 84 -- fixed
//Undaunted Slowking Prime 85 -- fixed
//Undaunted Umbreon Prime 86 -- fixed
//Undaunted Kyogre & Groudon LEGEND (87 and 88) -- fixed
//Undaunted Rayquaza & Deoxys LEGEND (89 and 90) -- fixed
//Triumphant Unown 51 -- fixed
//Triumphant Darkrai & Cresselia LEGEND (99 and 100) -- fixed
//Triumphant Palkia & Dialga LEGEND (101 and 102) -- fixed
//Black and White Promos Kyurem BW44 -- fixed
//Black and White Promos Keldeo EX BW61 -- fixed
//Black and White Promos Black Kyurem EX BW62 -- fixed
//Black and White Promos White Kyurem EX BW63 -- fixed
//Black and White Promos Meloetta BW68 -- fixed
//Black and White Promos Meloetta BW69 -- fixed
//Black and White Promos Darkrai BW73 -- fixed
//Black and White Promos Giratina BW74 -- fixed
//Black and White Promos Electrode BW76 -- fixed
//Black and White Promos Landorus BW79 -- fixed
//Black and White Promos Tornadus EX BW96 -- fixed
//Black and White Promos Tornadus EX BW96 -- fixed
//Black and White Promos Genesect BW99 -- fixed
//Noble Victories Virizion NVI97 -- fixed
//Noble Victories Victini NVI98 -- fixed
//Noble Victories Terrakion NVI99 -- fixed
//Noble Victories Cobalion NVI100 -- fixed
//Noble Victories N NVI101 -- fixed
//Next Destinies Shaymin-EX NXD94 -- fixed
//Next Destinies Reshiram-EX NXD95 -- fixed
//Next Destinies Kyurem-EX NXD96 -- fixed
//Next Destinies Zekrom-EX NXD97 -- fixed
//Next Destinies Mewtwo-EX NXD98 -- fixed
//Next Destinies Regigigas-EX NXD99 -- fixed
//Dark Explorers Kyogre-EX DEX26 -- fixed
//Dragons Exalted Rayquaza-EX DRX85 -- fixed
//Dragons Exalted Rayquaza-EX DRX123 -- fixed
//Boundaries Crossed Celebi-EX BCR141 -- fixed
//Boundaries Crossed Keldeo-EX BCR142 -- fixed
//Boundaries Crossed Cresselia-EX BCR143 -- fixed
//Boundaries Crossed Landorus-EX BCR144 -- fixed
//Boundaries Crossed Black Kyurem-EX BCR145 -- fixed
//Boundaries Crossed White Kyurem-EX BCR146 -- fixed
//Boundaries Crossed Bianca BCR147 -- fixed
//Boundaries Crossed Cheren BCR148 -- fixed
//Boundaries Crossed Skyla-EX BCR149 -- fixed
//Plasma Storm Victini-EX PLS131 -- fixed
//Plasma Storm Articuno-EX PLS132 -- fixed
//Plasma Storm Cobalion-EX PLS133 -- fixed
//Plasma Storm Lugia-EX PLS134 -- fixed
//Plasma Storm Colress PLS135 -- fixed
//Plasma Blast Abomasnow PLB26 -- fixed
//Plasma Blast Exeggcute PLB102 -- fixed
//Plasma Blast Virizion PLB103 -- fixed
//Legendary Treasures Trubbish LTR67 -- fixed
//XY Promos Xerneas-EX XY07 -- fixed
//XY Promos Yveltal-EX XY08 -- fixed
//XY Promos Garchomp-EX XY09 -- fixed
//XY Promos Dragalge XY10 -- fixed
//XY Promos Machamp XY13 -- fixed
//XY Promos Charizard-EX XY17 -- fixed
//XY Promos Chesnaught-EX XY18 -- fixed
//XY Promos Delphox-EX XY19 -- fixed
//XY Promos Greninja-EX XY20 -- fixed
//XY Promos Krookodile-EX XY25 -- fixed
//XY Promos Pyroar XY26 -- fixed
//XY Promos Metagross-EX XY34 -- fixed (ends in -ptcgo.jpg!)
//XY Promos XY35 M Metagross-EX -- fixed (ends in -ptcgo.jpg!)
//XY Promos XY36 Treecko -- fixed (ends in -ptcgo.jpg!)
//XY Promos XY37 Torchic -- fixed (ends in -ptcgo.jpg!)
//XY Promos XY38 Mudkip -- fixed (ends in -ptcgo.jpg!)
//All of Kalos Starter Set -- fixed 
//All of XY -- fixed
//All of Flashfire -- fixed
//All of Furious Fists -- fixed (except M Lucario-EX 55a)
////////////////////////////////////////////////////////

////////////////////////////////////////////////////////
//BREAK cards: don't load on card page, have -rotated.jpg and .jpg versions
//Clicking the link on the card page leads to -rotated.jpg, may be why they don't load properly
//XY Promos
//BREAKthrough 
//BREAKpoint
//Fates Collide
//Steam Siege
//Evolutions
////////////////////////////////////////////////////////

////////////////////////////////////////////////////////
//Sun & Moon Promos are all goofy
////////////////////////////////////////////////////////
//name-promo-sm##.jpg
//SM01 Rowlet
//SM02 Litten
//SM03 Popplio
//SM05 Snorlax-GX
////////////////////////////////////////////////////////
//name-sun-moon-promos-sm##.jpg
//SM04 Pikachu
//SM14 Lycanroc-GX (EXCEPT ITS ACTUALLY -sun-moons-promos
//SM16 Solgaleo-GX
//SM17 Lunala-GX
//SM18 Alolan Sandslash
//SM19 Oricorio
//SM20 Mudsdale
//SM21 Drampa
//SM22 Rowlet
//SM23 Litten
//SM24 Popplio
//SM25 Lurantis
//SM26 Tsareena
//SM27 Turtonator
//SM28 Vikavolt
//SM29 Mimikyu
//SM30 Tapu Koko
//SM31 Tapu Koko
//SM32 Tapu Bulu-GX
//SM33 Tapu Koko-GX
//SM34 Bewear-GX
//SM35 Espeon-GX
//SM36 Umbreon-GX
//SM37 Decidueye-GX
//SM38 Incineroar-GX
//SM39 Primarina-GX
//SM40 Jangmo-o
//SM41 Komala
//SM42 Cosmog
//SM43 Alolan Meowth
//SM44 Togedemaru
//SM45 Tapu Lele
//SM46 Seviper
//SM47 Crabominable
//SM48 Zygarde
//SM49 Bewear
//SM50 Tapu Koko-GX
//SM51 Alolan Meowth
//SM56 Tsareena-GX 
////////////////////////////////////////////////////////
//name-sun-moon-sm##.jpg
//SM06 Rockruff
//SM07 Pikipek
//SM08 Litten
//SM09 Togedemaru
//SM10 Shiinotic
//SM11 Bruxish
//SM12 Passimian
//SM13 Oranguru
//SM15 Zygarde
////////////////////////////////////////////////////////
//###-name-sun-moon-promos-sm##.jpg
//Find: /card/(.+)-sm(\d+)
//Replace: /card/0$2-$1-sm$2
//SM52 Golisopod
//SM53 Dhelmise
//SM54 Lucario
//SM55 Decidueye
//SM57 Ho-oh-GX
//SM58 Necrozma-GX
//SM59 Marshadow-GX
//SM60 Charizard-GX
//SM62 Golisopod-GX
//SM63 Salazzle-GX
//SM64 Silvally
//SM65 Alolan Raichu
//SM66 Pheromosa-GX
//SM67 Celesteela-GX
//SM68 Xurkitree-GX
//SM69 Buzzwole-GX
//SM70 Shining-Ho-Oh
//SM71 Kommo-o-GX
//SM72 Alolan Raichu
//SM73 Salazzle
//SM74 Regirock
//SM75 Registeel
//SM76 Pikachu
//SM77 Mewtwo
//SM78 Champions Festival
//SM79 Shining Celebi
//SM80 Ho-oh-GX
//SM81 Pikachu
//SM82 Shining Lugia
//SM83 Zorua
//SM84 Zoroark-GX
//SM85 Marshadow
//SM86 Pikachu
//SM87 Latias
//SM88 Latios
//SM91 Silvally-GX
//SM98 Pikachu
//SM99 Mimikyu
////////////////////////////////////////////////////////

//HS introduced LEGEND 2 part cards that look sick and Alph Lithographs
*/
public class Generator {
	BufferedImage template= null;
	URL[] urls = new URL[10000];
	int numCards = 0;

	public Generator() {
		//Set up array of URLs 
		String line = null;
		try (
				InputStream is = Generator.class.getResourceAsStream("/res/cards/cardimages.txt");
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader ready = new BufferedReader(isr);
				){
			while((line = ready.readLine()) != null) {
				urls[numCards] = new URL(line);
				numCards++;
			}
		} catch (MalformedURLException e) {
			System.out.println("Error creating a URL from String "+ line);
		} catch (IOException e) {
			System.out.println("Error reading a line in the BufferedReader. /n Last read line was: "+urls[numCards]);
			System.out.println("");
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Last line read was: "+urls[numCards]);
			System.out.println(numCards);
			System.out.println("Error loading the cards.txt file or the BufferedReader went on for too long.");
		}
	}
	public BufferedImage generate(int templatenum) {
		//add threads?
		
		//load template
		try { 
			template = ImageIO.read(Generator.class.getResourceAsStream("/res/templates/template"+templatenum+".png"));
		}catch(IOException e){
			System.out.println("Couldn't load the template you requested.");
		}catch(IllegalArgumentException f) {
			System.out.println("I don't even know.");
		}
		//create wallpaper
		Graphics2D wally = template.createGraphics();

		//Choose cards and draw them 
		Random rando = new Random();
		for( int j = 0; j < 3; j++){
			int selecty = rando.nextInt(numCards);
			try {
				//load a card
				BufferedImage cardy = ImageIO.read(urls[selecty]);
				//draw the card
				wally.drawImage(cardy, (24+(624*j)), 128, 600, 825, null);
			} catch (IOException e) {
				System.out.println("Couldn't load image at "+urls[selecty]);
			}
		}
		wally.finalize();
		return template;
	}

	public void drawCard(int cardNumber, Graphics2D wallpaper) {
		
	}

	public BufferedImage getCard(int choice) {
		try {
			//load a card
			BufferedImage cardy = ImageIO.read(urls[choice]);
			return cardy;
		} catch (IOException e) {
			System.out.println("Couldn't load image at "+urls[choice]);
		}
		return null;
	}
	private void reportError() {
		//Use JavaMail to email myself a error log?
	}
	public void urlTest(int start, int end) {
		//Note: Card url n in the array corresponds to line n+1 in the .txt file
		HttpURLConnection test = null;
		BufferedWriter writey = null;
		Path pathy = Paths.get("src/res/cards/errors.txt");
		try {
			writey = new BufferedWriter(Files.newBufferedWriter(pathy, CREATE, APPEND));
		} catch (IOException e1) {
			System.out.println("Couldn't create BufferedWriter.");
		}
		for(/*URL card: urls*/int k = start; k < end; k++) {
			try {
				test = (HttpURLConnection) /*card*/urls[k].openConnection();
			} catch (IOException e) {
				System.out.println("Uhh");
				e.printStackTrace();
			}
			try {
				test.setRequestMethod("HEAD");
			} catch (ProtocolException e) {
				System.out.println("Broke at setting request method to GET.");
				e.printStackTrace();
			}
			try {
				test.connect();
			} catch (IOException e) {
				System.out.println("Failed to connect.");
				e.printStackTrace();
			}
			try {
				int responseCode = test.getResponseCode();
				if(responseCode != 200) {
					System.out.println(responseCode);
					writey.write(responseCode + " " + /*card*/urls[k]);
					writey.newLine();
				}
				else {
					//Note: Card url n in the array corresponds to line n+1 in the .txt file
					System.out.println("Card url " + k + " worked");
				}
			} catch (IOException e) {
				System.out.println("Couldn't get response code.");
				e.printStackTrace();
			}
		}
		try {
			writey.flush();
			writey.close();
		} catch (IOException e) {
			System.out.println("Couldn't close writey?");
			e.printStackTrace();
		}
	}
	public BufferedImage generateTest(int templateNum) {

		try { 
			template = ImageIO.read(Generator.class.getResourceAsStream("/res/templates/template"+templateNum+".png"));
		}catch(IOException e){
			System.out.println("Couldn't load the template you requested.");
		}catch(IllegalArgumentException f) {
			System.out.println("I don't even know.");
		}
		//create wallpaper
		Graphics2D wally = template.createGraphics();
		// place cards on the template
		for( int j = 0; j < 3; j++){
			try {
				//load a card
				BufferedImage cardy = ImageIO.read(Generator.class.getResourceAsStream("/res/cards/alakazam-base-set-bs-1.jpg"));
				//draw the card
				wally.drawImage(cardy, (24+(624*j)), 128, 600, 825, null);
			} catch (IOException e) {
				System.out.println("Couldn't load image");
			}
		}
		wally.finalize();
		return template;
	}
}
