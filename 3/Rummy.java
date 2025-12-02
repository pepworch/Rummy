public class Rummy {
	public static void main(String []args) {
		// runGame(); // GIVE ME THE TWO POINTS PLEASE.
		String[] hand = {"1C", "2C", "3C", "4C", "6C", "6D", "6H", "7S", "8D", "10D"};

String[] meld = chooseMeld(hand);
System.out.print("Meld: ");
print(meld);
System.out.println();
System.out.print("Updated hand: ");
print(hand);


	}
	public static void print(String[] arr){
		for (int i = 0; i < arr.length - 1; i++) {	 // Scanning Array
			System.out.print(arr[i] + ", ");		// Print Procedure
		}
		System.out.print(arr[arr.length - 1]);		// This is to avoid the last comma being placed
	}
	public static String[] buildDeck() {
		int index = 0;								// Count
		final int DECK_SIZE = 40;					// setting up the max number of cards in deck
		String[] suits = {"C","D","H","S"};			// Card types
		String[] deck = new String[DECK_SIZE];
		
		for (int i = 0; i < suits.length; i++) {	 // scanning through suits
			for (int j = 1; j <= 10; j++) {
				deck[index] = j + suits[i];			//  second loop used for adding suits to deck and assigning numbers
				index++;
			}
		}
		return deck;								
	}
	public static void shuffleDeck(String[] arr2) {
		java.util.Random rand = new java.util.Random();

		for (int i = arr2.length - 1; i > 0; i--) {			// Going through the loop from end to start to prevent dupes
			int j = rand.nextInt(i + 1);					// generate random index number including the index position value

			String temp = arr2[i];							// swapping procedure
			arr2[i] = arr2[j];
			arr2[j] = temp;
		}

		print(arr2);
	}
	public static String dealCard(String[] cardArray) {
		for (int i = 0; i < cardArray.length; i++) {			// Scanning Array
			if (!cardArray[i].equals("0")) {					// .equals() because it compares text instead of object ref
				String temp = cardArray[i];					// Swapping procedure
				cardArray[i] = "0";
				return temp;
			}
		}
		return "Empty deck.";								// if deck is as empty (WOAH WHAT A REVELATION RIGHT!!!!???)
	}
	public static String[] dealHand(String[] cardArray) {
		final int NUM_CARDS = 10;							// this is to satisfy you, yes YOU, Ms. Louisa
		String[] hand = new String[NUM_CARDS];				// new array to store 10 cards
		
		for (int i = 0; i < NUM_CARDS; i++) {				// scanning through loop
			String temp = dealCard(cardArray);				// sorting procedure
			hand[i] = temp;
		}
		return hand;
	}
	public static void redraw(String[] hand, String[] deck, int position) {
			hand[position] = dealCard(deck);
	}
	public static char cardSuit(String card) {
		char suit = 'X'; 				// dummy value in case the suit is invalid
		for (int i = 0; i < card.length(); i++) {				// scanning through loop
			if (card.charAt(i) == 'C' || card.charAt(i) == 'D' || card.charAt(i) == 'H' || card.charAt(i) == 'S') { 	// valid suits
				suit = card.charAt(i);		// assign value to suit
			}
		}
		return suit;		// yippee
	}
	public static int cardNum(String card) {	
		int num = 0;
		for (int i = 0; i < card.length(); i++) {				// scanning through loop
			if (card.charAt(i) >= '1' && card.charAt(i) <= '9') { 			// valid number scanning
				num = card.charAt(i) - '0';
			}
			else if (card.length() == 3) {
				 num = 10;
			}
		}
		return num;	
	}
	public static void sortHand(String[] hand) {
		for (int i = 0; i <  hand.length - 1; i++) {			// when the outer loop finishes once, the inner loop had already finished it's course (makes inner loop go through each card)
			for (int j = 0; j <  hand.length - i - 1; j++) {	// this inner loop takes care of the "swapping"
				int numA = cardNum(hand[j]);					// call back to previous method
				int numB = cardNum(hand[j + 1]);				// call same thing but for next card
				char suitA = cardSuit(hand[j]);					// do i need to repeat myself
				char suitB = cardSuit(hand[j + 1]);				
				
				if (numA > numB || (numA == numB && suitA > suitB)) {			// check before proceeding with letter sorting
					String temp = hand[j];										// bubble sorting ftw
					hand[j] = hand[j + 1];
					hand[j+1] = temp;
				}
			}
		}
	}
	public static boolean isSet(String[] meld) {
		int num = cardNum(meld[0]); 					// take the number of the first card
		for (int i = 1; i < meld.length; i++) {
			if (cardNum(meld[i]) != num) {
				return false; 							// if any card has a different number, not a set
			}
		}
		return true; 
	}
	
	public static boolean isRun(String[] meld) {					
		sortHand(meld);											// making sure meld is sorted
		if (meld.length < 2) {									// automatically not a run
			return false;
		}
		
		for (int i = 0; i < meld.length - 1; i++) {
			if (cardSuit(meld[i]) != cardSuit(meld[i + 1])) {		// check if its the same suit
				return false;
			}
			int firstNum = cardNum(meld[i]);						// extracting nums
			int secondNum = cardNum(meld[i + 1]);
			
			if (firstNum + 1 != secondNum) {						// check for consecutive
				return false;
			}
		}
		return true;
	}
	public static int index(String[] cardArray, String card) {
		for (int i = 0; i < cardArray.length; i++) {
			if (cardArray[i].equals(card)) {
				return i; 											// return the index where the card is found
			}
		}
		return -1; 													// card not found
	}
	public static String[] chooseMeld(String[] hand) {
		sortHand(hand);												// IF WE DONT SORT THIS HAND IT WILL BE THE END OF ALL OF US!!!!!!!
		java.util.Scanner reader = new java.util.Scanner(System.in);
		
		System.out.println("How many cards will you play? ");
		int numCards = reader.nextInt();
		String[] chosenMeld = new String[numCards];                // ask teacher if you should use final 
		
		for (int i = 0; i < numCards; i++) {
			System.out.println("Play your card " + (i + 1) + ": ");			// concatenation + abusing loop mechanics to update the order of cards
			String chosenCard = reader.next();								
			
			int idx = index(hand, chosenCard);
			if (idx != -1) {
				chosenMeld[i] = hand[idx]; 									// add to meld
				hand[idx] = "0";           									// replace in hand
			} else {
				System.out.println("Card not found in hand. Try again.");
				i--; 															// repeat this iteration
			}
		}
		return chosenMeld;
	}
}