package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;

public class CompositeBlackPawnRule extends ChessRule {
	
	ChessBoard chessBoard;
	
	public CompositeBlackPawnRule(ChessBoard board){
		chessBoard = board;
	}
	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		ChessRule rule1 = ChessRule.and(new BlackPawnBasicRule(), new EmptyDestinationRule(chessBoard));
		ChessRule rule2 = ChessRule.and(new BlackPawnCapture(), new EnemyDestinationRule(chessBoard));
		ChessRule rule3 = ChessRule.and(new BlackPawnBigStep(), new EmptyDestinationRule(chessBoard));
		
		
		return rule1.verifyMove(gridPos, newGridPos) ||
				rule2.verifyMove(gridPos, newGridPos) ||
				rule3.verifyMove(gridPos, newGridPos);
	}

}
