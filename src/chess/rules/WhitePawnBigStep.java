package chess.rules;

import java.awt.Point;

public class WhitePawnBigStep extends ChessRule {

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		int distanceY = newGridPos.y - gridPos.y;
		
		if (newGridPos.y == 6 && distanceY == -2)
			return true;
		return false;
	}

	
}
