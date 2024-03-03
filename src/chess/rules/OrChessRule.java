package chess.rules;

import java.awt.Point;

public class OrChessRule extends ChessRule {

	ChessRule rule1;
	ChessRule rule2;
	
	public OrChessRule(ChessRule r1, ChessRule r2) {
		rule1 = r1;
		rule2 = r2;
	}

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		// TODO Auto-generated method stub
		if (rule1.verifyMove(gridPos, newGridPos) || rule2.verifyMove(gridPos, newGridPos))
			return true;
		return false;
	}

}
