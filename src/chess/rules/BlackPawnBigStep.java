package chess.rules;

import java.awt.Point;

public class BlackPawnBigStep extends ChessRule {

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		int distanceY = newGridPos.y - gridPos.y;
		
		if (newGridPos.y == 1 && distanceY == 2)	//ligne 7
			return true;
		return false;
	}

}
