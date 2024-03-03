package chess.rules;

import java.awt.Point;

import chess.game.ChessBoard;
import chess.game.ChessPiece;
import chess.game.ChessUtils;

public abstract class ChessRule {
	
	//int color;
	//int type;
	
	public ChessRule() {
		
	}
	
	/*
	public ChessRule(int c, int t){
		
		color = c; 
		type = t;
	}*/

	public abstract boolean verifyMove(Point gridPos, Point newGridPos);
	
	public static ChessRule createRulesForPiece(ChessPiece piece, ChessBoard board) {
		
		int type = piece.getType();
		int color = piece.getColor();
		
		switch (type) {

		case ChessUtils.TYPE_PAWN:

			if (color == ChessUtils.WHITE) {
				//return new WhitePawnBasicRule();
				return new CompositeWhitePawnRule(board);
				
			} else if (color == ChessUtils.BLACK) {
				return new CompositeBlackPawnRule(board);
			}

		case ChessUtils.TYPE_BISHOP:

			return new BishopBasicRule();

		case ChessUtils.TYPE_KING:

			return new KingBasicRule();

		case ChessUtils.TYPE_ROOK:

			return new RookBasicRule();

		case ChessUtils.TYPE_QUEEN:

			//return new QueenBasicRule(color, type);
			return ChessRule.or(new RookBasicRule(), new BishopBasicRule());

		case ChessUtils.TYPE_KNIGHT:

			return new KnightBasicRule();
			
		case ChessUtils.TYPE_NONE:
			
			return new EmptyPieceRule();

		}

		//par defaut retourne le rule pour une piece vide qui lance une exception
		return new EmptyPieceRule();
	}
	
	
	public static ChessRule and(ChessRule r1,ChessRule r2) {
		 return new AndChessRule(r1,r2);
	}
	
	public static ChessRule or(ChessRule r1,ChessRule r2) {
		return new OrChessRule(r1,r2);
	}


}
