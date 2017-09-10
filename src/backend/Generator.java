package backend;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;

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
//Generations
//SM Promos end with SM##


//sets with ptcgo-1.png suffix
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

//known issues: 
//Mr. Mime, Mr. Fuji, Mr. Briney, Mr. Stone don't have the period in the card url but do in the image url --fixed
//gym heroes images don't have the g1 abbreviation on them -- fixed
//gym challenge images don't have the g2 abbreviation on them -- fixed
//neo genesis images don't have the n1 abbreviation on them -- fixed
//neo discovery images don't have the n2 abbreviation on them -- fixed
//neo revelation images don't have the n3 abbreviation on them -- fixed
//neo destiny images don't have the n4 abbreviation on them -- fixed
//some delta species images have an added adjective after the pokemon name and before the set name (starts with EX Delta Species) --fixed
//see: https://bulbapedia.bulbagarden.net/wiki/%CE%94_Delta_Species_(TCG)
//EX Delta Species has -d-
//EX Holon Phantoms uses -delta-
//EX Crystal Guardians uses -delta-
//pop series uses -delta-
//EX Dragon Frontiers uses -delta-
//EX delta species still have -delta-, but Star delta do not
//Lv.X cards have lv-x on card page and lv.x on image in DPPromos, DP, MT, SW, GE, MD, LA, SF, PL, RR, SV, AR, 
//Trainer cards with + in SF don't have + in page but have + in image
//World Collection has language on image but number on page
//HeartGold SoulSilver end with -ptcgo-#.png, but images without that ending that are .jpg exist
//also, Pokegear 3.0 has issues
//Mime Jr. has a decimal
//bw promos victory cups are image/page inconsistent and don't end with the -1.jpg suffix
//Pokemon Catcher in EPO has an even more messed suffix
//Exp. share in NXD
//Exp. share in DRV
//Mr. Mime in PLF
//BREAK cards are rotated and don't show up on the card page. have -rotated.jpg as well (XY Promo, BREAKthrough, BREAKpoint, Fates Collide, Steam Siege (with no suffix), Evolutions (with horizontal.jpg suffix
//FFI secret rares are listed as rare holos?
//Mr. Mime in Jungle
//PCR/PCL suffix conflict in Primal Clash
//Mr. Mime in Primal Clash
//Trainer's Mail 92a in Roaring Skies has a bad picture
//fates collide suffix is fco on page and fcl on image
//Mr. Mime in Fates Collide
//sun and moon promo images don't have the set "sun-moon-promos-", just "promo-" Wait, sun and moon promos are just all over the place and inconsistent gross. 
//sun and moon images don't ahev their sum abbreviation
//sun and moon energies are weird? psychic has a gold nameplate and is secret rare, the rest don't have numbers? secret rare energies with gold nameplates in other sets
//guardians rising images don't have the gri abbreviation in their file names
//Victory Medals 
//x+y images have a ptcgo abbreviation in their file name
//fates collide 

//HS introduced LEGEND 2 part cards that look sick and Alph Lithographs

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



	public BufferedImage getCard() {
		try {
			//load a card
			BufferedImage cardy = ImageIO.read(urls[69]);
			return cardy;
		} catch (IOException e) {
			System.out.println("Couldn't load image at "+urls[69]);
		}
		return null;
	}

}
