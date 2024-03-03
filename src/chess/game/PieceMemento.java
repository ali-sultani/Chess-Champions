package chess.game;

import java.awt.Point;
import java.io.Writer;
import java.util.Scanner;

public class PieceMemento {
	
	// Position de la pièce sur l'échiquier
	
	String memento = "";
	
	public PieceMemento(String chessPiece) {
		
		memento = chessPiece;
	
	}
	
	public static PieceMemento readFromStream(Scanner reader) {

		String pieceDescription = reader.next();
		
		
		
		return new PieceMemento(pieceDescription);
		
		// LaboX: vider
/*
		String pieceDescription = reader.next();

		if (pieceDescription.length() != 5) {
			throw new IllegalArgumentException("Badly formed Chess Piece description: " + pieceDescription);
		}

		return new ChessPiece(pieceDescription.substring(3, 5), pieceDescription.substring(0, 2), b);
*/
	}
	
	
	public void saveToStream(Writer writer) throws Exception {
		
		/*
		writer.append(ChessUtils.makeAlgebraicPosition(gridPosX, gridPosY));
		writer.append('-');
		writer.append(ChessUtils.makePieceName(color, type));
		*/
		writer.append(memento);
		writer.append('\n');

	}
	
	public String getString() {
		
		return memento;
	}
	
	public Point getPosition() {
		
		return ChessUtils.convertAlgebraicPosition(memento.substring(0, 2));
	}
	
	public int getPosX() {
		
		return getPosition().x;
	}
	
	public int getPosY() {
		
		return getPosition().y;
	}
	
	public int getColor() {
		
		return ChessUtils.getColor(memento.substring(3, 5)); 
	}
	
	public int getType() {
		
		return ChessUtils.getType(memento.substring(3, 5)); 
	}

}
