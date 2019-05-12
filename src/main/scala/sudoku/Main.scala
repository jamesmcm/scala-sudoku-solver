// Copyright (C) 2019 James McMurray
//
// scala-sudoku-solver is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// scala-sudoku-solver is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with scala-sudoku-solver.  If not, see <http://www.gnu.org/licenses/>.

package sudoku


object Main extends App {

  @SuppressWarnings(Array("org.wartremover.warts.Equals"))
  implicit final class AnyOps[A](self: A) {
   def ===(other: A): Boolean = self == other
}

  if (args.length === 0) {
    println("Missing filename argument")
    System.exit(1)
  }
  println(solveSudoku(args(0)))


  def solveSudoku(filename: String): String ={
    recurse(loadSudoku(filename), 0,0) match {
      case None => "No valid solution"
      case Some(solution) => solution.map((x:Array[Int]) => x.mkString("")).mkString("\n")
    }
  }

  def getBoxBounds(row: Int, col: Int): (Int, Int) = {
    (3*(row/3), 3*(col/3))
  }

  def isValidMove(a: Array[Array[Int]], row: Int, col: Int, candidate: Int): Boolean = {
    val (i, j) = getBoxBounds(row, col)
    !(a(row)
      .map((x: Int) => x === candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y) |
    a.map((x: Array[Int]) => x(col))
      .map((x: Int) => x === candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y) |
    a.slice(i, i + 3).flatMap((x: Array[Int]) => x.slice(j, j + 3))
      .map((x: Int) => x === candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y))
  }

  def bound(row: Int, col: Int): (Int, Int) = {
    (row, col) match {
      case (_, 8) => (row + 1, 0)
      case _ => (row, col + 1)
    }
  }

  def move(a: Array[Array[Int]], row: Int, col: Int): Option[Array[Array[Int]]] = {
    val (newrow, newcol) = bound(row, col)
    (newrow, newcol) match {
      case (9, _) => Some(a)
      case _ => recurse(a, newrow, newcol)
    }
  }

  def substitute(a: Array[Array[Int]], row: Int, col: Int, candidate: Int): Option[Array[Array[Int]]] = {
    val b = a.map(_.clone)

    b(row)(col) = candidate
    recurse(b, row, col)
  }

  def recurse(a: Array[Array[Int]], row: Int, col: Int): Option[Array[Array[Int]]] = {
    a(row)(col) match {
      case 0 => (1 to 9).filter(isValidMove(a, row, col, _))
        .map((x: Int) => substitute(a, row, col, x))
        .find(_.isDefined).flatten
      case _ => move(a, row, col)
    }
  }


  def loadSudoku(filename: String): Array[Array[Int]] = {
    val form = (x: String) => if (x === "-") "0" else x
    val source = scala.io.Source.fromFile(filename)

    try source.getLines.map((x: String) => x.split("").map(form).map(_.toInt)).toArray finally source.close()
  }
}
