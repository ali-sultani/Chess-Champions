package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;

public class NoFriendlyFireRule extends ChessRule {

	private ChessBoard chessBoard;
	
	NoFriendlyFireRule(ChessBoard board){
		chessBoard = board;
	}

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		if (chessBoard.getPiece(gridPos).getColor() != chessBoard.getPiece(newGridPos).getColor())
			return true;
		
		return false;
	}
	

}
