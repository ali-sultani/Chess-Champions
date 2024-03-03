package chess.rules;

import java.awt.Point;

public class BishopBasicRule extends ChessRule {
	
	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		int distanceX = newGridPos.x - gridPos.x;
		int distanceY = newGridPos.y - gridPos.y;
		return (Math.abs(distanceX) == Math.abs(distanceY));
	}

	
}
