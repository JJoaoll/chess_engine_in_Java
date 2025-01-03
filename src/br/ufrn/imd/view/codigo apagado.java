/*
public void paint(Graphics2D g2d){
        g2d.drawImage(sprite, xPos, yPos, null);
    }


// SelectedPiece Highlight
            /*for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++) {

                    try {
                        if(isValidMove(new Move(this, selectedPiece, c, r))) {
                            g2d.setColor(new Color(68, 180, 57, 190));
                            g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }*/



 /*// PAINT HIGHLIGHTS
        // TODO: fix this DRY ('U CAN MOVE HERE')
        // TODO: The current position should be less neutral!
        // TODO: Remove TRY CATCHS
        if (selectedPiece != null)
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < cols; c++) {

                    try {
                        if(isValidMove(new Move(this, selectedPiece, c, r))) {
                            g2d.setColor(new Color(68, 180, 57, 190));
                            g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

        // PAINT PIECES
        for (Piece piece : pieceList) {
            piece.paint(g2d);
        }*/



/*@Override
    public void mouseDragged(MouseEvent e) {
        GameManager referee = GameManager.getInstance();
        Board board = Game.getBoard();
        Optional<Piece> opt_piece = referee.getSelectedPiece();

        opt_piece.ifPresent(piece -> {
            // Atualize as coordenadas da peça com base no movimento do mouse
            int newX = e.getX() - (board.tileSize / 2);
            int newY = e.getY() - (board.tileSize / 2);

            Position2D position = new Position2D(newX, newY);

            if (true) {
                piece.setCurrent_position(position);
            }

            referee.repaint();
        });
    }*/


/*if(board.selectedPiece != null) {
            Move move = new Move(board, board.selectedPiece, col, row);

            // TODO: Inlinize it
            try {
                if(board.isValidMove(move)){
                    board.makeMove(move);
                }

                else {
                    board.selectedPiece.setxPos(
                            board.selectedPiece.getCol() * board.tileSize);

                    board.selectedPiece.setyPos(
                            board.selectedPiece.getRow() * board.tileSize);
                }
            }

            catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            board.selectedPiece = null;
            board.repaint();
        }*/
