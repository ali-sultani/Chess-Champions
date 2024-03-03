package chess.game;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ChessMove {
	
	private Point posDepart;
	private Point posArrivee;
	
	public ChessMove(String move) {
		
		String start = move.substring(0,2);
		String end = move.substring(3,5);
		
		posDepart = ChessUtils.convertAlgebraicPosition(start);
		posArrivee = ChessUtils.convertAlgebraicPosition(end);
	}
	
	public ChessMove(Point point1, Point point2) {
		
		posDepart = point1;
		posArrivee = point2;
	}
	
	public Point getPosDepart() {
		return posDepart;
	}
	
	public Point getPosArrivee() {
		return posArrivee;
	}
	
	public void saveToStream(FileWriter writer) {
		
		String move = "";
		move += ChessUtils.makeAlgebraicPosition(posDepart.x, posDepart.y);
		move += "-";
		move += ChessUtils.makeAlgebraicPosition(posArrivee.x, posArrivee.y);
		move += "\n";
		
		try {
			writer.write(move);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadFromStream(Scanner reader) {
		
		String move = reader.next();
		String pos1 = move.substring(0, 2);
		String pos2 = move.substring(3, 5);
		
		posDepart = ChessUtils.convertAlgebraicPosition(pos1);
		posArrivee = ChessUtils.convertAlgebraicPosition(pos2);
	}

}
