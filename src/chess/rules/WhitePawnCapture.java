package chess.rules;

import java.awt.Point;

public class WhitePawnCapture extends ChessRule{

	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {

		int distanceX = newGridPos.x - gridPos.x;
		int distanceY = newGridPos.y - gridPos.y;
		
		if ((distanceX == -1 && distanceY == -1) || (distanceX == 1 && distanceY == -1))
			return true;
		return false;
	}

}
