package com.example.demo2.algorithmDisplays;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MatrixDisplay extends Display {

    private Matrix matrix;

    public MatrixDisplay(VBox container, String name, int ratio) {
        super(container, name, ratio);
        //todo - prerobit, musim
        System.out.println(super.getWidth()+" "+super.getHeight());
        this.matrix = new Matrix(super.getPane(), 0,0,super.getWidth(), super.getWidth());
    }

    @Override
    public void centre(){
        //todo
    }

    @Override
    public void setSize(double width, double height){
        super.setSize(width, height);
        this.matrix.setBorders(0,0, width, height - 20.0);
    }

    public void setMatrixSize(int numberOfRows, int numberOfColumns){
        this.matrix.setMatrixSize(numberOfRows, numberOfColumns);
    }

    public void highlightSquare(int rowNumber, int columnNumber){
        this.matrix.highlightSquare(rowNumber, columnNumber);
    }

    public void highlightRow(int rowNumber){
        this.matrix.highlightRow(rowNumber);
    }

    public void highlightColumn(int columnNumber){
        this.matrix.highlightColumn(columnNumber);
    }

    public void setSquareColor(int rowNumber, int columnNumber, Color color){
        this.matrix.setSquareColor(rowNumber, columnNumber, color);
    }

    public void setSquareText(int rowNumber, int columnNumber, String text){
        this.matrix.setSquareText(rowNumber, columnNumber, text);
    }

    public void setSquareText(int rowNumber, int columnNumber, int text){
        this.matrix.setSquareText(rowNumber, columnNumber, text);
    }

    public void setSquareText(int rowNumber, int columnNumber, char c){
        this.matrix.setSquareText(rowNumber, columnNumber, c);
    }

    public void setSquareIndex(int rowNumber, int columnNumber, String index){
        this.matrix.setSquareIndex(rowNumber, columnNumber, index);
    }

    public void setSquareIndex(int rowNumber, int columnNumber, int text){
        this.matrix.setSquareIndex(rowNumber, columnNumber, text);
    }

    public void setSquareIndex(int rowNumber, int columnNumber, char c){
        this.matrix.setSquareIndex(rowNumber, columnNumber, c);
    }

    public void addArrow(int startRow, int startColumn, int endRow, int endColumn){
        this.matrix.addArrow(startRow, startColumn, endRow, endColumn);
    }

    public void removeArrow(int startRow, int startColumn, int endRow, int endColumn){
        this.matrix.removeArrow(startRow, startColumn, endRow, endColumn);
    }

    public void setArrowColor(int startRow, int startColumn, int endRow, int endColumn, Color color){
        this.matrix.setArrowColor(startRow, startColumn, endRow, endColumn, color);
    }

    public void highlightArrow(int startRow, int startColumn, int endRow, int endColumn){
        this.matrix.highlightArrow(startRow, startColumn, endRow, endColumn);
    }

    public void unhighlightArrow(int startRow, int startColumn, int endRow, int endColumn){
        this.matrix.unhighlightArrow(startRow, startColumn, endRow, endColumn);
    }

    public void clearArrows(){
        this.matrix.clearArrows();
    }

    public void setSize(double size){
        this.matrix.setSize(size);
        centre();
    }


}
