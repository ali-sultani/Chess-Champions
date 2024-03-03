package chess.rules;

import java.awt.Point;

public class BlackPawnBasicRule extends ChessRule {
	
	@Override
	public boolean verifyMove(Point gridPos, Point newGridPos) {
		
		int distanceX = newGridPos.x - gridPos.x;
		int distanceY = newGridPos.y - gridPos.y;
		
		return (distanceX == 0 && distanceY == 1);
	}

}
