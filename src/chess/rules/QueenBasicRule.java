package chess.rules;

import java.awt.Point;

public class QueenBasicRule extends ChessRule{

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		int distanceX = newGridPos.x - gridPos.x;
		int distanceY = newGridPos.y - gridPos.y;
		return (Math.abs(distanceX) == Math.abs(distanceY)) || (distanceX != 0 && distanceY == 0)
				|| (distanceX == 0 && distanceY != 0);
	}
	
	

}
