package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;
import chess.game.ChessPiece;
import chess.game.ChessUtils;

public class EnemyDestinationRule extends ChessRule{

	private ChessBoard chessBoard;
	
	EnemyDestinationRule(ChessBoard board){
		chessBoard = board;
	}
	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		ChessPiece piece1 = chessBoard.getPiece(gridPos);
		ChessPiece piece2 = chessBoard.getPiece(newGridPos);
		
		//si elle contient une pièce ennemie,
		if (piece1.getColor() != piece2.getColor() && piece2.getColor() != ChessUtils.COLORLESS)
			return true;
		return false;
	}

}
