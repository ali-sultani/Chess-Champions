package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;

public class HasPieceRule extends ChessRule {
	
	private ChessBoard chessBoard;
	
	HasPieceRule(ChessBoard board){
		chessBoard = board;
	}

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		if (!chessBoard.getPiece(gridPos).isNone())
			return true;
		
		return false;
	}
	

}
