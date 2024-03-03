package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;
import chess.game.ChessPiece;

public class EmptyDestinationRule extends ChessRule{
	
	private ChessBoard chessBoard;
	
	EmptyDestinationRule(ChessBoard board){
		chessBoard = board;
	}

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		ChessPiece piece = chessBoard.getPiece(newGridPos);
		
		if (piece.isNone())		//return true si la nouvelle position est vide
			return true;
		
		return false;
	}

}
