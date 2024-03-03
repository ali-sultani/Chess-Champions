package chess.rules;

import java.awt.Point;

public class KingBasicRule extends ChessRule{

	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		int distanceX = newGridPos.x - gridPos.x;
		int distanceY = newGridPos.y - gridPos.y;
		return (Math.abs(distanceX) <= 1 && Math.abs(distanceY) <= 1);
	}

	
}
