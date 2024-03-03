package chess.game;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import chess.ui.BoardView;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import chess.game.ChessPiece;

public class ChessBoard {

	// Grille de jeu 8x8 cases. Contient des références aux pièces présentes sur
	// la grille.
	// Lorsqu'une case est vide, elle contient une pièce spéciale
	// (type=ChessPiece.NONE, color=ChessPiece.COLORLESS).
	private ChessPiece[][] grid;

	private BoardView view;
	
	private BoardMemento initialeState;
	
	// pile d'anciens mouvement
	private Stack<ChessMove> anciensMove = new Stack<ChessMove>();

	public ChessBoard(int x, int y) {

		view = new BoardView(x, y);

		// Initialise la grille avec des pièces vides.
		grid = new ChessPiece[8][8];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new ChessPiece(i, j, this);
			}
		}
		
		
		initialeState = createMemento();
	}
	
	public ChessBoard(BoardMemento boardMemento, int x, int y) {
				
		view = new BoardView(x, y);

		// Initialise la grille avec des pièces vides.
		grid = new ChessPiece[8][8];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new ChessPiece(i, j, this);
			}
		}
		ArrayList<PieceMemento> mementos = boardMemento.getMemento();
		
		for (int i = 0; i < mementos.size(); i++)
		{
			ChessPiece newPiece = new ChessPiece(mementos.get(i), this);
			
			grid[newPiece.getGridX()][newPiece.getGridY()] = newPiece;
			
			putPiece(newPiece);
		}
		
		initialeState = createMemento();
	}

	// Place une pièce vide dans la case
	public void clearSquare(int x, int y) {
		grid[x][y] = new ChessPiece(x, y, this);
	}

	// Place une pièce sur le planche de jeu.
	public void putPiece(ChessPiece piece) {

		Point2D pos = view.gridToPane(piece.getGridX(), piece.getGridY());
		piece.moveUI(pos);
		piece.getUI().relocate(pos.getX(), pos.getY());
		view.getPane().getChildren().add(piece.getUI());
		grid[piece.getGridX()][piece.getGridY()] = piece;
	}

	// Vérifie si la case contient une pièce vide
	public boolean isEmpty(Point pos) {
		return (grid[pos.x][pos.y].getType() == ChessUtils.TYPE_NONE);
	}

	// Vérifie si une coordonnée est valide sur la planche de jeu
	public boolean isValid(Point pos) {
		return (pos.x >= 0 && pos.x <= 7 && pos.y >= 0 && pos.y <= 7);
	}

	// Vérifie si deux pièces sont de la même couleur
	public boolean isSameColor(Point pos1, Point pos2) {
		return grid[pos1.x][pos1.y].getColor() == grid[pos2.x][pos2.y].getColor();
	}

	// Déplace une pièce. Appelé par l'interface graphique lorsqu'un déplacement est
	// détecté.
	
	public boolean move(Point2D pixelPos, Point2D newPixelPos) {

		Point gridPos = view.paneToGrid(pixelPos);
		
		// nouvelle position
		Point newGridPos = view.paneToGrid(newPixelPos);
		
		ChessMove chessMove = new ChessMove(gridPos, newGridPos);

		ChessPiece toMove = grid[gridPos.x][gridPos.y];

		boolean result = move(chessMove);
		//boolean result = move(view.paneToGrid(pixelPos), view.paneToGrid(newPixelPos));

		toMove.moveUI(view.gridToPane(toMove.getGridPos()));

		return result;
	}

	 public boolean move(ChessMove chessMove){
		 
		 ChessPiece toMove = getPiece(chessMove.getPosDepart());

		if (toMove.isNone()) {
			return false;
		}

		if (!isValid(chessMove.getPosArrivee())) {
			return false;
		}

		if (isSameColor(chessMove.getPosDepart(), chessMove.getPosArrivee())) {
			return false;
		}

		if (!toMove.verifyMove(chessMove.getPosDepart(), chessMove.getPosArrivee())) {
			return false;
		}

		
		
		
		if (!isEmpty(chessMove.getPosArrivee())) {

			// Capture!
			removePiece(chessMove.getPosArrivee());
		}
		
		assignSquare(chessMove.getPosArrivee(), toMove);
		clearSquare(chessMove.getPosDepart());

		//ajouter une sauvegarde de l'objet Chessmove dans votre pile de mouvement passe si le mouvement
		//a reussi
		anciensMove.push(chessMove);
		
		toMove.moveUI(view.gridToPane(toMove.getGridPos()));
		
		return true;
	 }
	
	 /*
	// Déplace une pièce sur la grille. Vérifie les règles de déplacement.
	public boolean move(Point startPos, Point endPos) {

		ChessPiece toMove = getPiece(startPos);

		if (toMove.isNone()) {
			return false;
		}

		if (!isValid(endPos)) {
			return false;
		}

		if (isSameColor(startPos, endPos)) {
			return false;
		}

		if (!toMove.verifyMove(startPos, endPos)) {
			return false;
		}

		if (!isEmpty(endPos)) {

			// Capture!
			removePiece(endPos);
		}
		assignSquare(endPos, toMove);
		clearSquare(startPos);

		return true;
	}*/

	// Lecture et exécution d'une suite de mouvements
	public void loadMovesFromFile(File file) throws Exception {
		
		//loadScript(file);
		
		
		Scanner reader = new Scanner(file);
		
		BoardMemento boardMemento = BoardMemento.readFromFile(reader);
		
		
		view = new BoardView(0, 0);

		// Initialise la grille avec des pièces vides.
		grid = new ChessPiece[8][8];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new ChessPiece(i, j, this);
			}
		}
		ArrayList<PieceMemento> mementos = boardMemento.getMemento();
		
		for (int i = 0; i < mementos.size(); i++)
		{
			ChessPiece newPiece = new ChessPiece(mementos.get(i), this);
			
			grid[newPiece.getGridX()][newPiece.getGridY()] = newPiece;
			
			putPiece(newPiece);
		}
		
		initialeState = createMemento();
		

		while(reader.hasNext())
		{
			String move = reader.nextLine();
			
			ChessMove chessMove = new ChessMove(move);
			move(chessMove);
		}
		
		reader.close();
	}
	
	

	// Lecture d'un ChessBoard à partir d'un fichier. Utilisé par les tests.
	public static ChessBoard readFromFile(String fileName) throws Exception {
		return readFromFile(new File(fileName), 0, 0);
	}
	
	
	
	// Lecture d'un ChessBoard à partir d'un fichier
	public static ChessBoard readFromFile(File file, int x, int y) throws Exception {

		Scanner reader = new Scanner(new FileReader(file));
		
		BoardMemento boardMemento = BoardMemento.readFromFile(reader);

		/*
		while (true) {
			ChessPiece piece;
			try {
				piece = ChessPiece.readFromStream(reader, board);
			} catch (Exception e) {
				break;
			}
			board.putPiece(piece);
		}*/
		reader.close();
		ChessBoard board = new ChessBoard(boardMemento, x, y);
		
		board.initialeState = board.createMemento();
		
		return board;

	}
	

	
	//Sauvegarde dans un fichier.
	public void saveToFile(File file) throws Exception {

		FileWriter writer = new FileWriter(file);
		
		//BoardMemento boardMemento = createMemento();
		
		BoardMemento.saveToFile(writer, grid);

		/*
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				ChessPiece toWrite = grid[i][j];
				if (!toWrite.isNone()) {
					toWrite.saveToStream(writer);
				}
			}
		}
		*/
		//Séparateur. Nécessaire pour la lecture de scripts.
		//writer.write("</BOARD>\n");
		writer.close();
	}
	
	
	

	@Override
	public boolean equals(Object other) {

		if (other instanceof ChessBoard) {
			ChessBoard otherBoard = (ChessBoard) other;

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (!grid[i][j].equals(otherBoard.grid[i][j])) {
						return false;
					}
				}
			}
			return true;
		}

		return false;

	}

	public void removePiece(Point pos) {

		view.getPane().getChildren().remove(grid[pos.x][pos.y].getUI());
		clearSquare(pos);
	}

	public void clearSquare(Point pos) {
		grid[pos.x][pos.y] = new ChessPiece(pos.x, pos.y, this);
	}

	public void assignSquare(Point pos, ChessPiece piece) {
		piece.setGridPos(pos);
		grid[pos.x][pos.y] = piece;
	}

	public ChessPiece getPiece(Point pos) {
		return grid[pos.x][pos.y];
	}

	public Node getUI() {

		return view.getPane();
	}
	
	public ChessPiece[][] getGrid()	{
		
		return grid;
	}

	public BoardMemento createMemento() {
		
		ArrayList<PieceMemento> pieceMementos = new ArrayList<PieceMemento>();
		
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				if(!grid[i][j].isNone())
					pieceMementos.add(grid[i][j].createMemento());
			}
		}
		System.out.println(pieceMementos.size());
		return new BoardMemento(pieceMementos);
	}
	
	public void restroreMemento(BoardMemento boardMemento) {
		
		ArrayList<PieceMemento> mementos = boardMemento.getMemento(); // retourne le ArrayList des PieceMemento
		
		for(int i = 0; i < mementos.size(); i++) {
			
			
			Point posPiece = mementos.get(i).getPosition();
			
			grid[posPiece.x][posPiece.y].restoreMemento(mementos.get(i), this);
		}
	
	}
	
	public void saveScript(File fichier) {
		
		
		try {
			
			FileWriter writer = new FileWriter(fichier);
			
			//sauvegarder d'abord intialeState
			initialeState.saveToStream(writer);
			
			
			
			//parcourez la pile de mouvements et enregistrez-les dans le fichier.
			for (int i = 0; i < anciensMove.size(); i++)
			{
				anciensMove.pop().saveToStream(writer);
			}
			
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void loadScript(File fichier) throws Exception {
		
		
		//loadMovesFromFile(fichier);
		
		Scanner reader = new Scanner(fichier);
				
		BoardMemento memento = BoardMemento.readFromFile(reader);
		
		while(reader.hasNextLine())
		{
			String move = reader.nextLine();			
			ChessMove chessMove = new ChessMove(move);
			move(chessMove);
		}
		
		reader.close();
	}
	

}
