package chess.game;

import java.awt.Point;
import java.io.Writer;
import java.util.Scanner;

import chess.rules.ChessRule;
import chess.ui.PieceView;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public class ChessPiece {

	// Position de la pièce sur l'échiquier
	private int gridPosX;
	private int gridPosY;

	private int type;
	private int color;

	private PieceView view;
	
	private ChessRule chessRule;

	// Pour créer des pièces à mettre sur les cases vides
	public ChessPiece(int x, int y, ChessBoard b) {
		this.type = ChessUtils.TYPE_NONE;
		this.color = ChessUtils.COLORLESS;
		gridPosX = x;
		gridPosY = y;
		
		chessRule = ChessRule.createRulesForPiece(this, b);

	}
	
	public ChessPiece(PieceMemento pieceMemento, ChessBoard board) {
		
		String piece = pieceMemento.getString();
		
		System.out.println(piece);
		
		color = ChessUtils.getColor(piece.substring(3, 5));
		type = ChessUtils.getType(piece.substring(3, 5));
		view = new PieceView(type, color, board);
		
		Point point = ChessUtils.convertAlgebraicPosition(piece.substring(0, 2));
		
		gridPosX = point.x;
		gridPosY = point.y;
		//setAlgebraicPos(piece.substring(0, 2));
		
		chessRule = ChessRule.createRulesForPiece(this, board);

	}

	// Création d'une pièce normale. La position algébrique en notation d'échecs
	// lui donne sa position sur la grille.
	public ChessPiece(String name, String pos, ChessBoard b) {

		color = ChessUtils.getColor(name);
		type = ChessUtils.getType(name);
		view = new PieceView(type, color, b);

		setAlgebraicPos(pos);
		
		chessRule = ChessRule.createRulesForPiece(this, b);

	}

	/*public static ChessPiece readFromStream(Scanner reader, ChessBoard b) {
		// LaboX: vider

		String pieceDescription = reader.next();

		if (pieceDescription.length() != 5) {
			throw new IllegalArgumentException("Badly formed Chess Piece description: " + pieceDescription);
		}

		return new ChessPiece(pieceDescription.substring(3, 5), pieceDescription.substring(0, 2), b);

	}*/

	/*public void saveToStream(Writer writer) throws Exception {

		
		writer.append(ChessUtils.makeAlgebraicPosition(gridPosX, gridPosY));
		writer.append('-');
		writer.append(ChessUtils.makePieceName(color, type));
		writer.append('\n');

	}
*/
	// Règles de mouvements
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		return chessRule.verifyMove(gridPos, newGridPos);
		
	}
	public void setAlgebraicPos(String pos) {

		Point pos2d = ChessUtils.convertAlgebraicPosition(pos);

		gridPosX = pos2d.x;
		gridPosY = pos2d.y;
	}

	public int getType() {
		return type;
	}

	public int getColor() {
		return color;
	}

	public int getGridX() {
		return gridPosX;
	}

	public int getGridY() {
		return gridPosY;
	}

	public Point getGridPos() {
		return new Point(gridPosX, gridPosY);
	}

	public void setGridPos(Point pos) {
		gridPosX = pos.x;
		gridPosY = pos.y;
	}

	public Pane getUI() {
		return view.getPane();
	}

	public void moveUI(Point2D pos) {
		if (view != null)
			view.move(pos);
	}

	public boolean isNone() {

		return type == ChessUtils.TYPE_NONE;
	}

	@Override
	public boolean equals(Object other) {

		if (other instanceof ChessPiece) {
			ChessPiece otherPiece = (ChessPiece) other;

			return (color == otherPiece.color && type == otherPiece.type);
		}
		return false;

	}

	
	public PieceMemento createMemento() {
		
		String pos = ChessUtils.makeAlgebraicPosition(gridPosX, gridPosY);
		String nom = ChessUtils.makePieceName(color, type);
		String piece = pos + "-" + nom;
		
		return new PieceMemento(piece);
	}
	
	
	public void restoreMemento(PieceMemento pieceMemento, ChessBoard board) {
				
		gridPosX = pieceMemento.getPosX();
		gridPosY = pieceMemento.getPosY();
		color = pieceMemento.getColor();
		type = pieceMemento.getType();
		
		chessRule = ChessRule.createRulesForPiece(this, board);
		
	}
}
