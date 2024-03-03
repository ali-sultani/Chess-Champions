package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;

public class CompositeWhitePawnRule extends ChessRule{

	ChessBoard chessBoard;
	
	public CompositeWhitePawnRule(ChessBoard board){
		chessBoard = board;
	}
	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		ChessRule rule1 = ChessRule.and(new WhitePawnBasicRule(), new EmptyDestinationRule(chessBoard));
		ChessRule rule2 = ChessRule.and(new WhitePawnCapture(), new EnemyDestinationRule(chessBoard));
		ChessRule rule3 = ChessRule.and(new WhitePawnBigStep(), new EmptyDestinationRule(chessBoard));
		
		
		return rule1.verifyMove(gridPos, newGridPos) ||
				rule2.verifyMove(gridPos, newGridPos) ||
				rule3.verifyMove(gridPos, newGridPos);
	}

}
