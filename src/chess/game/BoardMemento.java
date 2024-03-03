package chess.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardMemento {
	
	private ArrayList<PieceMemento> mementos = new ArrayList<PieceMemento>();
	
	public BoardMemento(ArrayList<PieceMemento> memento) {
		
		mementos = memento;
	}
	/*
	
	public BoardMemento(ChessBoard board) {
		
		ChessPiece grid[][] = board.getGrid();
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (!grid[i][j].isNone()) // si la piece n'est pas vide
				{
					
					String pos = ChessUtils.makeAlgebraicPosition(grid[i][j].getGridX(), grid[i][j].getGridY());
					String nom = ChessUtils.makePieceName(grid[i][j].getColor(), grid[i][j].getType());
					String piece = pos + "-" + nom;
					// creer un memento sur la piece 
					PieceMemento pieceMemento = new PieceMemento(piece);
					// ajouter dans la liste des mementos
					mementos.add(pieceMemento);
				}
			}
		}
		
	}*/

	
	// Lecture d'un ChessBoard à partir d'un fichier
	public static BoardMemento readFromFile(Scanner reader) throws Exception {

		ArrayList<PieceMemento> boardState = new ArrayList<PieceMemento>();
		
		while(reader.hasNextLine())
		{
			String piece = reader.nextLine();
			
			if (piece.equals("</BOARD>")) {
				break;
			}
			else {
				boardState.add(new PieceMemento(piece));
				
			}
		}
		
		return new BoardMemento(boardState);
		/*
		ChessBoard board = new ChessBoard(x, y);

		while (true) {
			ChessPiece piece;
			try {
				piece = PieceMemento.readFromStream(reader, board);
			} catch (Exception e) {
				break;
			}
			board.putPiece(piece);
		}
		
		return board;
		*/
	}
	
	//Sauvegarde dans un fichier.
	public static void saveToFile(FileWriter writer, ChessPiece grid[][]) throws Exception {		

		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (!grid[i][j].isNone()) 
				{
					grid[i][j].createMemento().saveToStream(writer);
				}
			}
		}
		
		writer.write("</BOARD>\n");
		
		/*
		for (int i = 0; i < mementos.size(); i++) {
			
			mementos.get(i).saveToStream(writer);
		}
		writer.write("</BOARD>\n");*/
		//writer.close();
		
		/*
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				ChessPiece toWrite = grid[i][j];
				
				if (!toWrite.isNone()) {
					toWrite.saveToStream(writer);
				}
			}
		}
		//Séparateur. Nécessaire pour la lecture de scripts.
		writer.write("</BOARD>\n");
		//writer.close();*/
	}
	
	
	public ArrayList<PieceMemento> getMemento(){
		
		return mementos;
	}


	public void saveToStream(FileWriter writer) throws Exception {
		// TODO Auto-generated method stub
		
		for (PieceMemento pieceMemento : mementos)
		{
			if (pieceMemento.getType() != ChessUtils.TYPE_NONE)
				pieceMemento.saveToStream(writer);
		}
		
		writer.write("</BOARD>\n");
	}
	
}
