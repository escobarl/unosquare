package unosquare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MinesChallenge {

	public static void main(String[] args) {

		MinesChallenge challenge = new MinesChallenge();

		try {

			String FILE = "mines.txt";

			float[][] mines;

			// Load all the mines from the input file
			mines = challenge.loadMines(FILE);

			if (mines != null) {

				// Process all mines
				Map<String, Explosion> map = challenge.processMines(mines);

				List<Explosion> list = new ArrayList<Explosion>(map.values());

				int MAX_MINES = Collections.max(list, Comparator.comparing(e -> e.getMaxMines())).getMaxMines();

				List<Explosion> targetMines = list.stream().filter(e -> e.getMaxMines() == MAX_MINES)
						.collect(Collectors.toList());

				System.out.println(" *** FINAL RESULTS *** ");
				
				System.out.println("MINES THAT EXPLODE THE MAX NUMBER OF MINES = " + targetMines.size());

				for (Explosion e : targetMines) {
					System.out.println("> MINE [x,y,r] [" + e.getX() + "," + e.getY() + "," + e.getR()
							+ "] which explodes " + e.getMaxMines() + " mines.");
				}

			}

		} catch (IOException ioException) {
			System.out.println("Unable to read the input file.");
		}
	}

	/*
	 * Method that calculates whether a mine explodes or not based on the distance
	 * and blast radius
	 */
	private boolean mineExplodes(float x1, float y1, float radio, float x2, float y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)) <= radio;
	}

	/*
	 * Method that process each mine against the rest of the mines
	 * 
	 */
	private Map<String, Explosion> processMines(float[][] mines) {

		Map<String, Explosion> map = new HashMap<String, Explosion>();
		
		int rows = mines.length;

		System.out.println("*** EXPLOSIONS ***");

		for (int i = 0; i < rows; i++) {

			float x1 = mines[i][0];
			float y1 = mines[i][1];
			float rad = mines[i][2];

			int count = 0;

			for (int j = 0; j < rows; j++) {

				if (i != j) {

					float x2 = mines[j][0];
					float y2 = mines[j][1];

					// Invoke method that determines whether the mine explodes or not
					boolean explodes = mineExplodes(x1, y1, rad, x2, y2);

					if (explodes) {

						Explosion mine = new Explosion(x1, y1, rad);
						String KEY = x1 + "," + y1;

						if (map.containsKey(KEY)) {
							map.get(KEY).setMaxMines(++count);
						} else {
							mine.setMaxMines(++count);
							map.put(KEY, mine);
						}

						System.out.println("mine: " + x1 + ", " + y1 + " explodes: " + x2 + ", " + y2);
					}
				}
			}
		}

		return map;
	}

	/*
	 * Method that loads all the mines into an array from a given file path
	 * 
	 */
	private float[][] loadMines(String FILE) throws IOException {

		float[][] mines = null;
		BufferedReader reader = null;
		
		try {

			int lineCount = 0;
			String line = "";

			reader = new BufferedReader(new FileReader(new File(FILE)));

			while ((line = reader.readLine()) != null) {
				if (!line.isEmpty()) {
					lineCount++;
				}
			}

			mines = new float[lineCount][3];

			Scanner scan = new Scanner(new File(FILE));
			int row = 0;

			while (scan.hasNext()) {
				line = scan.nextLine();

				if (!line.isEmpty()) {
					String[] values = line.split(" ");

					mines[row][0] = Float.valueOf(values[0]);
					mines[row][1] = Float.valueOf(values[1]);
					mines[row][2] = Float.valueOf(values[2]);

					row++;
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File is not found" + e.getMessage());
		}

		return mines;
	}

}
